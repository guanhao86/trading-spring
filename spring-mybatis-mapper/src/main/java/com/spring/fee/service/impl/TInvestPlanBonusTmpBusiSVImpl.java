package com.spring.fee.service.impl;

import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TInvestPlanBonusTmpMapper;
import com.spring.fee.model.TInvestPlanBonusTmp;
import com.spring.fee.model.TInvestPlanBonusTmpExample;
import com.spring.fee.service.ITInvestPlanBonusTmpBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 奖金计算临时服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanBonusTmpBusiSVImpl implements ITInvestPlanBonusTmpBusiSV {

    @Autowired
    TInvestPlanBonusTmpMapper tInvestPlanBonusTmpMapper;

    @Override
    public TInvestPlanBonusTmp insert(TInvestPlanBonusTmp tInvestPlanBonusTmp) {
        Date sysdate = DateUtils.getSysDate();
        tInvestPlanBonusTmp.setCreateTime(sysdate);
        tInvestPlanBonusTmp.setSettleFlag(InvestConstants.Bonus.BONUS_SETTLE_FLAG);
        if (StringUtils.isEmpty(tInvestPlanBonusTmp.getPaymentday())){
            tInvestPlanBonusTmp.setPaymentday(DateUtils.getYYYYMMDD(sysdate));//账期
        }

        if (1 == tInvestPlanBonusTmpMapper.insert(tInvestPlanBonusTmp)) {
            return tInvestPlanBonusTmp;
        }
        return null;
    }

    @Override
    public TInvestPlanBonusTmp update(TInvestPlanBonusTmp tInvestPlanBonusTmp) {
        if (1 == tInvestPlanBonusTmpMapper.updateByPrimaryKeySelective(tInvestPlanBonusTmp)) {
            return tInvestPlanBonusTmp;
        }
        return null;
    }

    @Override
    public TInvestPlanBonusTmp delete(TInvestPlanBonusTmp tInvestPlanBonusTmp) {
        return null;
    }

    @Override
    public TInvestPlanBonusTmp select(TInvestPlanBonusTmp tInvestPlanBonusTmp) {
        return this.tInvestPlanBonusTmpMapper.selectByPrimaryKey(tInvestPlanBonusTmp.getId());
    }

    @Override
    public List<TInvestPlanBonusTmp> queryList(TInvestPlanBonusTmp tInvestPlanBonusTmp) {

        TInvestPlanBonusTmpExample example = new TInvestPlanBonusTmpExample();
        TInvestPlanBonusTmpExample.Criteria criteria = example.createCriteria();

        if (null != tInvestPlanBonusTmp.getId()) {
            criteria.andIdEqualTo(tInvestPlanBonusTmp.getId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getMemberId())) {
            criteria.andMemberIdEqualTo(tInvestPlanBonusTmp.getMemberId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getOrderId())) {
            criteria.andOrderIdEqualTo(tInvestPlanBonusTmp.getOrderId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getType())) {
            criteria.andTypeEqualTo(tInvestPlanBonusTmp.getType());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getPaymentday())) {
            criteria.andPaymentdayEqualTo(tInvestPlanBonusTmp.getPaymentday());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getSettleFlag())) {
            criteria.andSettleFlagEqualTo(tInvestPlanBonusTmp.getSettleFlag());
        }

        //t_invest_plan_main表id字段
        if (StringUtils.isNotEmpty(tInvestPlanBonusTmp.getPlanMainId())) {
            criteria.andPlanMainIdEqualTo(tInvestPlanBonusTmp.getPlanMainId());
        }

        return this.tInvestPlanBonusTmpMapper.selectByExample(example);
    }
}
