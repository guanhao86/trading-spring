package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableRepurchaseBonusSetting;

import java.util.Map;

public interface ITableRepurchaseBonusSettingBusiSV {

    TableRepurchaseBonusSetting insert(TableRepurchaseBonusSetting bo);

    TableRepurchaseBonusSetting update(TableRepurchaseBonusSetting bo);

    TableRepurchaseBonusSetting delete(TableRepurchaseBonusSetting bo);

    TableRepurchaseBonusSetting select(TableRepurchaseBonusSetting bo);

    PageInfo<TableRepurchaseBonusSetting> queryListPage(TableRepurchaseBonusSetting bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
