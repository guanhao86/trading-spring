package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring.fee.dao.mapper.TInvestPlanRunDetailMapper;
import com.spring.fee.model.TInvestPlanRunDetail;
import com.spring.fee.model.TInvestPlanRunDetailExample;
import com.spring.fee.service.ITInvestPlanConfigBusiSV;
import com.spring.fee.service.ITInvestPlanMainBusiSV;
import com.spring.fee.service.ITInvestPlanRunDetailBusiSV;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 粮仓计划执行明细表服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanRunDetailBusiSVImpl implements ITInvestPlanRunDetailBusiSV {

    @Autowired
    TInvestPlanRunDetailMapper investQueueMapper;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    /**
     * 创建粮仓计划执行明细
     * @param tInvestPlanRunDetail
     * @return
     */
    @Override
    public TInvestPlanRunDetail insert(TInvestPlanRunDetail tInvestPlanRunDetail) {
        log.info("创建粮仓计划执行明细参数tInvestPlanRunDetail：{}", JSON.toJSON(tInvestPlanRunDetail));
        tInvestPlanRunDetail.setRetrunTime(DateUtils.getSysDate());
        investQueueMapper.insert(tInvestPlanRunDetail);
        return tInvestPlanRunDetail;
    }

    @Override
    public TInvestPlanRunDetail update(TInvestPlanRunDetail tInvestPlanRunDetail) {
        if (1 == investQueueMapper.updateByPrimaryKeySelective(tInvestPlanRunDetail)){
            return tInvestPlanRunDetail;
        }
        return null;
    }

    @Override
    public TInvestPlanRunDetail delete(TInvestPlanRunDetail tInvestPlanRunDetail) {
        return null;
    }

    @Override
    public TInvestPlanRunDetail select(TInvestPlanRunDetail tInvestPlanRunDetail) {
        return null;
    }

    @Override
    public List<TInvestPlanRunDetail> queryList(TInvestPlanRunDetail tInvestPlanRunDetail) {
        TInvestPlanRunDetailExample example = new TInvestPlanRunDetailExample();
        TInvestPlanRunDetailExample.Criteria criteria = example.createCriteria();
        if (null != tInvestPlanRunDetail.getId()) {
            criteria.andIdEqualTo(tInvestPlanRunDetail.getId());
        }
        if (StringUtils.isNotEmpty(tInvestPlanRunDetail.getPlanDetailId())) {
            criteria.andPlanDetailIdEqualTo(tInvestPlanRunDetail.getPlanDetailId());
        }
        return investQueueMapper.selectByExample(example);
    }
}
