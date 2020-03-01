package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TInvestPlanBonus;
import com.spring.fee.model.TInvestPlanBonusDZ;
import com.spring.fee.model.TInvestPlanBonusTmp;
import com.spring.fee.model.*;

import java.util.List;
import java.util.Map;

/**
 * 粮仓伞下金额记录服务
 * 根据[奖金计算临时表]计算的金额记录，用于解决平级阻断设计模式
 */
public interface ITInvestPlanBonusBusiSV {

    TInvestPlanBonus insert(TInvestPlanBonus tInvestPlanBonus);

    TInvestPlanBonus update(TInvestPlanBonus tInvestPlanBonus);

    TInvestPlanBonus delete(TInvestPlanBonus tInvestPlanBonus);

    TInvestPlanBonus select(TInvestPlanBonus tInvestPlanBonus);

    List<TInvestPlanBonus> queryList(TInvestPlanBonus tInvestPlanBonus);

    PageInfo<TInvestPlanBonus> queryPage(TInvestPlanBonus tInvestPlanBonus, Integer pageNum, Integer pageSize, Map<String ,Object> map);

    /**
     * 计算伞下业绩(递归)
     * @param tInvestPlanBonusTmp
     */
    boolean calculateTmp(TInvestPlanBonusTmp tInvestPlanBonusTmp, String memberId);

    /**
     * 结算奖金，并存入用户账户
     * @param memberId
     * @param amount
     * @param map group 后数据
     */
    boolean calculate(String memberId, String memberLevel, Long amount, Map<String, TInvestPlanBonusDZ> map);

    /**
     * 获取会员奖金收益率  %
     * 根据会员当前级别，正在执行主仓类型及数量 获取指定的收益百分比
     * @return
     */
    Map<String, Double> getMemberBonusEarningRate();

    /**
     * 获取会员奖金收益率  %
     * 根据会员当前级别，正在执行主仓类型及数量 获取指定的收益百分比
     * 与计划无关：1-4,5-6,7 三个档次
     * @return
     */
    Map<String, Double> getMemberBonusEarningRate2();

    /**
     * 计算奖金V2版本（级差算法） 结算算法入口
     * investPlanBonusTmp = null 结算当天数据
     * @return
     */
    void calculateStartV2(Long a);

    /**
     * 统计收益：支持全部收益，历史收益  会员ID必填
     * @param tInvestPlanBonusDZ
     * @return
     */
    List<TInvestPlanBonusDZ> statisticEarnings(TInvestPlanBonusDZ tInvestPlanBonusDZ);

    /**
     * 根据会员分组统计收益
     * @return
     */
    List<TInvestPlanBonusDZ> statisticGroupByMemberId();
}
