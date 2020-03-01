package com.spring.fee.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TWheatFinancialStreamMapper;
import com.spring.fee.dao.mapper.TWheatFinancialStreamMapper;
import com.spring.fee.model.TWheatFinancialStream;
import com.spring.fee.model.TWheatFinancialStreamExample;
import com.spring.fee.model.TWheatFinancialStream;
import com.spring.fee.service.ITWheatFinancialStreamBusiSV;
import com.spring.fee.service.ITWheatFinancialStreamBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员服务
 */
@Slf4j
@Service
@Transactional
public class TWheatFinancialBusiStreamSVImpl implements ITWheatFinancialStreamBusiSV {

    @Autowired
    TWheatFinancialStreamMapper mapper;


    @Override
    public TWheatFinancialStream insert(TWheatFinancialStream tWheatFinancialStream) {
        tWheatFinancialStream.setCreateTime(DateUtils.getSysDate());
        if (1 == this.mapper.insert(tWheatFinancialStream)) {
            return tWheatFinancialStream;
        }
        return null;
    }

    @Override
    public TWheatFinancialStream update(TWheatFinancialStream tWheatFinancialStream) {
        if (1 == mapper.updateByPrimaryKeySelective(tWheatFinancialStream)) {
            return tWheatFinancialStream;
        }
        return null;
    }

    @Override
    public TWheatFinancialStream delete(TWheatFinancialStream tWheatFinancialStream) {
        if (1 == mapper.deleteByPrimaryKey(tWheatFinancialStream.getId())) {
            return tWheatFinancialStream;
        }
        return null;
    }

    @Override
    public TWheatFinancialStream select(TWheatFinancialStream tWheatFinancialStream) {
        return mapper.selectByPrimaryKey(tWheatFinancialStream.getId());
    }

    @Override
    public List<TWheatFinancialStream> queryList(TWheatFinancialStream tWheatFinancialStream, String[] orderBy) {
        TWheatFinancialStreamExample example = new TWheatFinancialStreamExample();
        TWheatFinancialStreamExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        if (null != tWheatFinancialStream.getId())
            criteria.andIdEqualTo(tWheatFinancialStream.getId());

        if (StringUtils.isNotEmpty(tWheatFinancialStream.getProductName()))
            criteria.andProductNameLike("%"+tWheatFinancialStream.getProductName()+"%");

        if (null != tWheatFinancialStream.getFinancialId())
            criteria.andFinancialIdEqualTo(tWheatFinancialStream.getFinancialId());

        if (StringUtils.isNotEmpty(tWheatFinancialStream.getMemberId()))
            criteria.andMemberIdLike("%"+tWheatFinancialStream.getMemberId()+"%");

        if (StringUtils.isNotEmpty(tWheatFinancialStream.getPhone()))
            criteria.andPhoneLike("%"+tWheatFinancialStream.getPhone()+"%");

        if (StringUtils.isNotEmpty(tWheatFinancialStream.getOrderNo()))
            criteria.andOrderNoLike("%"+tWheatFinancialStream.getOrderNo()+"%");

        if (null != tWheatFinancialStream.getBeginTime())
            criteria.andCreateTimeGreaterThanOrEqualTo(tWheatFinancialStream.getBeginTime());

        if (null != tWheatFinancialStream.getEndTime())
            criteria.andCreateTimeLessThanOrEqualTo(tWheatFinancialStream.getEndTime());

        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TWheatFinancialStream> pageList(TWheatFinancialStream entity, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        List<TWheatFinancialStream> findList = this.queryList(entity,null);
        return new PageInfo<TWheatFinancialStream>(findList);
    }
}
