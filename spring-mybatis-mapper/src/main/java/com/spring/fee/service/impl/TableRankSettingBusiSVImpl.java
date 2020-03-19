package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableRankSettingMapper;
import com.spring.fee.model.TableRankSetting;
import com.spring.fee.model.TableRankSettingExample;
import com.spring.fee.service.ITableRankSettingBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 奖衔配置服务
 */
@Slf4j
@Service
@Transactional
public class TableRankSettingBusiSVImpl implements ITableRankSettingBusiSV {

    @Autowired
    TableRankSettingMapper iTableRankSettingMapper;

    /**
     * 创建奖衔配置记录
     * @param bo
     * @return
     */
    @Override
    public TableRankSetting insert(TableRankSetting bo) {
        log.info("创建奖衔配置参数bo：{}", JSON.toJSON(bo));
        iTableRankSettingMapper.insert(bo);
        return bo;
    }

    @Override
    public TableRankSetting update(TableRankSetting bo) {
        if (this.iTableRankSettingMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRankSetting delete(TableRankSetting bo) {
        if (this.iTableRankSettingMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRankSetting select(TableRankSetting bo) {
        return this.iTableRankSettingMapper.selectByPrimaryKey(bo.getId());
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
    public PageInfo<TableRankSetting> queryListPage(TableRankSetting bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取奖衔配置参数bo：{}", JSON.toJSON(bo));
        log.info("获取奖衔配置参数pageNum：{}", pageNum);
        log.info("获取奖衔配置参数pageSize：{}", pageSize);
        log.info("获取奖衔配置参数map：{}", JSON.toJSON(map));
        
        TableRankSettingExample example = new TableRankSettingExample();
        TableRankSettingExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getName())) {
            criteria.andNameEqualTo(bo.getName());
        }
        if (null != bo.getCasePerformance()) {
            criteria.andCasePerformanceEqualTo(bo.getCasePerformance());
        }
        if (null != bo.getCaseDownRankCount()) {
            criteria.andCaseDownRankCountEqualTo(bo.getCaseDownRankCount());
        }

        PageInfo<TableRankSetting> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableRankSettingMapper.selectByExample(example));
        log.info("获取奖衔配置结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
