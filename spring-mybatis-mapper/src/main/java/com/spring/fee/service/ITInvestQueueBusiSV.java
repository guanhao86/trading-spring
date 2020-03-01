package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TInvestQueue;
import com.spring.fee.model.TInvestQueueDZ;

import java.util.List;
import java.util.Map;

/**
 * 粮仓计划队列服务
 */
public interface ITInvestQueueBusiSV {

    TInvestQueue insert(TInvestQueue tInvestQueue);

    TInvestQueue update(TInvestQueue tInvestQueue);

    TInvestQueue delete(TInvestQueue tInvestQueue);

    TInvestQueue select(TInvestQueue tInvestQueue);

    List<TInvestQueue> queryList(TInvestQueue tInvestQueue);

    /**
     * 数据列表分页
     * @param tInvestQueue
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    PageInfo<TInvestQueue> queryListPage(TInvestQueue tInvestQueue, Integer pageNum, Integer pageSize, Map<String ,Object> map);

    /**
     * 申请排队
     * @param tInvestQueue
     * @return
     */
    TInvestQueue createQueue(TInvestQueue tInvestQueue);

    /**
     * 申请排队
     * @param tInvestQueue
     * @return
     */
    TInvestQueue createQueueV2(TInvestQueue tInvestQueue);

    /**
     * 获取待执行队列，指定数量
     * @param size
     * @return
     */
    List<TInvestQueue> queryQueueToPlanList(int size);

    /**
     * 申请排队并开始执行计划
     * @param tInvestQueue
     * @return
     */
    TInvestQueueDZ createQueueV2AndRun(TInvestQueue tInvestQueue);

}
