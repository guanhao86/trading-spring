package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TInvestSettleRecordMapper;
import com.spring.fee.model.TInvestSettleRecord;
import com.spring.fee.model.TInvestSettleRecordDZ;
import com.spring.fee.model.TInvestSettleRecordExample;
import com.spring.fee.service.ITInvestSettleRecordBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算记录服务
 */
@Slf4j
@Service
@Transactional
public class TInvestSettleRecordBusiSVImpl implements ITInvestSettleRecordBusiSV {

    @Autowired
    TInvestSettleRecordMapper iInvestSettleRecordMapper;

    /**
     * 创建结算记录
     * @param iInvestSettleRecord
     * @return
     */
    @Override
    public TInvestSettleRecord insert(TInvestSettleRecord iInvestSettleRecord) {
        log.info("创建结算记录参数iInvestSettleRecord：{}", JSON.toJSON(iInvestSettleRecord));
        Date sysdate = DateUtils.getSysDate();
        iInvestSettleRecord.setCreateTime(sysdate);
        iInvestSettleRecord.setSettleday(DateUtils.getYYYYMMDD(sysdate));
        iInvestSettleRecordMapper.insert(iInvestSettleRecord);
        return iInvestSettleRecord;
    }

    @Override
    public TInvestSettleRecord update(TInvestSettleRecord tInvestSettleRecord) {
        if (this.iInvestSettleRecordMapper.updateByPrimaryKeySelective(tInvestSettleRecord) == 1) {
            return tInvestSettleRecord;
        }
        return null;
    }

    /**
     * 创建结算记录
     * @param planCount
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TInvestSettleRecord insert2(Long planCount) {
        TInvestSettleRecord tInvestSettleRecord = new TInvestSettleRecord();
        tInvestSettleRecord.setPlanCount(planCount);
        return this.insert(tInvestSettleRecord);
    }

    @Override
    public List<TInvestSettleRecord> queryList(TInvestSettleRecordDZ iInvestSettleRecord) {
        log.info("获取结算记录列表参数iInvestSettleRecord：{}", JSON.toJSON(iInvestSettleRecord));
        TInvestSettleRecordExample example = new TInvestSettleRecordExample();
        TInvestSettleRecordExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(iInvestSettleRecord.getSettleday())) {
            criteria.andSettledayEqualTo(iInvestSettleRecord.getSettleday());
        }
        if (null != iInvestSettleRecord.getCreateTimeStart()) {
            criteria.andCreateTimeGreaterThanOrEqualTo(iInvestSettleRecord.getCreateTimeStart());
        }
        if (null != iInvestSettleRecord.getCreateTimeEnd()) {
            criteria.andCreateTimeLessThanOrEqualTo(iInvestSettleRecord.getCreateTimeEnd());
        }
        if (StringUtils.isNotEmpty(iInvestSettleRecord.getOrderBy())) {
            example.setOrderByClause(iInvestSettleRecord.getOrderBy());
        }
        return iInvestSettleRecordMapper.selectByExample(example);
    }

    /**
     * 数据列表分页
     * @param iInvestSettleRecord
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TInvestSettleRecord> queryListPage(TInvestSettleRecordDZ iInvestSettleRecord, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取结算记录列表参数iInvestSettleRecord：{}", JSON.toJSON(iInvestSettleRecord));
        log.info("获取结算记录列表参数pageNum：{}", pageNum);
        log.info("获取结算记录列表参数pageSize：{}", pageSize);
        log.info("获取结算记录列表参数map：{}", JSON.toJSON(map));

        PageInfo<TInvestSettleRecord> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.queryList(iInvestSettleRecord));
        log.info("获取结算记录列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
