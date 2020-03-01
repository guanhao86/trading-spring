package com.spring.fee.service;

import com.spring.fee.model.TInvestPlanRunDetail;

import java.util.List;

/**
 * 粮仓计划执行明细服务
 */
public interface ITInvestPlanRunDetailBusiSV {

    TInvestPlanRunDetail insert(TInvestPlanRunDetail TInvestPlanRunDetail);

    TInvestPlanRunDetail update(TInvestPlanRunDetail TInvestPlanRunDetail);

    TInvestPlanRunDetail delete(TInvestPlanRunDetail TInvestPlanRunDetail);

    TInvestPlanRunDetail select(TInvestPlanRunDetail TInvestPlanRunDetail);

    List<TInvestPlanRunDetail> queryList(TInvestPlanRunDetail TInvestPlanRunDetail);

}
