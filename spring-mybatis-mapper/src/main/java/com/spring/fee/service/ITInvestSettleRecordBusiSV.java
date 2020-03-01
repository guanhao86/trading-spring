package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TInvestSettleRecord;
import com.spring.fee.model.TInvestSettleRecordDZ;

import java.util.List;
import java.util.Map;

/**
 * 结算记录服务
 */
public interface ITInvestSettleRecordBusiSV {

    TInvestSettleRecord insert(TInvestSettleRecord tInvestSettleRecord);

    TInvestSettleRecord update(TInvestSettleRecord tInvestSettleRecord);

    TInvestSettleRecord insert2(Long planCount);

    List<TInvestSettleRecord> queryList(TInvestSettleRecordDZ tInvestSettleRecord);

    PageInfo<TInvestSettleRecord> queryListPage(TInvestSettleRecordDZ iInvestSettleRecord, Integer pageNum, Integer pageSize, Map<String ,Object> map);
}
