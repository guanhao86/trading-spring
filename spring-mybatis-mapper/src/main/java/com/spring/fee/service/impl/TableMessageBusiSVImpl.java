package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMessageMapper;
import com.spring.fee.model.TableMessage;
import com.spring.fee.model.TableMessageExample;
import com.spring.fee.service.ITableMessageBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 留言服务
 */
@Slf4j
@Service
@Transactional
public class TableMessageBusiSVImpl implements ITableMessageBusiSV {

    @Autowired
    TableMessageMapper iTableMessageMapper;

    /**
     * 创建留言记录
     * @param bo
     * @return
     */
    @Override
    public TableMessage insert(TableMessage bo) {
        log.info("创建留言参数bo：{}", JSON.toJSON(bo));
        bo.setCreateTime(DateUtils.getSysDate());
        iTableMessageMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMessage update(TableMessage bo) {
        if (this.iTableMessageMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMessage delete(TableMessage bo) {
        if (this.iTableMessageMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMessage select(TableMessage bo) {
        return this.iTableMessageMapper.selectByPrimaryKey(bo.getId());
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
    public PageInfo<TableMessage> queryListPage(TableMessage bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取留言参数bo：{}", JSON.toJSON(bo));
        log.info("获取留言参数pageNum：{}", pageNum);
        log.info("获取留言参数pageSize：{}", pageSize);
        log.info("获取留言参数map：{}", JSON.toJSON(map));
        
        TableMessageExample example = new TableMessageExample();
        TableMessageExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getTitle())) {
            criteria.andTitleEqualTo(bo.getTitle());
        }
        if (StringUtils.isNotEmpty(bo.getMessage())) {
            criteria.andMessageEqualTo(bo.getMessage());
        }
        if (StringUtils.isNotEmpty(bo.getResponse())) {
            criteria.andResponseEqualTo(bo.getResponse());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (StringUtils.isNotEmpty(bo.getRespMemberId())) {
            criteria.andRespMemberIdEqualTo(bo.getRespMemberId());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (null != bo.getResponseTime()) {
            criteria.andResponseTimeEqualTo(bo.getResponseTime());
        }
        if (StringUtils.isNotEmpty(bo.getState())) {
            criteria.andStateEqualTo(bo.getState());
        }

        example.setOrderByClause("create_time desc");

        PageInfo<TableMessage> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMessageMapper.selectByExample(example));
        log.info("获取留言结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
