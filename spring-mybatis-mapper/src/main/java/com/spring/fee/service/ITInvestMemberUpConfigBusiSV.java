package com.spring.fee.service;

import com.spring.fee.model.TInvestMemberUpConfig;

import java.util.List;
import java.util.Map;

/**
 * 会员升级配置服务
 */
public interface ITInvestMemberUpConfigBusiSV {

    TInvestMemberUpConfig selectById(String levelId);

    TInvestMemberUpConfig select(TInvestMemberUpConfig iInvestMemberUpConfig);

    List<TInvestMemberUpConfig> queryList(TInvestMemberUpConfig iInvestMemberUpConfig);

    /**
     * 获取配置map
     * @return
     */
    Map<String, TInvestMemberUpConfig> queryMap();

}
