package com.spring.fee.service;

import com.spring.fee.model.TInvestMemberMoneyConfig;

import java.util.List;
import java.util.Map;

/**
 * 会员奖金配置服务
 */
public interface ITInvestMemberMoneyConfigBusiSV {

    TInvestMemberMoneyConfig select(String level, String planId);

    TInvestMemberMoneyConfig selectById(Long id);

    TInvestMemberMoneyConfig update(TInvestMemberMoneyConfig tInvestMemberMoneyConfig);

    List<TInvestMemberMoneyConfig> queryList(TInvestMemberMoneyConfig iInvestMemberMoneyConfig, String orderBy);

    /**
     * 获取配置map
     * @return
     */
    Map<String, TInvestMemberMoneyConfig> queryMap();

    /**
     * 获取配置map
     * level:List
     * @return
     */
    Map<String, List<TInvestMemberMoneyConfig>> queryMap2();
}
