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
     * @param accountType 1:充值  2:购物
     * @param amount
     * @param remark
     * @return
     */
    @Override
    public TableMemberAccountDetail changeMoney(String memberId, String accountType, Float amount, String remark) {

        //获取会员信息
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(memberId);

        if (tableMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }

        if (amount == null || amount <= 0) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "金额不正确！", "", null);
        }

        Float accountMoney = tableMember.getAccountMoney();
        TableMemberAccountDetail memberAccountDetail = new TableMemberAccountDetail();
        memberAccountDetail.setBeforeValue(accountMoney);
        if ("1".equals(accountType)) {
            //充值
            memberAccountDetail.setAccountType(1);
            accountMoney += amount;
        }
        if ("2".equals(accountType)) {
            //购物
            memberAccountDetail.setAccountType(1);
            accountMoney -= amount;
            if (accountMoney < 0) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "余额不足！", "", null);
            }
        }

        tableMember.setAccountMoney(accountMoney);
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

        this.changeMoney(fromMemberId, "2", amount1, "转账到会员"+toMemberId+"。备注："+remark);
        this.changeMoney(toMemberId, "1", amount1, "会员"+fromMemberId+"转入。备注："+remark);

        return null;
    }
}
