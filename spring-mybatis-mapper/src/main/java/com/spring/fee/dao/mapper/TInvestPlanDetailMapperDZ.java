package com.spring.fee.dao.mapper;

import com.spring.fee.model.TInvestPlanDetail;
import com.spring.fee.model.TInvestPlanDetailDZ;

import java.util.List;

public interface TInvestPlanDetailMapperDZ {
    /**
     * 获取正在执行主计划列表(memberId, planId分组)
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> getMainRunningCount(TInvestPlanDetailDZ tInvestPlanDetailDZ);

    /**
     * 获取正在执行主计划列表（memberId分组）
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> getMainRunningCount2(TInvestPlanDetailDZ tInvestPlanDetailDZ);


    /**
     * 统计业绩
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> statisticPlanAmount(TInvestPlanDetailDZ tInvestPlanDetailDZ);

    /**
     * 获取主计划执行中数量统计，用于可购买计划校验
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> getMainRunningPlanCount(TInvestPlanDetailDZ tInvestPlanDetailDZ);
}