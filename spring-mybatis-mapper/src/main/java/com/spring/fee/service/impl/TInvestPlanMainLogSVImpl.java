package com.spring.fee.service.impl;

import com.spring.fee.dao.mapper.TInvestPlanMainLogMapper;
import com.spring.fee.dao.mapper.TInvestPlanMainLogMapperDZ;
import com.spring.fee.model.TInvestPlanConfigExample;
import com.spring.fee.model.TInvestPlanMainLog;
import com.spring.fee.model.TInvestPlanMainLogDZ;
import com.spring.fee.model.TInvestPlanMainLogExample;
import com.spring.fee.service.ITInvestPlanMainLogSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 粮仓计划执行日志服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanMainLogSVImpl implements ITInvestPlanMainLogSV {

    @Autowired
    TInvestPlanMainLogMapper tInvestPlanMainLogMapper;

    @Autowired
    TInvestPlanMainLogMapperDZ investPlanMainLogMapperDZ;

    /**
     * 创建粮仓计划执行日志
     *
     * @param tInvestPlanMainLog
     * @return
     */
    @Override
    public TInvestPlanMainLog insert(TInvestPlanMainLog tInvestPlanMainLog) {
        tInvestPlanMainLog.setCreateTime(DateUtils.getSysDate());
        if (1 == this.tInvestPlanMainLogMapper.insert(tInvestPlanMainLog))
            return tInvestPlanMainLog;
        else
            return null;
    }

    /**
     * 查询结果
     *
     * @param tInvestPlanMainLog
     * @return
     */
    @Override
    public List<TInvestPlanMainLog> queryList(TInvestPlanMainLog tInvestPlanMainLog) {
        TInvestPlanMainLogExample example = new TInvestPlanMainLogExample();
        TInvestPlanMainLogExample.Criteria criteria = example.createCriteria();
        if (null != tInvestPlanMainLog.getSettleRecordId()) {
            criteria.andSettleRecordIdEqualTo(tInvestPlanMainLog.getSettleRecordId());
        }
        if (StringUtils.isNotEmpty(tInvestPlanMainLog.getStatus())) {
            criteria.andStatusEqualTo(tInvestPlanMainLog.getStatus());
        }
        return this.tInvestPlanMainLogMapper.selectByExample(example);
    }

    /**
     * 查询结果
     *
     * @param tInvestPlanMainLog
     * @return
     */
    @Override
    public List<TInvestPlanMainLogDZ> queryList2(TInvestPlanMainLogDZ tInvestPlanMainLog) {
        return this.investPlanMainLogMapperDZ.selectList(tInvestPlanMainLog);
    }
}
