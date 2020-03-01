package com.spring.fee.service.impl;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TWheatFinancialMapper;
import com.spring.fee.dao.mapper.TWheatFinancialOrderMapper;
import com.spring.fee.model.TWheatFinancial;
import com.spring.fee.model.TWheatFinancialExample;
import com.spring.fee.model.TWheatFinancialOrder;
import com.spring.fee.model.TWheatFinancialOrderExample;
import com.spring.fee.service.ITWheatFinancialOrderBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 会员服务
 */
@Slf4j
@Service
@Transactional
public class TWheatFinancialBusiOrderSVImpl implements ITWheatFinancialOrderBusiSV {

    @Autowired
    TWheatFinancialOrderMapper mapper;


    @Override
    public TWheatFinancialOrder insert(TWheatFinancialOrder tWheatFinancialOrder) {
        tWheatFinancialOrder.setCreateTime(DateUtils.getSysDate());
        if (1 == this.mapper.insert(tWheatFinancialOrder)) {
            return tWheatFinancialOrder;
        }
        return null;
    }

    @Override
    public TWheatFinancialOrder update(TWheatFinancialOrder tWheatFinancialOrder) {
        if (1 == mapper.updateByPrimaryKeySelective(tWheatFinancialOrder)) {
            return tWheatFinancialOrder;
        }
        return null;
    }

    @Override
    public TWheatFinancialOrder delete(TWheatFinancialOrder tWheatFinancialOrder) {
        if (1 == mapper.deleteByPrimaryKey(tWheatFinancialOrder.getId())) {
            return tWheatFinancialOrder;
        }
        return null;
    }

    @Override
    public TWheatFinancialOrder select(TWheatFinancialOrder tWheatFinancialOrder) {
        return mapper.selectByPrimaryKey(tWheatFinancialOrder.getId());
    }

    @Override
    public List<TWheatFinancialOrder> queryList(TWheatFinancialOrder tWheatFinancialOrder, String[] orderBy) {
        TWheatFinancialOrderExample example = new TWheatFinancialOrderExample();
        TWheatFinancialOrderExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        if (null != tWheatFinancialOrder.getId())
            criteria.andIdEqualTo(tWheatFinancialOrder.getId());

        if (StringUtils.isNotEmpty(tWheatFinancialOrder.getProductName()))
            criteria.andProductNameLike("%"+tWheatFinancialOrder.getProductName()+"%");

        if (null != tWheatFinancialOrder.getFinancialId())
            criteria.andFinancialIdEqualTo(tWheatFinancialOrder.getFinancialId());

        if (StringUtils.isNotEmpty(tWheatFinancialOrder.getMemberId()))
            criteria.andMemberIdLike("%"+tWheatFinancialOrder.getMemberId()+"%");

        if (StringUtils.isNotEmpty(tWheatFinancialOrder.getPhone()))
            criteria.andPhoneLike("%"+tWheatFinancialOrder.getPhone()+"%");

        if (null != tWheatFinancialOrder.getBeginTime())
            criteria.andCreateTimeGreaterThanOrEqualTo(tWheatFinancialOrder.getBeginTime());

        if (null != tWheatFinancialOrder.getEndTime())
            criteria.andCreateTimeLessThanOrEqualTo(tWheatFinancialOrder.getEndTime());

        if (null != tWheatFinancialOrder.getDueTime())
            criteria.andDueTimeGreaterThan(tWheatFinancialOrder.getDueTime());

        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TWheatFinancialOrder> pageList(TWheatFinancialOrder entity, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        List<TWheatFinancialOrder> findList = this.queryList(entity,null);
        return new PageInfo<TWheatFinancialOrder>(findList);
    }
}
