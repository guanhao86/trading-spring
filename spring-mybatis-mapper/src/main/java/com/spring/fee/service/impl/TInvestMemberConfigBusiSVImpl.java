package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring.fee.dao.mapper.TInvestMemberConfigMapper;
import com.spring.fee.model.TInvestMemberConfig;
import com.spring.fee.model.TInvestMemberConfigExample;
import com.spring.fee.service.ITInvestMemberConfigBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 会员投资配置信息服务
 */
@Slf4j
@Service
@Transactional
public class TInvestMemberConfigBusiSVImpl implements ITInvestMemberConfigBusiSV {

    @Autowired
    TInvestMemberConfigMapper tInvestMemberConfigMapper;

    @Override
    public TInvestMemberConfig insert(TInvestMemberConfig tInvestMemberConfig) {
        log.info("插入会员投资配置信息{}", JSON.toJSONString(tInvestMemberConfig));
        Date sysdate = DateUtils.getSysDate();
        tInvestMemberConfig.setUpdateTime(sysdate);
        if (1 == tInvestMemberConfigMapper.insert(tInvestMemberConfig)) {
            return tInvestMemberConfig;
        }
        return null;
    }

    @Override
    public TInvestMemberConfig update(TInvestMemberConfig tInvestMemberConfig) {
        log.info("修改会员投资配置信息{}", JSON.toJSONString(tInvestMemberConfig));
        Date sysdate = DateUtils.getSysDate();
        tInvestMemberConfig.setUpdateTime(sysdate);

        TInvestMemberConfigExample example = new TInvestMemberConfigExample();
        TInvestMemberConfigExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(tInvestMemberConfig.getMemberId()).andConfigCodeEqualTo(tInvestMemberConfig.getConfigCode());

        if (1 == tInvestMemberConfigMapper.updateByExampleSelective(tInvestMemberConfig, example)) {
            return tInvestMemberConfig;
        }
        return null;
    }

    @Override
    public TInvestMemberConfig modify(TInvestMemberConfig tInvestMemberConfig) {
        if (StringUtils.isEmpty(selectByMemberId(tInvestMemberConfig.getMemberId(), tInvestMemberConfig.getConfigCode()))){
            insert(tInvestMemberConfig);
        }else{
            update(tInvestMemberConfig);
        }
        return tInvestMemberConfig;
    }

    @Override
    public String selectByMemberId(String memberId, String configCode) {
        TInvestMemberConfigExample example = new TInvestMemberConfigExample();
        TInvestMemberConfigExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(memberId).andConfigCodeEqualTo(configCode);

        List<TInvestMemberConfig> arr = this.tInvestMemberConfigMapper.selectByExample(example);
        if (arr != null && arr.size() == 1){
            return arr.get(0).getConfigValue();
        }
        return null;
    }

    /**
     * 获取固定属性数量
     *
     * @param memberList
     * @param configCode
     * @return
     */
    @Override
    public int selectByMemberIdList(List<String> memberList, String configCode) {
        if (!CollectionUtils.isEmpty(memberList)) {
            TInvestMemberConfigExample example = new TInvestMemberConfigExample();
            TInvestMemberConfigExample.Criteria criteria = example.createCriteria();

            criteria.andMemberIdIn(memberList).andConfigCodeEqualTo(configCode);

            List<TInvestMemberConfig> arr = this.tInvestMemberConfigMapper.selectByExample(example);

            return arr.size();
        }
        return 0;
    }
}
