package com.spring.fee.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;

import java.util.List;
import java.util.Map;

/**
 * 粮仓计划实例服务
 */
public interface ITInvestPlanMainBusiSV {

    TInvestPlanMain insert(TInvestPlanMain investPlanMain);

    TInvestPlanMain update(TInvestPlanMain investPlanMain);

    TInvestPlanMain delete(TInvestPlanMain investPlanMain);

    TInvestPlanMain select(TInvestPlanMain investPlanMain);

    PageInfo<TInvestPlanMain> queryList(TInvestPlanMain investPlanMain, Integer pageNum, Integer pageSize, Map<String ,Object> map);

    PageInfo<TInvestPlanMainDZ> queryMainAndDetailList(TInvestPlanMain investPlanMain, Integer pageNum, Integer pageSize, Map<String ,Object> map);

    List<TInvestPlanMain> queryList(TInvestPlanMainDZ investPlanMain);

    /**
     * 创建主计划（新建）
     * @param investPlanMain
     * @return
     */
    TInvestPlanMain createPlanMainNew(TInvestPlanMain investPlanMain);

    /**
     * 创建主计划（续仓）
     * @param investPlanMain
     * @return
     */
    TInvestPlanMain createPlanMainSecond(TInvestPlanMain investPlanMain);

    /**
     * 获取队列并生成计划
     * @return
     */
    TInvestPlanMain createPlan(int size);

    /**
     * 指定队列生成计划
     * @param tInvestQueue
     * @return
     */
    TInvestPlanMainDZ createPlan(TInvestQueue tInvestQueue);

    /**
     * 校验是否可以续仓并返回副仓结果
     * @param investPlanMain
     * @return
     */
    TInvestPlanDetail checkCanSecondAndReturnDetail(TInvestPlanMain investPlanMain);

    /**
     * 获取执行中计划列表，逐条调用结算方法
     * @return
     */
    void runSettle();

    TInvestSettleRecord runSettleV2();

    /**
     * 结算方法
     * @return
     */
    TInvestPlanMainDZ settle(TInvestPlanMain tInvestPlanMain);

    /**
     * 获取该会员可以购买计划的列表
     * 只允许买一个级别计划
     * @param tWheatMember
     * @return
     */
    //List<TInvestPlanConfig> getCanBuyPlanConfigList(TWheatMember tWheatMember);

    /**
     * 获取该会员可以购买计划的列表
     * 购买了高级别计划，不允许买低级别计划
     * @param tWheatMember
     * @return
     */
    List<TInvestPlanConfig> getCanBuyPlanConfigListV2(TWheatMember tWheatMember);

    /**
     * 校验该会员是否可以购买该计划
     * @param memberId
     * @param planId
     * @return
     */
    TInvestPlanConfig checkCanBuyPlan(String memberId, String planId);

    /**
     * 校验是否存在其他计划的执行中主仓数据
     * @param memberId
     * @param planId
     * @return
     */
    boolean checkOtherPlanIng(String memberId, String planId);

    /**
     * 统计会员购买粮仓情况 执行中，所有，投入中
     * @param memberId
     * @return
     */
    TInvestPlanMainStatistic statisticPlanCount(String memberId);

    /**
     * 统计粮仓数量和复投数量
     * @param tInvestPlanMainStatistic
     * @return
     */
    TInvestPlanMainStatistic statisticPlanCount2(TInvestPlanMainStatistic tInvestPlanMainStatistic);

    /**
     * 统计会员的所有粮仓和当前粮仓数量
     * @return
     */
    List<TInvestPlanMainStatistic> statisticPlanCountGroupByMemeber(TInvestPlanMainStatistic tInvestPlanMainStatistic);

    void autoBuy(TInvestPlanMainDZ tInvestPlanMainDZ);

    public TInvestPlanDetailDZ getMaxRuningPlan(String memberId);
}
