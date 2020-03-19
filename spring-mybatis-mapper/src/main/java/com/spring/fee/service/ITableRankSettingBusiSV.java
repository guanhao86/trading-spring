package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableRankSetting;

import java.util.Map;

public interface ITableRankSettingBusiSV {

    TableRankSetting insert(TableRankSetting bo);

    TableRankSetting update(TableRankSetting bo);

    TableRankSetting delete(TableRankSetting bo);

    TableRankSetting select(TableRankSetting bo);

    PageInfo<TableRankSetting> queryListPage(TableRankSetting bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
