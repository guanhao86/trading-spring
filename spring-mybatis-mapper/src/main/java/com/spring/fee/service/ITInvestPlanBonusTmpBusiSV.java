package com.spring.fee.service;

import com.spring.fee.model.TInvestPlanBonusTmp;

import java.util.List;

/**
 * 粮仓奖金计算临时服务
 * 执行计划时，将计划金额和执行时间插入到该表
 */
public interface ITInvestPlanBonusTmpBusiSV {

    TInvestPlanBonusTmp insert(TInvestPlanBonusTmp tInvestPlanBonusTmp);

    TInvestPlanBonusTmp update(TInvestPlanBonusTmp tInvestPlanBonusTmp);

    TInvestPlanBonusTmp delete(TInvestPlanBonusTmp tInvestPlanBonusTmp);

    TInvestPlanBonusTmp select(TInvestPlanBonusTmp tInvestPlanBonusTmp);

    List<TInvestPlanBonusTmp> queryList(TInvestPlanBonusTmp tInvestPlanBonusTmp);

}
