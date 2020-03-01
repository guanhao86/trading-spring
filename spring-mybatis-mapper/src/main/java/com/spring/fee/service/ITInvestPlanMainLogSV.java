package com.spring.fee.service;

import com.spring.fee.model.TInvestPlanConfig;
import com.spring.fee.model.TInvestPlanMainLog;
import com.spring.fee.model.TInvestPlanMainLogDZ;

import java.util.List;

/**
 * 粮仓计划执行/结算日志服务
 */
public interface ITInvestPlanMainLogSV {

    /**
     * 创建粮仓计划执行日志
     * @param tInvestPlanMainLog
     * @return
     */
    TInvestPlanMainLog insert (TInvestPlanMainLog tInvestPlanMainLog);

    /**
     * 查询结果
     * @param tInvestPlanMainLog
     * @return
     */
    List<TInvestPlanMainLog> queryList(TInvestPlanMainLog tInvestPlanMainLog);

    List<TInvestPlanMainLogDZ> queryList2(TInvestPlanMainLogDZ tInvestPlanMainLog);
}
