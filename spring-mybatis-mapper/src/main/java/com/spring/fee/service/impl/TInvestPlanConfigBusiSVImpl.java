package com.spring.fee.service.impl;

import com.spring.fee.dao.mapper.TInvestPlanConfigMapper;
import com.spring.fee.model.TInvestPlanConfig;
import com.spring.fee.model.TInvestPlanConfigExample;
import com.spring.fee.service.ITInvestPlanConfigBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划配置服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanConfigBusiSVImpl implements ITInvestPlanConfigBusiSV {

    @Autowired
    TInvestPlanConfigMapper investPlanConfigMapper;

    /**
     * 获取计划配置详情
     *
     * @param tInvestPlanConfig
     * @return
     */
    @Override
    public TInvestPlanConfig select(TInvestPlanConfig tInvestPlanConfig) {
        return investPlanConfigMapper.selectByPrimaryKey(tInvestPlanConfig.getPlanId());
    }

    /**
     * 更新
     *
     * @param tInvestPlanConfig
     * @return
     */
    @Override
    public TInvestPlanConfig update(TInvestPlanConfig tInvestPlanConfig) {
        if (this.investPlanConfigMapper.updateByPrimaryKeySelective(tInvestPlanConfig) == 1) {
            return tInvestPlanConfig;
        }
        return null;
    }

    /**
     * 获取计划配置详情
     *
     * @param planId
     * @return
     */
    @Override
    public TInvestPlanConfig selectById(String planId) {
        TInvestPlanConfig tInvestPlanConfig = new TInvestPlanConfig();
        tInvestPlanConfig.setPlanId(planId);
        return this.select(tInvestPlanConfig);
    }

    /**
     * 获取计划配置列表
     *
     * @param TInvestPlanConfig
     * @return
     */
    @Override
    public List<TInvestPlanConfig> queryList(TInvestPlanConfig TInvestPlanConfig) {
        TInvestPlanConfigExample example = new TInvestPlanConfigExample();
        TInvestPlanConfigExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" plan_id");
        return investPlanConfigMapper.selectByExample(example);
    }

    /**
     * 获取计划配置列表
     * 大于largeThanPlanId的所有计划
     *
     * @param tInvestPlanConfig
     * @param largeThanPlanId
     * @return
     */
    @Override
    public List<TInvestPlanConfig> queryList(TInvestPlanConfig tInvestPlanConfig, String largeThanPlanId) {
        TInvestPlanConfigExample example = new TInvestPlanConfigExample();
        TInvestPlanConfigExample.Criteria criteria = example.createCriteria();

        criteria.andPlanIdGreaterThan(largeThanPlanId);

        example.setOrderByClause(" plan_id");
        return investPlanConfigMapper.selectByExample(example);
    }

    /**
     * 获取计划配置Map
     *
     * @return
     */
    @Override
    public Map<String, TInvestPlanConfig> queryMap() {
        List<TInvestPlanConfig> queryList = this.queryList( new TInvestPlanConfig());
        Map<String, TInvestPlanConfig> map = new HashMap<>();
        if (queryList != null) {
            for (TInvestPlanConfig tmp : queryList) {
                map.put(tmp.getPlanId(), tmp);
            }
        }
        return map;
    }
}
