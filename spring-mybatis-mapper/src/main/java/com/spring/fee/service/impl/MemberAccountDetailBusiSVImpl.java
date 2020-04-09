package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberAccountDetailMapper;
import com.spring.fee.model.TableMemberAccountDetail;
import com.spring.fee.model.TableMemberAccountDetailExample;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 账户资金情况变更表服务
 */
@Slf4j
@Service
@Transactional
public class MemberAccountDetailBusiSVImpl implements IMemberAccountDetailBusiSV {

    @Autowired
    TableMemberAccountDetailMapper iTableMemberAccountDetailMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberAccountDetail insert(TableMemberAccountDetail bo) {
        log.info("创建账户资金情况变更表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        iTableMemberAccountDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberAccountDetail update(TableMemberAccountDetail bo) {
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        if (this.iTableMemberAccountDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberAccountDetail delete(TableMemberAccountDetail bo) {
        if (this.iTableMemberAccountDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberAccountDetail select(TableMemberAccountDetail bo) {
        return this.iTableMemberAccountDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 账户变更接口
     *
     * @param memberId
     * @param operType 1:增加  2:扣减
     * @param amount
     * @param remark
     * @param accountType
     * 1：account_money
     * 2：account_point_available
     * 3：account_dj_point
     * 4：account_jys_point
     * @return
     */
    @Override
    public TableMemberAccountDetail changeMoney(String memberId, String operType, Float amount, String remark, Integer accountType) {

        //获取会员信息
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(memberId);

        if (tableMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }

        if (amount == null || amount <= 0) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "金额不正确！", "", null);
        }


        Float accountMoney = tableMember.getAccountMoney();

        if (null == accountType || accountType == 1) {
            accountMoney = tableMember.getAccountMoney();
        }
        if (accountType == 2) {
            accountMoney = tableMember.getAccountPointAvailable();
        }
        if (accountType == 3) {
            accountMoney = tableMember.getAccountDjPoint();
        }
        if (accountType == 4) {
            accountMoney = tableMember.getAccountJsyPoint();
        }

        TableMemberAccountDetail memberAccountDetail = new TableMemberAccountDetail();
        memberAccountDetail.setBeforeValue(accountMoney);
        if ("1".equals(operType)) {
            //充值
            memberAccountDetail.setAccountType(accountType==null?1:accountType);
            accountMoney += amount;
        }
        if ("2".equals(operType)) {
            //购物
            memberAccountDetail.setAccountType(accountType==null?1:accountType);
            accountMoney -= amount;
            if (accountMoney < 0) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "余额不足！", "", null);
            }
        }
        if (null == accountType || accountType == 1) {
            tableMember.setAccountMoney(accountMoney);
        }
        if (accountType == 2) {
            tableMember.setAccountPointAvailable(accountMoney);
        }
        if (accountType == 3) {
            tableMember.setAccountDjPoint(accountMoney);
        }
        if (accountType == 4) {
            tableMember.setAccountJsyPoint(accountMoney);
        }
        this.iTableMemberBusiSV.update(tableMember);

        memberAccountDetail.setModifyTime(DateUtils.getSysDate());
        memberAccountDetail.setMemberId(memberId);
        memberAccountDetail.setRemark(remark);
        memberAccountDetail.setAfterValue(accountMoney);
        this.insert(memberAccountDetail);

        return memberAccountDetail;
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
    public PageInfo<TableMemberAccountDetail> queryListPage(TableMemberAccountDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取账户资金情况变更表参数bo：{}", JSON.toJSON(bo));
        log.info("获取账户资金情况变更表参数pageNum：{}", pageNum);
        log.info("获取账户资金情况变更表参数pageSize：{}", pageSize);
        log.info("获取账户资金情况变更表参数map：{}", JSON.toJSON(map));
        
        TableMemberAccountDetailExample example = new TableMemberAccountDetailExample();
        TableMemberAccountDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getAccountType()) {
            criteria.andAccountTypeEqualTo(bo.getAccountType());
        }
        if (null != bo.getBeforeValue()) {
            criteria.andBeforeValueEqualTo(bo.getBeforeValue());
        }
        if (null != bo.getAfterValue()) {
            criteria.andAfterValueEqualTo(bo.getAfterValue());
        }
        if (null != bo.getModifyTime()) {
            criteria.andModifyTimeEqualTo(bo.getModifyTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andModifyTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andModifyTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }


        example.setOrderByClause("modify_time desc");

        PageInfo<TableMemberAccountDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberAccountDetailMapper.selectByExample(example));
        log.info("获取账户资金情况变更表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 转账
     *
     * @param fromMemberId
     * @param toMemberId
     * @param amount
     * @param remark
     * @return
     */
    @Override
    public TableMemberAccountDetail transfer(String fromMemberId, String toMemberId, String amount, String remark) {

        //查询frommember
        TableMember fromMember = this.iTableMemberBusiSV.selectByMemberId(fromMemberId);
        Float amount1 = 0f;
        try{
            amount1 = Float.parseFloat(amount);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "转账金额格式错误！", "", null);
        }

        if (toMemberId == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "转出会员ID不能为空！", "", null);
        }

        if (fromMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "转出会员不存在！", "", null);
        }

        //查询tomember
        TableMember toMember = this.iTableMemberBusiSV.selectByMemberId(toMemberId);
        if (toMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "转入会员不存在！", "", null);
        }

        this.changeMoney(fromMemberId, "2", amount1, "转账到会员"+toMemberId+"。备注："+remark, null);
        this.changeMoney(toMemberId, "1", amount1, "会员"+fromMemberId+"转入。备注："+remark, null);

        return null;
    }

    /**
     * 账本内部互转
     *
     * @param memberId
     * @param amount
     * @param type     1:奖金可用到现金
     * @return
     */
    @Override
    public TableMemberAccountDetail transferInner(String memberId, String amount, String type) {
        Float amount1 = 0f;
        try{
            amount1 = Float.parseFloat(amount);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "转账金额格式错误！", "", null);
        }

        //转出
        this.changeMoney(memberId, "2", amount1, "账本内转出", type==null?2:Integer.parseInt(type));
        //转入
        this.changeMoney(memberId, "1", amount1, "账本内转入", type==null?1:Integer.parseInt(type));
        return null;
    }
}
