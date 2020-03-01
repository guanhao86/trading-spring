package com.spring.fee.service;

import com.spring.fee.model.TInvestMemberConfig;

import java.util.List;

/**
 * 会员投资配置信息服务
 */
public interface ITInvestMemberConfigBusiSV {

    TInvestMemberConfig insert(TInvestMemberConfig tInvestMemberConfig);

    TInvestMemberConfig update(TInvestMemberConfig tInvestMemberConfig);

    TInvestMemberConfig modify(TInvestMemberConfig tInvestMemberConfig);

    String selectByMemberId(String memberId, String configCode);

    /**
     * 获取固定属性数量
     * @param memberList
     * @param configCode
     * @return
     */
    int selectByMemberIdList(List<String> memberList, String configCode);

}
