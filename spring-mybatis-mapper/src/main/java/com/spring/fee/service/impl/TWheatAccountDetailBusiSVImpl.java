package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TWheatAccountDetailMapper;
import com.spring.fee.model.TWheatAccountDetail;
import com.spring.fee.model.TWheatAccountDetailDZ;
import com.spring.fee.model.TWheatAccountDetailExample;
import com.spring.fee.service.ITWheatAccountDetailBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 账户明细服务
 */
@Slf4j
@Service
@Transactional
public class TWheatAccountDetailBusiSVImpl implements ITWheatAccountDetailBusiSV {

    @Autowired
    TWheatAccountDetailMapper tWheatAccountDetailMapper;


    @Override
    public TWheatAccountDetail insert(TWheatAccountDetail tWheatAccountDetail) {
        log.info("创建账户明细参数tWheatAccountDetail：{}", JSON.toJSON(tWheatAccountDetail));
        tWheatAccountDetail.setCreateTime(DateUtils.getSysDate());
        tWheatAccountDetailMapper.insert(tWheatAccountDetail);
        return tWheatAccountDetail;
    }

    @Override
    public TWheatAccountDetail update(TWheatAccountDetail tWheatAccountDetail) {
        return null;
    }

    @Override
    public TWheatAccountDetail delete(TWheatAccountDetail tWheatAccountDetail) {
        return null;
    }

    @Override
    public TWheatAccountDetail select(TWheatAccountDetail tWheatAccountDetail) {
        return null;
    }

    @Override
    public List<TWheatAccountDetail> queryList(TWheatAccountDetail tWheatAccountDetail) {
        TWheatAccountDetailExample example = new TWheatAccountDetailExample();
        TWheatAccountDetailExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(tWheatAccountDetail.getMemberId())) {
            criteria.andMemberIdEqualTo(tWheatAccountDetail.getMemberId());
        }
        if (null != tWheatAccountDetail.getId()) {
            criteria.andIdEqualTo(tWheatAccountDetail.getId());
        }
        if (null != tWheatAccountDetail.getAcctId()) {
            criteria.andIdEqualTo(tWheatAccountDetail.getAcctId());
        }
        example.setOrderByClause("create_time desc");

        return this.tWheatAccountDetailMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TWheatAccountDetail> queryPage(TWheatAccountDetailDZ tWheatAccountDetail, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        TWheatAccountDetailExample example = new TWheatAccountDetailExample();
        TWheatAccountDetailExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(tWheatAccountDetail.getMemberId())) {
            criteria.andMemberIdEqualTo(tWheatAccountDetail.getMemberId());
        }
        if (null != tWheatAccountDetail.getId()) {
            criteria.andIdEqualTo(tWheatAccountDetail.getId());
        }
        if (null != tWheatAccountDetail.getAcctId()) {
            criteria.andIdEqualTo(tWheatAccountDetail.getAcctId());
        }

        if (null != tWheatAccountDetail.getCreateTimeStart()) {
            criteria.andCreateTimeGreaterThanOrEqualTo(tWheatAccountDetail.getCreateTimeStart());
        }

        if (null != tWheatAccountDetail.getCreateTimeEnd()) {
            criteria.andCreateTimeLessThanOrEqualTo(tWheatAccountDetail.getCreateTimeEnd());
        }

        example.setOrderByClause("create_time desc");
        PageInfo<TWheatAccountDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.tWheatAccountDetailMapper.selectByExample(example));
        log.info("获取结算记录列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
