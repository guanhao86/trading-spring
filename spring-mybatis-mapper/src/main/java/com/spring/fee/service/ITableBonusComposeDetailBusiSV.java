package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusComposeDetail;

import java.util.Map;

/**
 * 组织奖详情表服务
 */
public interface ITableBonusComposeDetailBusiSV {

    TableBonusComposeDetail insert(TableBonusComposeDetail bo);

    TableBonusComposeDetail update(TableBonusComposeDetail bo);

    TableBonusComposeDetail delete(TableBonusComposeDetail bo);

    TableBonusComposeDetail select(TableBonusComposeDetail bo);

    PageInfo<TableBonusComposeDetail> queryListPage(TableBonusComposeDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
