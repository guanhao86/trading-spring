package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableRepurchaseBonusSettingMapper;
import com.spring.fee.model.TableRepurchaseBonusSetting;
import com.spring.fee.model.TableRepurchaseBonusSettingExample;
import com.spring.fee.service.ITableRepurchaseBonusSettingBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 复消奖金配置服务
 */
@Slf4j
@Service
@Transactional
public class TableRepurchaseBonusSettingBusiSVImpl implements ITableRepurchaseBonusSettingBusiSV {

    @Autowired
    TableRepurchaseBonusSettingMapper iTableRepurchaseBonusSettingMapper;

    /**
     * 创建复消奖金配置记录
     * @param bo
     * @return
     */
    @Override
    public TableRepurchaseBonusSetting insert(TableRepurchaseBonusSetting bo) {
        log.info("创建复消奖金配置参数bo：{}", JSON.toJSON(bo));
        iTableRepurchaseBonusSettingMapper.insert(bo);
        return bo;
    }

    @Override
    public TableRepurchaseBonusSetting update(TableRepurchaseBonusSetting bo) {
        if (this.iTableRepurchaseBonusSettingMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRepurchaseBonusSetting delete(TableRepurchaseBonusSetting bo) {
        if (this.iTableRepurchaseBonusSettingMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableRepurchaseBonusSetting select(TableRepurchaseBonusSetting bo) {
        return this.iTableRepurchaseBonusSettingMapper.selectByPrimaryKey(bo.getId());
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
    public PageInfo<TableRepurchaseBonusSetting> queryListPage(TableRepurchaseBonusSetting bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取复消奖金配置参数bo：{}", JSON.toJSON(bo));
        log.info("获取复消奖金配置参数pageNum：{}", pageNum);
        log.info("获取复消奖金配置参数pageSize：{}", pageSize);
        log.info("获取复消奖金配置参数map：{}", JSON.toJSON(map));
        
        TableRepurchaseBonusSettingExample example = new TableRepurchaseBonusSettingExample();
        TableRepurchaseBonusSettingExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (null != bo.getPrice()) {
            criteria.andPriceEqualTo(bo.getPrice());
        }
        if (null != bo.getLayer()) {
            criteria.andLayerEqualTo(bo.getLayer());
        }
        if (null != bo.getPoint()) {
            criteria.andPointEqualTo(bo.getPoint());
        }

        PageInfo<TableRepurchaseBonusSetting> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableRepurchaseBonusSettingMapper.selectByExample(example));
        log.info("获取复消奖金配置结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
