package com.spring.fee.service.impl;

import com.spring.fee.dao.mapper.TInvestMemberUpConfigMapper;
import com.spring.fee.model.TInvestMemberUpConfig;
import com.spring.fee.model.TInvestMemberUpConfigExample;
import com.spring.fee.service.ITInvestMemberUpConfigBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员升级，奖金计算配置
 */
@Slf4j
@Service
@Transactional
public class TInvestMemberUpConfigBusiSVImpl implements ITInvestMemberUpConfigBusiSV {

    @Autowired
    TInvestMemberUpConfigMapper investMemberUpConfigMapper;

    /**
     * 获取计划配置详情
     *
     * @param tInvestMemberUpConfig
     * @return
     */
    @Override
    public TInvestMemberUpConfig select(TInvestMemberUpConfig tInvestMemberUpConfig) {
        return investMemberUpConfigMapper.selectByPrimaryKey(tInvestMemberUpConfig.getLevelId());
    }

    /**
     * 获取计划配置详情
     *
     * @param levelId
     * @return
     */
    @Override
    public TInvestMemberUpConfig selectById(String levelId) {
        TInvestMemberUpConfig tInvestMemberUpConfig = new TInvestMemberUpConfig();
        tInvestMemberUpConfig.setLevelId(Integer.parseInt(levelId));
        return this.select(tInvestMemberUpConfig);
    }

    /**
     * 获取计划配置列表
     *
     * @param TInvestMemberUpConfig
     * @return
     */
    @Override
    public List<TInvestMemberUpConfig> queryList(TInvestMemberUpConfig TInvestMemberUpConfig) {
        TInvestMemberUpConfigExample example = new TInvestMemberUpConfigExample();
        TInvestMemberUpConfigExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" level_id");
        return investMemberUpConfigMapper.selectByExample(example);
    }

    /**
     * 获取配置map
     *
     * @return
     */
    @Override
    public Map<String, TInvestMemberUpConfig> queryMap() {
        Map<String, TInvestMemberUpConfig> map = new HashMap<>();
        List<TInvestMemberUpConfig> queryList = this.queryList(new TInvestMemberUpConfig());
        for (TInvestMemberUpConfig tmp : queryList) {
            map.put(String.valueOf(tmp.getLevelId()), tmp);
        }
        return map;
    }
}
