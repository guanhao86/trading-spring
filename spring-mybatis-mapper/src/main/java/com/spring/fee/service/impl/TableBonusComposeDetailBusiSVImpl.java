package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableBonusComposeDetailMapper;
import com.spring.fee.model.TableBalanceDetailExample;
import com.spring.fee.model.TableBonusComposeDetail;
import com.spring.fee.model.TableBonusComposeDetailExample;
import com.spring.fee.service.ITableBonusComposeDetailBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 组织奖详情表服务
 */
@Slf4j
@Service
@Transactional
public class TableBonusComposeDetailBusiSVImpl implements ITableBonusComposeDetailBusiSV {

    @Autowired
    TableBonusComposeDetailMapper iTableBonusComposeDetailMapper;

    /**
     * 创建组织奖详情记录
     * @param bo
     * @return
     */
    @Override
    public TableBonusComposeDetail insert(TableBonusComposeDetail bo) {
        log.info("创建组织奖详情表参数bo：{}", JSON.toJSON(bo));
        iTableBonusComposeDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableBonusComposeDetail update(TableBonusComposeDetail bo) {
        TableBonusComposeDetailExample example = new TableBonusComposeDetailExample();
        TableBonusComposeDetailExample.Criteria criteria = example.createCriteria();

        if(this.iTableBonusComposeDetailMapper.updateByExample(bo, example) > 0)
            return bo;
        return null;
    }

    @Override
    public TableBonusComposeDetail delete(TableBonusComposeDetail bo) {
        TableBonusComposeDetailExample example = new TableBonusComposeDetailExample();
        if (this.iTableBonusComposeDetailMapper.deleteByExample(example)>0) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBonusComposeDetail select(TableBonusComposeDetail bo) {
        TableBonusComposeDetailExample example = new TableBonusComposeDetailExample();
        List<TableBonusComposeDetail> list = this.iTableBonusComposeDetailMapper.selectByExample(example);
        return list.get(0);
    }

    @Override
    public PageInfo<TableBonusComposeDetail> queryListPage(TableBonusComposeDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        TableBonusComposeDetailExample example = new TableBonusComposeDetailExample();
        TableBonusComposeDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (null != bo.getBonusDetailId()) {
            criteria.andBonusDetailIdEqualTo(bo.getBonusDetailId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getOrderPrice()) {
            criteria.andOrderPriceEqualTo(bo.getOrderPrice());
        }
        if (null != bo.getOrderTime()) {
            criteria.andOrderTimeEqualTo(bo.getOrderTime());
        }

        PageInfo<TableBonusComposeDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableBonusComposeDetailMapper.selectByExample(example));
        log.info("组织奖详情表结果：{}", JSON.toJSON(pageInfo));

        return pageInfo;
    }
}
