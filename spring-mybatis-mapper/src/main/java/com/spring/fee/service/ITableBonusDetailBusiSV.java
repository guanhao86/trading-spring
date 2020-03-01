package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;

import java.util.List;
import java.util.Map;

/**
 * 奖金流水管理表服务
 */
public interface ITableBonusDetailBusiSV {

    TableBonusDetail insert(TableBonusDetail bo);

    TableBonusDetail update(TableBonusDetail bo);

    TableBonusDetail delete(TableBonusDetail bo);

    TableBonusDetail select(TableBonusDetail bo);

    PageInfo<TableBonusDetail> queryListPage(TableBonusDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
