package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableBonusSetting;

import java.util.Map;

/**
 * 奖金阈值配置表服务
 */
public interface ITableBonusSettingBusiSV {

    TableBonusSetting insert(TableBonusSetting bo);

    TableBonusSetting update(TableBonusSetting bo);

    TableBonusSetting delete(TableBonusSetting bo);

    TableBonusSetting select(TableBonusSetting bo);

    PageInfo<TableBonusSetting> queryListPage(TableBonusSetting bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
