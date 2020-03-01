package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TInvestPlanDetail;
import com.spring.fee.model.TInvestPlanDetailDZ;
import com.spring.fee.model.TInvestPlanMain;

import java.util.List;
import java.util.Map;

/**
 * 粮仓计划分仓实例服务
 */
public interface ITInvestPlanDetailBusiSV {

    TInvestPlanDetail insert(TInvestPlanDetail investPlanDetail);

    TInvestPlanDetail update(TInvestPlanDetail investPlanDetail);

    TInvestPlanDetail delete(TInvestPlanDetail investPlanDetail);

    TInvestPlanDetail select(TInvestPlanDetail investPlanDetail);

    PageInfo<TInvestPlanDetail> queryList(TInvestPlanDetail investPlanDetail, Integer pageNum, Integer pageSize, Map<String ,Object> map);

    List<TInvestPlanDetail> queryList(TInvestPlanDetail investPlanDetail);

    /**
     * 根据计划主表插入计划分表(未续仓)
     * @param investPlanMain
     * @return
     */
    long insertByMainNew(TInvestPlanMain investPlanMain);

    /**
     * 根据计划主表插入计划分表（续仓）
     * @param investPlanMainOrig
     * @param investPlanMainNew
     * @return
     */
    long insertByMainSecond(TInvestPlanMain investPlanMainOrig, TInvestPlanMain investPlanMainNew);

    /**
     * 获取正在执行主计划列表（计划ID，会员级别分组）
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> getMainRunningCount(TInvestPlanDetailDZ tInvestPlanDetailDZ);

    /**
     * 获取正在执行主计划列表（会员级别分组）
     * @param tInvestPlanDetailDZ
     * @return
     */
    Map<String, TInvestPlanDetailDZ> getMainRunningCount2(TInvestPlanDetailDZ tInvestPlanDetailDZ);

    /**
     * 统计业绩
     * @param memberIdList
     * @param todayFlag = 1今日业绩  其他 累计业绩
     * @return
     */
    long statisticPlanAmount(List<String> memberIdList, String todayFlag);

    /**
     * 获取主计划执行中数量统计，用于可购买计划校验
     * @param tInvestPlanDetailDZ
     * @return
     */
    List<TInvestPlanDetailDZ> getMainRunningPlanCount(TInvestPlanDetailDZ tInvestPlanDetailDZ);

}
