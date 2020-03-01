package com.spring.fee.service;

import com.spring.fee.model.TInvestPlanConfig;

import java.util.List;
import java.util.Map;

/**
 * 粮仓计划配置服务
 */
public interface ITInvestPlanConfigBusiSV {

    /**
     * 获取计划配置详情
     * @param tInvestPlanConfig
     * @return
     */
    TInvestPlanConfig select(TInvestPlanConfig tInvestPlanConfig);

    /**
     * 更新
     * @param tInvestPlanConfig
     * @return
     */
    TInvestPlanConfig update(TInvestPlanConfig tInvestPlanConfig);

    /**
     * 获取计划配置详情
     * @param planId
     * @return
     */
    TInvestPlanConfig selectById(String planId);

    /**
     * 获取计划配置列表
     * @param tInvestPlanConfig
     * @return
     */
    List<TInvestPlanConfig> queryList(TInvestPlanConfig tInvestPlanConfig);

    /**
     * 获取计划配置列表
     * @param tInvestPlanConfig
     * @return
     */
    List<TInvestPlanConfig> queryList(TInvestPlanConfig tInvestPlanConfig, String largeThanPlanId);


    /**
     * 获取计划配置Map
     * @return
     */
    Map<String, TInvestPlanConfig> queryMap();

}
