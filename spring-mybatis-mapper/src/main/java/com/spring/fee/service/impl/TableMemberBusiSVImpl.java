package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.dao.mapper.TableMemberMapper;
import com.spring.fee.model.*;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberLevelChangeDetailBusiSV;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
        if (tWheatMemberList != null && tWheatMemberList.size() >= 1) {
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
        String phone=m.getPhone();
        String referenceId=m.getReferenceId();
        String arrangeId = m.getArrangeId();

        if (StringUtils.isEmpty(phone)) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号不能为空！", "", null);
        }

        if (phone.length() <= 6) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号位数错误！", "", null);
        }

        TableMember  checkMember = this.selectByPhone(phone);
        if(checkMember!=null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号已注册！", "", null);
        }
        TableMember referenceIdMember = this.selectByMemberId(referenceId);

        if(referenceIdMember==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人不存在！", "", null);
        }
        try {
            //判断左右区类型
            if (3 == m.getLeftOrRight() || 4 == m.getLeftOrRight()) {
                log.info("自动滑落");
                //3 左区滑落
                //4 右区滑落
                String result = PythonUtil3.runPy("/usr/maitao/run_python", "get_leaf_node.py", arrangeId, "");
                if (StringUtils.isEmpty(result)) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "根据安置人"+arrangeId+"获取自动滑落会员编号失败","", null);
                }
                result = result.replace("(", "").replace(")", "").trim();
                if (3 == m.getLeftOrRight()) {
                    arrangeId = result.split(",")[0].replace("'","").trim();
                    m.setLeftOrRight(1);
                    log.info("自动滑落左区，推荐人ID={}", arrangeId);
                }
                if (4 == m.getLeftOrRight()) {
                    arrangeId = result.split(",")[1].replace("'","").trim();
                    m.setLeftOrRight(2);
                    log.info("自动滑落右区，推荐人ID={}", arrangeId);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "根据安置人"+arrangeId+"获取自动滑落会员编号失败","", null);
        }

        //判断安置人
        TableMember arrangeMemeber = this.selectByMemberId(arrangeId);

        if(arrangeMemeber==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "安置人不存在！","", null);
        }

        //判断左区，如果存在不允许注册
        if (1 == m.getLeftOrRight()) {
            if(!"0".equals(arrangeMemeber.getLeftChildNode()) && !org.springframework.util.StringUtils.isEmpty(arrangeMemeber.getLeftChildNode())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人左区已经存在会员，不允许注册！", "", null);
            }
        }

        //判断右区，如果存在不允许注册
        if (2 == m.getLeftOrRight()){
            if(!"0".equals(arrangeMemeber.getRightChildNode()) && !org.springframework.util.StringUtils.isEmpty(arrangeMemeber.getRightChildNode())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人右区已经存在会员，不允许注册！", "", null);
            }
        }

        String password = StringUtils.isEmpty(m.getPassword())?phone.substring(phone.length()-6):m.getPassword();

        TableMember member = new TableMember();
        member.setPhone(phone);
        member.setPassword(Md5Util.md5Hex(password));
        member.setReferenceId(referenceId);
        member.setLevel(0);
        member = this.insert(member);
        member.setMemberId("926"+String.format("%08d", member.getId()));
        member.setLeftChildNode("");
        member.setRightChildNode("");
        member.setRegisterFrom(registerFrom); //注册来源：后台
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

        //创建user信息，用于登陆系统
        UserInfo user = new UserInfo();
        user.setPassword(password);
        user.setUsername(member.getMemberId());
        user.setPhone(member.getPhone());
        user.setName(StringUtils.isEmpty(member.getReallyName())?member.getMemberId():member.getReallyName());
        user.setLoginFlag("1");
        user.setDelFlag("0");
        user.setUserType("1");

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
        this.update(bo);

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
        return this.update(tableMember) ;
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

        String sheetName = "传输计划";
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
            values[i][4] = DictUtils.getDictLabel(String.valueOf(member.getLevel()),"leftOrRight","");
            values[i][5] = member.getPhone();
            values[i][6] = DictUtils.getDictLabel(String.valueOf(member.getLevel()),"level","");
            values[i][7] = DictUtils.getDictLabel(String.valueOf(member.getLevel()),"mRank","");
            values[i][8] = String.valueOf(member.getAccountMoney());
            values[i][9] = String.valueOf(member.getAccountPointAvailable());
            values[i][10] = String.valueOf(member.getAccountPointFreeze());
            values[i][11] = String.valueOf(member.getAccountDjPoint());
            values[i][12] = String.valueOf(member.getAccountJsyPoint());
            values[i][13] = DictUtils.getDictLabel(String.valueOf(member.getLevel()),"autFlag","");
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
}
