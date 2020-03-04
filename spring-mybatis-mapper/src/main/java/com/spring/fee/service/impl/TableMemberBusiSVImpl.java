package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberMapper;
import com.spring.fee.model.*;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberLevelChangeDetailBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员管理表服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberBusiSVImpl implements ITableMemberBusiSV {

    @Autowired
    TableMemberMapper iTableMemberMapper;

    @Autowired
    ITableMemberLevelChangeDetailBusiSV iTableMemberLevelChangeDetailBusiSV;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableMember insert(TableMember bo) {
        log.info("创建会员管理表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setRegisterTime(sysdate);
        iTableMemberMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMember update(TableMember bo) {
        if (this.iTableMemberMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMember delete(TableMember bo) {
        if (this.iTableMemberMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
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
    @Override
    public List<TableMember> queryChildList(String memberId) {
        TableMember t = new TableMember();
        t.setReferenceId(memberId);
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
        List<TableMember> list = this.queryAllChildList(memberId);
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

        List<TableMember> tWheatMemberList = this.iTableMemberMapper.selectByExample(example);
        if (tWheatMemberList != null && tWheatMemberList.size() == 1) {
            return tWheatMemberList.get(0);
        }
        return null;
    }

    @Override
    public List<TableMember> queryList(TableMember bo) {

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

        return this.iTableMemberMapper.selectByExample(example);
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
    public TableMember regist(TableMember m) {
        String phone=m.getPhone();
        String referenceId=m.getReferenceId();
        String arrangeId = m.getArrangeId();
        TableMember  checkMember = this.selectByPhone(phone);
        if(checkMember!=null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号已注册！", "", null);
        }
        TableMember referenceIdMember = this.selectByMemberId(referenceId);

        if(referenceIdMember==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人不存在！", "", null);
        }

        //判断安置人
        TableMember arrangeMemeber = this.selectByMemberId(arrangeId);

        if(arrangeMemeber==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人不存在！","", null);
        }

        //判断左区，如果存在不允许注册
        if (1 == m.getLeftOrRight()) {
            if(!"0".equals(arrangeMemeber.getLeftChildNode()) || !org.springframework.util.StringUtils.isEmpty(arrangeMemeber.getLeftChildNode())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人左区已经存在会员，不允许注册！", "", null);
            }
        }

        //判断右区，如果存在不允许注册
        if (2 == m.getLeftOrRight()){
            if(!"0".equals(arrangeMemeber.getRightChildNode()) || !org.springframework.util.StringUtils.isEmpty(arrangeMemeber.getRightChildNode())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人右区已经存在会员，不允许注册！", "", null);
            }
        }

        TableMember member = new TableMember();
        member.setPhone(phone);
        member.setPassword(Md5Util.md5Hex(phone.substring(phone.length()-6)));
        member.setReferenceId(referenceId);
        member.setLevel(0);
        member = this.insert(member);
        member.setMemberId("926"+String.format("%08d", member.getId()));
        member.setLeftChildNode("");
        member.setRightChildNode("");
        member.setRegisterFrom(2); //注册来源：后台
        member.setmRank(0);
        member.setFlag("0001");
        member.setAccountMoney(0f);
        member.setAccountDjPoint(0f);
        member.setAccountJsyPoint(0f);
        member.setAccountPointAvailable(0f);
        member.setAccountPointFreeze(0f);
        member.setReportingAmount(0f);
        member.setAutFlag(0);
        member.setLastLoginTime(DateUtils.getSysDate());
        member.setPerfomanceTime(DateUtils.getSysDate());
        member.setLeftOrRight(m.getLeftOrRight());
        member.setArrangeId(m.getArrangeId());
        this.update(member);

        //更新安置人
        //判断左区
        if (1 == m.getLeftOrRight()) {
            arrangeMemeber.setLeftChildNode(member.getMemberId());
        }

        //判断右区，如果存在不允许注册
        if (2 == m.getLeftOrRight()) {
            arrangeMemeber.setRightChildNode(member.getMemberId());
        }
        this.update(arrangeMemeber);

        return member;
    }
}
