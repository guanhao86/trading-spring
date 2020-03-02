package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableRepurchaseDetailMapper;
import com.spring.fee.model.TableRepurchaseDetail;
import com.spring.fee.model.TableRepurchaseDetailExample;
import com.spring.fee.service.ITableRepurchaseDetailBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 复消记录表服务
 */
@Slf4j
@Service
@Transactional
public class TableRepurchaseDetailBusiSVImpl implements ITableRepurchaseDetailBusiSV {

    @Autowired
    TableRepurchaseDetailMapper iTableRepurchaseDetailMapper;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableRepurchaseDetail insert(TableRepurchaseDetail bo) {
        log.info("创建复消记录表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setRepurchaseTime(sysdate);
        iTableRepurchaseDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableRepurchaseDetail update(TableRepurchaseDetail bo) {
        if (this.iTableRepurchaseDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRepurchaseDetail delete(TableRepurchaseDetail bo) {
        if (this.iTableRepurchaseDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRepurchaseDetail select(TableRepurchaseDetail bo) {
        return this.iTableRepurchaseDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 数据列表分页
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TableRepurchaseDetail> queryListPage(TableRepurchaseDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取复消记录表参数bo：{}", JSON.toJSON(bo));
        log.info("获取复消记录表参数pageNum：{}", pageNum);
        log.info("获取复消记录表参数pageSize：{}", pageSize);
        log.info("获取复消记录表参数map：{}", JSON.toJSON(map));
        
        TableRepurchaseDetailExample example = new TableRepurchaseDetailExample();
        TableRepurchaseDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getRepurchaseValue()) {
            criteria.andRepurchaseValueEqualTo(bo.getRepurchaseValue());
        }
        if (null != bo.getRepurchaseTime()) {
            criteria.andRepurchaseTimeEqualTo(bo.getRepurchaseTime());
        }
        if (null != bo.getCurrentLevel()) {
            criteria.andCurrentLevelEqualTo(bo.getCurrentLevel());
        }
        if (null != bo.getCurrentRank()) {
            criteria.andCurrentRankEqualTo(bo.getCurrentRank());
        }

        PageInfo<TableRepurchaseDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableRepurchaseDetailMapper.selectByExample(example));
        log.info("获取复消记录表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
