package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TableMemberMapper;
import com.spring.fee.dao.mapper.TableMemberMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberLevelChangeDetailBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.common.util.ExcelUtils;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.domain.RoleInfo;
import com.spring.free.domain.UserInfo;
import com.spring.free.system.UserService;
import com.spring.free.util.DateUtils;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.velocity.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 会员管理表服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberBusiSVImpl implements ITableMemberBusiSV {

    @Value("${python.path}")
    public String python_path;

    @Autowired
    TableMemberMapper iTableMemberMapper;

    @Autowired
    TableMemberMapperDZ iTableMemberMapperDZ;

    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;

    @Autowired
    ITableMemberLevelChangeDetailBusiSV iTableMemberLevelChangeDetailBusiSV;

    @Autowired
    UserService userService;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableMember insert(TableMember bo) {
        log.info("创建会员管理表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        BigDecimal zero = new BigDecimal(0);
        bo.setAccountCarPoint(zero);
        bo.setScore(0);
        bo.setAccountPointFreeze(zero);
        bo.setAccountJsyPoint(zero);
        bo.setAccountDjPoint(zero);
        bo.setAccountPointAvailable(zero);
        bo.setReportingAmount(zero);
        bo.setAccountMoney(zero);
        bo.setRegisterTime(sysdate);
        //默认都是实名的
        bo.setAutFlag(1);
        if (2 == bo.getRegisterFrom())
            bo.setState(InvestConstants.MemberState.VALID);
        if (1 == bo.getRegisterFrom())
            bo.setState(InvestConstants.MemberState.INIT);
        iTableMemberMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMember updateSimple(TableMember bo) {
        bo.setReallyName(null);
        bo.setPhone(null);
        if (this.iTableMemberMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMember updateSimple2(TableMember bo) {
        if (this.iTableMemberMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }


    @Override
    public TableMember update(TableMember bo, boolean updateArrange) {

        //查询会员
        TableMember origMember = this.selectByMemberId4BuyOrder(bo.getMemberId());
        bo.setId(origMember.getId());
        if (origMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }
        //修改了安置人
        if ((StringUtils.isNotEmpty(bo.getArrangeId()) && !origMember.getArrangeId().equals(bo.getArrangeId())) || updateArrange) {

            //判断新的安置人是否允许修改
            TableMember arrangeMember = this.selectByMemberId(bo.getArrangeId());
            if (arrangeMember == null) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "安置人不存在！", "", null);
            }
            checkArrange(bo, arrangeMember);

            //更新安置人
            //判断左区
            if (1 == bo.getLeftOrRight()) {
                arrangeMember.setLeftChildNode(bo.getMemberId());
            }

            //判断右区，如果存在不允许注册
            if (2 == bo.getLeftOrRight()) {
                arrangeMember.setRightChildNode(bo.getMemberId());
            }
            this.updateSimple(arrangeMember);

            if (!origMember.getArrangeId().equals(bo.getArrangeId())) {

                //原安置人对象
                TableMember origArrangeMember = this.selectByMemberId(origMember.getArrangeId());
                //修改原安置人数据
                //判断左区
                if (1 == origMember.getLeftOrRight()) {
                    origArrangeMember.setLeftChildNode("0");
                }

                //判断右区，如果存在不允许注册
                if (2 == origMember.getLeftOrRight()) {
                    origArrangeMember.setRightChildNode("0");
                }
                this.updateSimple(origArrangeMember);
            }
        }


        if (this.iTableMemberMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    /**
     * 删除
     * @param bo
     * @return
     */
    @Override
    public TableMember delete(TableMember bo) {
        if (null == bo.getId()){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "Id不能为空！", "", null);
        }

        bo = this.select(bo);

        //判断要删除的会员下面是否有其他会员(会员上下级关系根据arrangeId区分)
        TableMember queryParam = new TableMember();
        queryParam.setState(InvestConstants.MemberState.VALID);
        queryParam.setArrangeId(bo.getMemberId());
        List<TableMember> tableMembers = queryList(queryParam);
        if (!CollectionUtils.isEmpty(tableMembers)) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "存在子会员，不允许删除！", "", null);
        }

        //预留删除订单
        log.info("删订单");
        TableOrder tableOrder = new TableOrder();
        tableOrder.setMemberId(bo.getMemberId());
        tableOrder.setState(1);
        List<TableOrder> tableOrders = iTableOrderBusiSV.queryList(tableOrder, null);
        for (TableOrder order : tableOrders) {
            this.iTableOrderBusiSV.delete(order);
        }

        //更新会员状态
        log.info("删member");
        TableMember updateParam = new TableMember();
        updateParam.setState(InvestConstants.MemberState.INVALID);
        updateParam.setId(bo.getId());
        this.updateSimple(updateParam);

        //更新userInfo
        log.info("删userInfo");
        UserInfo userInfo = userService.getUserByUsernameLogin(bo.getMemberId());
        if (userInfo != null) {
            userService.deleteUserInfo(userInfo, new HashMap());
        }

        return bo;
    }

    /**
     * 恢复
     * @param bo
     * @return
     */
    @Override
    public TableMember renew(TableMember bo) {
        return null;
    }

    @Override
    public TableMember select(TableMember bo) {
        return this.iTableMemberMapper.selectByPrimaryKey(bo.getId());
    }

    @Override
    public TableMember selectByMemberId(String memberId) {

        TableMemberExample example = new TableMemberExample();
        TableMemberExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(memberId);
        criteria.andStateEqualTo(InvestConstants.MemberState.VALID);

        List<TableMember> tableMembers = this.iTableMemberMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(tableMembers)) {
            return null;
        }
        return tableMembers.get(0);
    }

    @Override
    public TableMember selectByMemberId4BuyOrder(String memberId) {

        TableMemberExample example = new TableMemberExample();
        TableMemberExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(memberId);
        criteria.andStateNotEqualTo(InvestConstants.MemberState.INVALID);

        List<TableMember> tableMembers = this.iTableMemberMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(tableMembers)) {
            return null;
        }
        return tableMembers.get(0);
    }

    /**
     * 获取会员下一级子会员
     *
     * @param memberId
     * @return
     */
    public List<TableMember> queryChildList(String memberId, Map<String, List<TableMember>> map) {
        return map.get(memberId);
    }


    /**
     * 获取会员下一级子会员
     *
     * @param memberId
     * @return
     */
    @Override
    public List<TableMember> queryChildList(String memberId) {
        TableMember t = new TableMember();
        t.setArrangeId(memberId);
        t.setState(InvestConstants.MemberState.VALID);
        return this.queryList(t);
    }

    /**
     * 获取所有子节点会员
     * 递归
     * 递归
     * 不包括会员本身
     * @param memberId
     * @return
     */
    @Override
    public List<TableMember> queryAllChildList(String memberId) {
        List<TableMember> list = new ArrayList<>();

        List<TableMember> childList = this.queryChildList(memberId);

        for (TableMember tWheatMember : childList) {
            list.add(tWheatMember);
            list.addAll(this.queryAllChildList(tWheatMember.getMemberId()));
        }

        return list;
    }

    /**
     * 获取所有子节点会员
     * 递归
     * 递归
     * 不包括会员本身
     * @param memberId
     * @return
     */
    @Override
    public List<TableMember> queryAllChildList(String memberId, Map<String, List<TableMember>> map) {
        List<TableMember> list = new ArrayList<>();

        List<TableMember> childList = this.queryChildList(memberId, map);

        if (!CollectionUtils.isEmpty(childList)) {

            for (TableMember tWheatMember : childList) {
                list.add(tWheatMember);
                list.addAll(this.queryAllChildList(tWheatMember.getMemberId(), map));
            }
        }
        return list;
    }

    /**
     * 获取所有子节点会员
     * 递归
     *
     * @param memberId
     * @return
     */
    @Override
    public PageInfo<TableMember> queryAllChildPage(String memberId, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Map<String, List<TableMember>> map1 = queryArrangeListMap(new TableMember());
        List<TableMember> list = this.queryAllChildList(memberId, map1);
        PageInfo<TableMember> pageInfo = new PageInfo<>();
        List<TableMember> pagelist = new ArrayList<>();
        int start = (pageNum - 1)*pageSize;
        int last = pageNum * pageSize;
        boolean isLast = false;
        if (last > list.size()) {
            last = list.size();
            isLast = true;
        }
        for (int i = start; i < last; i++) {
            pagelist.add(list.get(i));
        }
        pageInfo.setList(pagelist);
        pageInfo.setTotal(list.size());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setIsLastPage(isLast);
        return pageInfo;
    }

    /**
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     *
     * @param tableMemberTree
     * @return
     */
    @Override
    public TableMemberTree queryAllChildTree(TableMemberTree tableMemberTree, Map<String, List<TableMember>> map) {
        //log.info("获取所有子节点会员(TREE)");

        List<TableMemberTree> list = new ArrayList<>();

        List<TableMember> childList = this.queryChildList(tableMemberTree.getMemberId(), map);

        if(childList != null) {
            for (TableMember tWheatMember : childList) {
                TableMemberTree tWheatMemberTree1 = new TableMemberTree();
                BeanUtils.copyProperties(tWheatMember, tWheatMemberTree1);
                list.add(tWheatMemberTree1);
                tableMemberTree.setChildList(list);
                this.queryAllChildTree(tWheatMemberTree1, map);
            }
        }

        return tableMemberTree;
    }

    /**
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     *
     * @param tableMemberTree
     * @return
     */
    @Override
    public TableMemberTree queryAllChildTree(TableMemberTree tableMemberTree) {
        //log.info("获取所有子节点会员(TREE)");

        List<TableMemberTree> list = new ArrayList<>();

        List<TableMember> childList = this.queryChildList(tableMemberTree.getMemberId());

        for (TableMember tWheatMember : childList) {
            TableMemberTree tWheatMemberTree1 = new TableMemberTree();
            BeanUtils.copyProperties(tWheatMember, tWheatMemberTree1);
            list.add(tWheatMemberTree1);
            tableMemberTree.setChildList(list);
            this.queryAllChildTree(tWheatMemberTree1);
        }

        return tableMemberTree;
    }

    /**
     * 查询指定tree
     *
     * @param memberId
     * @param tableMemberTree
     * @return
     */
    @Override
    public TableMemberTree getChildTree(String memberId, TableMemberTree tableMemberTree) {
        if (tableMemberTree != null) {
            if (memberId.equals(tableMemberTree.getMemberId())) {
                return tableMemberTree;
            } else {
                if (tableMemberTree.getChildList() != null) {
                    for (TableMemberTree tmp : tableMemberTree.getChildList()) {
                        TableMemberTree result = this.getChildTree(memberId, tmp);
                        if (result != null)
                            return result;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取所有子节点会员ID列表
     * 递归
     * 不包括会员本身
     *
     * @param memberId
     * @return
     */
    @Override
    public List<String> queryAllChildMemberIdList(String memberId) {
        List<String> memberIdList = new ArrayList<>();
        Map<String, List<TableMember>> map = queryArrangeListMap(new TableMember());
        List<TableMember> list = this.queryAllChildList(memberId, map);
        for (TableMember tWheatMember : list) {
            memberIdList.add(String.valueOf(tWheatMember.getMemberId()));
        }
        return memberIdList;
    }

    @Override
    public TableMember selectByPhone(String phone) {
        TableMemberExample example = new TableMemberExample();
        TableMemberExample.Criteria criteria = example.createCriteria();

        criteria.andPhoneEqualTo(phone);
        criteria.andStateEqualTo(InvestConstants.MemberState.VALID);

        List<TableMember> tWheatMemberList = this.iTableMemberMapper.selectByExample(example);
        if (tWheatMemberList != null && tWheatMemberList.size() >= 1) {
            return tWheatMemberList.get(0);
        }
        return null;
    }

    @Override
    public List<TableMember> queryList(TableMember bo) {
        return queryList(bo, null);
    }

    @Override
    public List<TableMember> queryList(TableMember bo, Map<String, Object> map) {

        TableMemberExample example = new TableMemberExample();
        TableMemberExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (StringUtils.isNotEmpty(bo.getReferenceId())) {
            criteria.andReferenceIdEqualTo(bo.getReferenceId());
        }
        if (StringUtils.isNotEmpty(bo.getArrangeId())) {
            criteria.andArrangeIdEqualTo(bo.getArrangeId());
        }
        if (null != bo.getLeftOrRight()) {
            criteria.andLeftOrRightEqualTo(bo.getLeftOrRight());
        }
        if (StringUtils.isNotEmpty(bo.getLeftChildNode())) {
            criteria.andLeftChildNodeEqualTo(bo.getLeftChildNode());
        }
        if (StringUtils.isNotEmpty(bo.getRightChildNode())) {
            criteria.andRightChildNodeEqualTo(bo.getRightChildNode());
        }
        if (null != bo.getRegisterFrom()) {
            criteria.andRegisterFromEqualTo(bo.getRegisterFrom());
        }
        if (null != bo.getPhone()) {
            criteria.andPhoneEqualTo(bo.getPhone());
        }
        if (StringUtils.isNotEmpty(bo.getPassword())) {
            criteria.andPasswordEqualTo(bo.getPassword());
        }
        if (null != bo.getLevel()) {
            criteria.andLevelEqualTo(bo.getLevel());
        }
        if (null != bo.getmRank()) {
            criteria.andMRankEqualTo(bo.getmRank());
        }
        if (null != bo.getFlag()) {
            criteria.andFlagEqualTo(bo.getFlag());
        }
        if (StringUtils.isNotEmpty(bo.getState())) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (null != bo.getAccountMoney()) {
            criteria.andAccountMoneyEqualTo(bo.getAccountMoney());
        }
        if (null != bo.getAccountPointAvailable()) {
            criteria.andAccountPointAvailableEqualTo(bo.getAccountPointAvailable());
        }
        if (null != bo.getAccountPointFreeze()) {
            criteria.andAccountPointFreezeEqualTo(bo.getAccountPointFreeze());
        }
        if (null != bo.getAccountDjPoint()) {
            criteria.andAccountDjPointEqualTo(bo.getAccountDjPoint());
        }
        if (null != bo.getAccountJsyPoint()) {
            criteria.andAccountJsyPointEqualTo(bo.getAccountJsyPoint());
        }
        if (null != bo.getAutFlag()) {
            criteria.andAutFlagEqualTo(bo.getAutFlag());
        }
        if (StringUtils.isNotEmpty(bo.getReallyName())) {
            criteria.andReallyNameEqualTo(bo.getReallyName());
        }
        if (StringUtils.isNotEmpty(bo.getCartId())) {
            criteria.andCartIdEqualTo(bo.getCartId());
        }
        if (StringUtils.isNotEmpty(bo.getBankCardId())) {
            criteria.andBankCardIdEqualTo(bo.getBankCardId());
        }
        if (StringUtils.isNotEmpty(bo.getBankName())) {
            criteria.andBankNameEqualTo(bo.getBankName());
        }
        if (StringUtils.isNotEmpty(bo.getBankOpenAre())) {
            criteria.andBankOpenAreEqualTo(bo.getBankOpenAre());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg1())) {
            criteria.andCartImg1EqualTo(bo.getCartImg1());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg2())) {
            criteria.andCartImg2EqualTo(bo.getCartImg2());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg3())) {
            criteria.andCartImg3EqualTo(bo.getCartImg3());
        }
        if (StringUtils.isNotEmpty(bo.getBankImg1())) {
            criteria.andBankImg1EqualTo(bo.getBankImg1());
        }
        if (StringUtils.isNotEmpty(bo.getBankImg2())) {
            criteria.andBankImg2EqualTo(bo.getBankImg2());
        }
        if (null != bo.getRegisterTime()) {
            criteria.andRegisterTimeEqualTo(bo.getRegisterTime());
        }
        if (null != bo.getLastLoginTime()) {
            criteria.andLastLoginTimeEqualTo(bo.getLastLoginTime());
        }
        if (null != bo.getPerfomanceTime()) {
            criteria.andPerfomanceTimeEqualTo(bo.getPerfomanceTime());
        }

        criteria.andStateNotEqualTo(InvestConstants.MemberState.INIT);

        if (null != map) {
            if (null != map.get("REGISTER_TIME_START")) {
                criteria.andRegisterTimeGreaterThanOrEqualTo((Date)map.get("REGISTER_TIME_START"));
            }
            if (null != map.get("REGISTER_TIME_END")) {
                criteria.andRegisterTimeLessThanOrEqualTo((Date)map.get("REGISTER_TIME_END"));
            }
            if (null != map.get("ORDER")) {
                example.setOrderByClause((String)map.get("ORDER"));
            }
        }

        return this.iTableMemberMapper.selectByExample(example);
    }

    @Override
    public Map<String, TableMember> queryListMap(TableMember bo) {
        List<TableMember> list = this.queryList(new TableMember());
        Map<String, TableMember> map = new HashMap<>();
        for(TableMember tableMember : list) {
            map.put(tableMember.getMemberId(), tableMember);
        }
        return map;
    }

    /**
     * 根据arrange分组
     * @param bo
     * @return
     */
    @Override
    public Map<String, List<TableMember>> queryArrangeListMap(TableMember bo) {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("ORDER", "ARRANGE_ID");
        TableMember memberQuery = new TableMember();
        memberQuery.setState(InvestConstants.MemberState.VALID);
        List<TableMember> list = this.queryList(memberQuery, mapQuery);
        Map<String, List<TableMember>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            String arrangeId = list.get(0).getArrangeId();
            List<TableMember> arrangeList = new ArrayList<>();
            for (TableMember tableMember : list) {
                if (!arrangeId.equals(tableMember.getArrangeId())) {
                    //进行下一个
                    map.put(arrangeId, arrangeList);
                    arrangeId = tableMember.getArrangeId();
                    arrangeList = new ArrayList<>();
                }
                arrangeList.add(tableMember);
            }
        }
        return map;
    }


    /**
     * parent 是否是bo的父节点，可跨级别
     *
     * @param bo
     * @param parentMemberId
     * @return
     */
    @Override
    public boolean checkParent(TableMember bo, String parentMemberId) {
        return this.checkParent(null, bo ,parentMemberId);
    }

    /**
     * parent 是否是bo的父节点，可跨级别
     *
     * @param bo
     * @param parentMemberId
     * @return
     */
    public boolean checkParent(Map<String, TableMember> map, TableMember bo, String parentMemberId) {
        if (map == null) {
            map = this.queryListMap(new TableMember());
        }
        TableMember parent = map.get(bo.getArrangeId());
        if (parent == null) {
            return false;
        }
        if (parent.getMemberId().equals(parentMemberId)) {
            return true;
        }
        return this.checkParent(map, parent, parentMemberId);
    }

    /**
     * 数据列表分页
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TableMember> queryListPage(TableMember bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取会员管理表参数bo：{}", JSON.toJSON(bo));
        log.info("获取会员管理表参数pageNum：{}", pageNum);
        log.info("获取会员管理表参数pageSize：{}", pageSize);
        log.info("获取会员管理表参数map：{}", JSON.toJSON(map));
        
        TableMemberExample example = new TableMemberExample();
        TableMemberExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (StringUtils.isNotEmpty(bo.getReferenceId())) {
            criteria.andReferenceIdEqualTo(bo.getReferenceId());
        }
        if (StringUtils.isNotEmpty(bo.getArrangeId())) {
            criteria.andArrangeIdEqualTo(bo.getArrangeId());
        }
        if (null != bo.getLeftOrRight()) {
            criteria.andLeftOrRightEqualTo(bo.getLeftOrRight());
        }
        if (StringUtils.isNotEmpty(bo.getLeftChildNode())) {
            criteria.andLeftChildNodeEqualTo(bo.getLeftChildNode());
        }
        if (StringUtils.isNotEmpty(bo.getRightChildNode())) {
            criteria.andRightChildNodeEqualTo(bo.getRightChildNode());
        }
        if (null != bo.getRegisterFrom()) {
            criteria.andRegisterFromEqualTo(bo.getRegisterFrom());
        }
        if (StringUtils.isNotEmpty(bo.getPhone())) {
            criteria.andPhoneEqualTo(bo.getPhone());
        }
        if (StringUtils.isNotEmpty(bo.getPassword())) {
            criteria.andPasswordEqualTo(bo.getPassword());
        }
        if (null != bo.getLevel()) {
            criteria.andLevelEqualTo(bo.getLevel());
        }
        if (null != bo.getmRank()) {
            criteria.andMRankEqualTo(bo.getmRank());
        }
        if (null != bo.getFlag()) {
            criteria.andFlagEqualTo(bo.getFlag());
        }
        if (null != bo.getAccountMoney()) {
            criteria.andAccountMoneyEqualTo(bo.getAccountMoney());
        }
        if (null != bo.getAccountPointAvailable()) {
            criteria.andAccountPointAvailableEqualTo(bo.getAccountPointAvailable());
        }
        if (null != bo.getAccountPointFreeze()) {
            criteria.andAccountPointFreezeEqualTo(bo.getAccountPointFreeze());
        }
        if (null != bo.getAccountDjPoint()) {
            criteria.andAccountDjPointEqualTo(bo.getAccountDjPoint());
        }
        if (null != bo.getAccountJsyPoint()) {
            criteria.andAccountJsyPointEqualTo(bo.getAccountJsyPoint());
        }
        if (null != bo.getAutFlag()) {
            criteria.andAutFlagEqualTo(bo.getAutFlag());
        }
        if (StringUtils.isNotEmpty(bo.getReallyName())) {
            criteria.andReallyNameEqualTo(bo.getReallyName());
        }
        if (StringUtils.isNotEmpty(bo.getCartId())) {
            criteria.andCartIdEqualTo(bo.getCartId());
        }
        if (StringUtils.isNotEmpty(bo.getBankCardId())) {
            criteria.andBankCardIdEqualTo(bo.getBankCardId());
        }
        if (StringUtils.isNotEmpty(bo.getBankName())) {
            criteria.andBankNameEqualTo(bo.getBankName());
        }
        if (StringUtils.isNotEmpty(bo.getBankOpenAre())) {
            criteria.andBankOpenAreEqualTo(bo.getBankOpenAre());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg1())) {
            criteria.andCartImg1EqualTo(bo.getCartImg1());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg2())) {
            criteria.andCartImg2EqualTo(bo.getCartImg2());
        }
        if (StringUtils.isNotEmpty(bo.getCartImg3())) {
            criteria.andCartImg3EqualTo(bo.getCartImg3());
        }
        if (StringUtils.isNotEmpty(bo.getBankImg1())) {
            criteria.andBankImg1EqualTo(bo.getBankImg1());
        }
        if (StringUtils.isNotEmpty(bo.getBankImg2())) {
            criteria.andBankImg2EqualTo(bo.getBankImg2());
        }
        if (null != bo.getRegisterTime()) {
            criteria.andRegisterTimeEqualTo(bo.getRegisterTime());
        }
        if (null != bo.getLastLoginTime()) {
            criteria.andLastLoginTimeEqualTo(bo.getLastLoginTime());
        }
        if (null != bo.getPerfomanceTime()) {
            criteria.andPerfomanceTimeEqualTo(bo.getPerfomanceTime());
        }

        criteria.andStateNotEqualTo(InvestConstants.MemberState.INIT);

        example.setOrderByClause("register_time desc");

        PageInfo<TableMember> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberMapper.selectByExample(example));

        log.info("获取会员管理表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 注册
     *
     * @param m
     * @return
     */
    @Override
    public TableMember regist(TableMember m, int registerFrom) {
        String phone = m.getPhone();
        String referenceId = m.getReferenceId();
        String arrangeId = m.getArrangeId();

        if (StringUtils.isEmpty(phone)) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号不能为空！", "", null);
        }

        if (11 != phone.length()) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号必须是11位！", "", null);
        }

        if (phone.length() <= 6) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号位数错误！", "", null);
        }

        TableMember checkMember = this.selectByPhone(phone);
        if (checkMember != null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号已注册！", "", null);
        }
        TableMember referenceIdMember = this.selectByMemberId(referenceId);

        if (referenceIdMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人不存在！", "", null);
        }
        try {
            //判断左右区类型
            if (3 == m.getLeftOrRight() || 4 == m.getLeftOrRight()) {
                log.info("自动滑落");
                //3 左区滑落
                //4 右区滑落
                String result = PythonUtil3.runPy(python_path, "get_leaf_node.py", arrangeId, "");
                if (StringUtils.isEmpty(result)) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "根据安置人" + arrangeId + "获取自动滑落会员编号失败", "", null);
                }
                result = result.replace("(", "").replace(")", "").trim();
                if (3 == m.getLeftOrRight()) {
                    arrangeId = result.split(",")[0].replace("'", "").trim();
                    m.setLeftOrRight(1);
                    log.info("自动滑落左区，推荐人ID={}", arrangeId);
                }
                if (4 == m.getLeftOrRight()) {
                    arrangeId = result.split(",")[1].replace("'", "").trim();
                    m.setLeftOrRight(2);
                    log.info("自动滑落右区，推荐人ID={}", arrangeId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "根据安置人" + arrangeId + "获取自动滑落会员编号失败", "", null);
        }

        //判断安置人
        TableMember arrangeMemeber = this.selectByMemberId(arrangeId);

        if (arrangeMemeber == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "安置人不存在！", "", null);
        }


        //校验安置人
        checkArrange(m, arrangeMemeber);

        String password = StringUtils.isEmpty(m.getPassword()) ? phone.substring(phone.length() - 6) : m.getPassword();

        TableMember member = new TableMember();
        member.setPhone(phone);
        member.setPassword(Md5Util.md5Hex(password));
        member.setReferenceId(referenceId);
        member.setLevel(0);
        member.setReallyName(m.getReallyName());
        member.setRegisterFrom(registerFrom); //注册来源：后台
        member = this.insert(member);
        member.setMemberId("926" + String.format("%08d", member.getId()));
        member.setLeftChildNode("");
        member.setRightChildNode("");


        BigDecimal zero = new BigDecimal(0);

        member.setmRank(0);
        member.setFlag("0001");
        member.setAccountMoney(zero);
        member.setAccountDjPoint(zero);
        member.setAccountJsyPoint(zero);
        member.setAccountPointAvailable(zero);
        member.setAccountPointFreeze(zero);
        member.setReportingAmount(zero);
        member.setAutFlag(0);
        member.setLastLoginTime(DateUtils.getSysDate());
        member.setPerfomanceTime(DateUtils.getSysDate());
        member.setLeftOrRight(m.getLeftOrRight());
        member.setArrangeId(arrangeId);
        this.updateSimple(member);

        //后台注册
        if (2 == member.getRegisterFrom()){
            //更新安置人
            //判断左区
            if (1 == m.getLeftOrRight()) {
                arrangeMemeber.setLeftChildNode(member.getMemberId());
            }

            //判断右区，如果存在不允许注册
            if (2 == m.getLeftOrRight()) {
                arrangeMemeber.setRightChildNode(member.getMemberId());
            }
            this.updateSimple(arrangeMemeber);
        }

        //创建user信息，用于登陆系统
        UserInfo user = new UserInfo();
        user.setPassword(password);
        user.setUsername(member.getMemberId());
        user.setPhone(member.getPhone());
        user.setName(StringUtils.isEmpty(member.getReallyName())?member.getMemberId():member.getReallyName());
        if (member.getRegisterFrom() == 2) {
            user.setLoginFlag("1");
        }else{
            user.setLoginFlag("0");
        }
        user.setDelFlag("0");
        user.setUserType("1");
        //会员默认不允许登陆，购买完报单商品后才可以登陆

        List<RoleInfo> roleList = new ArrayList<RoleInfo>();
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(3l);
        roleList.add(roleInfo);

        user.setRoleList(roleList);

        Map map = Maps.newHashMap();
        map.put("userId", user.getId());
        userService.save(user, map);

        return member;
    }

    /**
     * 校验安置人是否可用
     * @param member
     * @param arrangeMember
     * @return
     */
    public boolean checkArrange(TableMember member, TableMember arrangeMember){
        //判断左区，如果存在不允许注册
        if (1 == member.getLeftOrRight()) {
            if(!"0".equals(arrangeMember.getLeftChildNode()) && !org.springframework.util.StringUtils.isEmpty(arrangeMember.getLeftChildNode())) {
                //判断左区会员是否有效
                if (this.selectByMemberId(arrangeMember.getLeftChildNode()) != null) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "安置人左区已经存在会员！", "", null);
                }
            }
        }

        //判断右区，如果存在不允许注册
        if (2 == member.getLeftOrRight()){
            if(!"0".equals(arrangeMember.getRightChildNode()) && !org.springframework.util.StringUtils.isEmpty(arrangeMember.getRightChildNode())) {
                if (this.selectByMemberId(arrangeMember.getRightChildNode()) != null) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "安置人右区已经存在会员！", "", null);
                }
            }
        }

        return true;
    }

    /**
     * 修改密码（密码为空则为密码重置）
     *
     * @param bo
     * @return
     */
    @Override
    public TableMember changePwd(TableMember bo, String oldPassword) {

        String pwd = bo.getPassword();

        TableMember tableMemberOrig;
        try {
            tableMemberOrig = this.selectByMemberId(bo.getMemberId());
        }catch (Exception e) {
            tableMemberOrig = this.select(bo);
        }

        if (tableMemberOrig == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }

        if (StringUtils.isEmpty(pwd)) {
            pwd = tableMemberOrig.getPhone().substring(tableMemberOrig.getPhone().length()-6);
        } else {
            //校验密码是否正确
            if (!tableMemberOrig.getPassword().equals(Md5Util.md5Hex(oldPassword))) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "原密码不正确！", "", null);
            }
        }

        pwd = Md5Util.md5Hex(pwd);

        bo.setPassword(pwd);
        bo.setId(tableMemberOrig.getId());
        this.updateSimple(bo);

        UserInfo userInfo = this.userService.getUserByUsernameLogin(tableMemberOrig.getMemberId());
        userInfo.setPassword(pwd);

        this.userService.update(userInfo, null);

        return bo;
    }

    /**
     * 实名认证审核
     *
     * @return
     */
    @Override
    public TableMember audit(Integer id, Integer autFlag) {
        TableMember tableMember = new TableMember();
        tableMember.setId(id);
        tableMember.setAutFlag(autFlag);
        return this.updateSimple(tableMember) ;
    }

    /**
     * 导出会员文件
     *
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public HSSFWorkbook exportFile(TableMember bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        PageInfo<TableMember> pageInfo = this.queryListPage(bo, 1, 10000000, map);

        List<TableMember> list = new ArrayList<>();
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            list = pageInfo.getList();
        } else {
            System.out.println("没有数据");
        }

        String sheetName = "会员";
        String[] title = {"会员编号", "会员姓名", "推荐人编号", "安置人编号", "左右区标识", "电话号码", "会员级别", "会员头衔"
                , "现金", "积分(可用)", "积分(冻结)", "购物积分", "保值积分", "实名认证标识", "身份证号码", "银行卡号"
                ,"开户行名字", "银行卡开户行地址", "注册时间"};
        String[][] values = new String[list.size()+1][title.length];

        int i = 0;
        for (TableMember member : list) {
            values[i][0] = member.getMemberId();
            values[i][1] = member.getReallyName();
            values[i][2] = member.getReferenceId();
            values[i][3] = member.getArrangeId();
            values[i][4] = DictUtils.getDictLabel(String.valueOf(member.getLeftOrRight()),"leftOrRight","");
            values[i][5] = member.getPhone();
            values[i][6] = DictUtils.getDictLabel(String.valueOf(member.getLevel()),"level","");
            values[i][7] = DictUtils.getDictLabel(String.valueOf(member.getmRank()),"mRank","");
            values[i][8] = String.valueOf(member.getAccountMoney());
            values[i][9] = String.valueOf(member.getAccountPointAvailable());
            values[i][10] = String.valueOf(member.getAccountPointFreeze());
            values[i][11] = String.valueOf(member.getAccountDjPoint());
            values[i][12] = String.valueOf(member.getAccountJsyPoint());
            values[i][13] = DictUtils.getDictLabel(String.valueOf(member.getAutFlag()),"autFlag","");
            values[i][14] = member.getCartId();
            values[i][15] = member.getBankCardId();
            values[i][16] = member.getBankName();
            values[i][17] = member.getBankOpenAre();
            values[i][18] = DateUtils.formatDateTime(member.getRegisterTime());
            i++;
        }

        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, values, null);
        return wb;
    }

    /**
     * 统计金额
     */
    @Override
    public List<TableMember> statisticMoney() {
        return this.iTableMemberMapperDZ.statisticMoney();
    }

    /**
     * 更新是否允许登陆状态 1允许 0不允许
     *
     * @param memberId
     * @param state
     * @return
     */
    @Override
    public void updateLoginState(String memberId, String state) {
        UserInfo userInfo = this.userService.getUserByUserName(memberId);
        userInfo.setLoginFlag(state);

        this.userService.update(userInfo, null);
    }
}
