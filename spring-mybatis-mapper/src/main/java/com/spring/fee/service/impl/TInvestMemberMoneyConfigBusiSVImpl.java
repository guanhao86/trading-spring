package com.spring.fee.service.impl;

import com.spring.fee.dao.mapper.TInvestMemberMoneyConfigMapper;
import com.spring.fee.model.TInvestMemberMoneyConfig;
import com.spring.fee.model.TInvestMemberMoneyConfigExample;
import com.spring.fee.service.ITInvestMemberMoneyConfigBusiSV;
import com.spring.fee.service.ITInvestMemberMoneyConfigBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员奖金计算配置
 */
@Slf4j
@Service
@Transactional
public class TInvestMemberMoneyConfigBusiSVImpl implements ITInvestMemberMoneyConfigBusiSV {

    @Autowired
    TInvestMemberMoneyConfigMapper investMemberMoneyConfigMapper;

    /**
     * 获取计划配置详情
     *
     * @param level
     * @return
     */
    @Override
    public TInvestMemberMoneyConfig select(String level, String planId) {
        TInvestMemberMoneyConfigExample example = new TInvestMemberMoneyConfigExample();
        TInvestMemberMoneyConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLevelIdEqualTo(Integer.parseInt(level)).andPlanIdEqualTo(planId);
        example.setOrderByClause(" level_id");
        List<TInvestMemberMoneyConfig> arr = investMemberMoneyConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(arr)) {
            return arr.get(0);
        }
        return null;
    }

    @Override
    public TInvestMemberMoneyConfig selectById(Long id) {
        return this.investMemberMoneyConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public TInvestMemberMoneyConfig update(TInvestMemberMoneyConfig tInvestMemberMoneyConfig) {
        if ( 1 == this.investMemberMoneyConfigMapper.updateByPrimaryKeySelective(tInvestMemberMoneyConfig)){
            return tInvestMemberMoneyConfig;
        }
        return null;
    }

    /**
     * 获取计划配置列表
     *
     * @param TInvestMemberMoneyConfig
     * @return
     */
    @Override
    public List<TInvestMemberMoneyConfig> queryList(TInvestMemberMoneyConfig TInvestMemberMoneyConfig, String orderBy) {
        TInvestMemberMoneyConfigExample example = new TInvestMemberMoneyConfigExample();
        TInvestMemberMoneyConfigExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(orderBy)) {
            example.setOrderByClause(" id");
        }else {
            example.setOrderByClause(orderBy);
        }
        return investMemberMoneyConfigMapper.selectByExample(example);
    }

    /**
     * 获取配置map
     *
     * @return
     */
    @Override
    public Map<String, TInvestMemberMoneyConfig> queryMap() {
        Map<String, TInvestMemberMoneyConfig> map = new HashMap<>();
        List<TInvestMemberMoneyConfig> queryList = this.queryList(new TInvestMemberMoneyConfig(), null);
        for (TInvestMemberMoneyConfig tmp : queryList) {
            map.put(tmp.getLevelId()+"_"+tmp.getPlanId(), tmp);
            map.put(tmp.getLevelId()+"", tmp);
        }
        return map;
    }

    /**
     * 获取配置map
     * level:list
     * @return
     */
    @Override
    public Map<String, List<TInvestMemberMoneyConfig>> queryMap2() {
        Map<String, List<TInvestMemberMoneyConfig>> map = new HashMap<>();
        List<TInvestMemberMoneyConfig> queryList = this.queryList(new TInvestMemberMoneyConfig(), " level_id, running_count desc");
        List<TInvestMemberMoneyConfig> arr = new ArrayList<>();
        for (TInvestMemberMoneyConfig tmp : queryList) {
            if (null != map.get(String.valueOf(tmp.getLevelId()))) {
                arr = map.get(String.valueOf(tmp.getLevelId()));
            } else {
                arr = new ArrayList<>();
            }
            arr.add(tmp);
            map.put(String.valueOf(tmp.getLevelId()), arr);
        }
        return map;
    }
}
