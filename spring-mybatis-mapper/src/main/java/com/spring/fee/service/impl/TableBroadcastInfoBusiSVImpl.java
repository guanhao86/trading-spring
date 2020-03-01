package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableBroadcastInfoMapper;
import com.spring.fee.model.TableBroadcastInfo;
import com.spring.fee.model.TableBroadcastInfoExample;
import com.spring.fee.service.ITableBroadcastInfoBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 公告管理服务
 */
@Slf4j
@Service
@Transactional
public class TableBroadcastInfoBusiSVImpl implements ITableBroadcastInfoBusiSV {

    @Autowired
    TableBroadcastInfoMapper iTableBroadcastInfoMapper;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableBroadcastInfo insert(TableBroadcastInfo bo) {
        log.info("创建公告管理参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setTime(sysdate);
        iTableBroadcastInfoMapper.insert(bo);
        return bo;
    }

    @Override
    public TableBroadcastInfo update(TableBroadcastInfo bo) {
        if (this.iTableBroadcastInfoMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBroadcastInfo delete(TableBroadcastInfo bo) {
        if (this.iTableBroadcastInfoMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBroadcastInfo select(TableBroadcastInfo bo) {
        return this.iTableBroadcastInfoMapper.selectByPrimaryKey(bo.getId());
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
    public PageInfo<TableBroadcastInfo> queryListPage(TableBroadcastInfo bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取公告管理参数bo：{}", JSON.toJSON(bo));
        log.info("获取公告管理参数pageNum：{}", pageNum);
        log.info("获取公告管理参数pageSize：{}", pageSize);
        log.info("获取公告管理参数map：{}", JSON.toJSON(map));
        
        TableBroadcastInfoExample example = new TableBroadcastInfoExample();
        TableBroadcastInfoExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getTitle())) {
            criteria.andTitleEqualTo(bo.getTitle());
        }
        if (StringUtils.isNotEmpty(bo.getContent())) {
            criteria.andContentEqualTo(bo.getContent());
        }
        if (null != bo.getTime()) {
            criteria.andTimeEqualTo(bo.getTime());
        }
        if (null != bo.getIsDelete()) {
            criteria.andIsDeleteEqualTo(bo.getIsDelete());
        }

        PageInfo<TableBroadcastInfo> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableBroadcastInfoMapper.selectByExample(example));
        log.info("获取公告管理结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
