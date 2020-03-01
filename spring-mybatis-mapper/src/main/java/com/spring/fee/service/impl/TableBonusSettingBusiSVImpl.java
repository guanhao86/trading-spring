package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableBonusSettingMapper;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableBonusDetailExample;
import com.spring.fee.model.TableBonusSetting;
import com.spring.fee.model.TableBonusSettingExample;
import com.spring.fee.service.ITableBonusSettingBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 奖金阈值配置表服务
 * 只有一条记录
 */
@Slf4j
@Service
@Transactional
public class TableBonusSettingBusiSVImpl implements ITableBonusSettingBusiSV {

    @Autowired
    TableBonusSettingMapper iTableBonusSettingMapper;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableBonusSetting insert(TableBonusSetting bo) {
        log.info("创建奖金阈值配置表参数bo：{}", JSON.toJSON(bo));
        iTableBonusSettingMapper.insert(bo);
        return bo;
    }

    @Override
    public TableBonusSetting update(TableBonusSetting bo) {
        TableBonusSettingExample example = new TableBonusSettingExample();
        TableBonusSettingExample.Criteria criteria = example.createCriteria();

        if(this.iTableBonusSettingMapper.updateByExample(bo, example) > 0)
            return bo;
        return null;
    }

    @Override
    public TableBonusSetting delete(TableBonusSetting bo) {
        TableBonusSettingExample example = new TableBonusSettingExample();
        if (this.iTableBonusSettingMapper.deleteByExample(example)>0) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBonusSetting select(TableBonusSetting bo) {
        TableBonusSettingExample example = new TableBonusSettingExample();
        List<TableBonusSetting> list = this.iTableBonusSettingMapper.selectByExample(example);
        return list.get(0);
    }

    @Override
    public PageInfo<TableBonusSetting> queryListPage(TableBonusSetting bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        TableBonusSettingExample example = new TableBonusSettingExample();

        PageInfo<TableBonusSetting> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableBonusSettingMapper.selectByExample(example));
        log.info("奖金阈值配置表结果：{}", JSON.toJSON(pageInfo));

        return pageInfo;
    }
}
