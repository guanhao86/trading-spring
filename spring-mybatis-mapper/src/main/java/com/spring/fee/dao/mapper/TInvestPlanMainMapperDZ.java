package com.spring.fee.dao.mapper;

import com.spring.fee.model.TInvestPlanMainStatistic;

import java.util.List;

public interface TInvestPlanMainMapperDZ {

    TInvestPlanMainStatistic statisticPlanCount(TInvestPlanMainStatistic tInvestPlanMainStatistic);

    /**
     * 统计会员的所有粮仓和当前粮仓数量
     * @return
     */
    List<TInvestPlanMainStatistic> statisticPlanCountGroupByMemeber(TInvestPlanMainStatistic tInvestPlanMainStatistic);

}