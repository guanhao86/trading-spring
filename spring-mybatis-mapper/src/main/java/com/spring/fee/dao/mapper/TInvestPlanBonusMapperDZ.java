package com.spring.fee.dao.mapper;

import com.spring.fee.model.TInvestPlanBonus;
import com.spring.fee.model.TInvestPlanBonusDZ;

import java.util.List;

public interface TInvestPlanBonusMapperDZ {

    /**
     * 统计收益情况
     */
    List<TInvestPlanBonusDZ> statisticGroupByMemberId();

    List<TInvestPlanBonusDZ> statisticMemberSum(TInvestPlanBonusDZ tInvestPlanBonusDZ);

}