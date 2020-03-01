package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBroadcastInfo;

import java.util.Map;

/**
 * 公告管理表服务
 */
public interface ITableBroadcastInfoBusiSV {

    TableBroadcastInfo insert(TableBroadcastInfo bo);

    TableBroadcastInfo update(TableBroadcastInfo bo);

    TableBroadcastInfo delete(TableBroadcastInfo bo);

    TableBroadcastInfo select(TableBroadcastInfo bo);

    PageInfo<TableBroadcastInfo> queryListPage(TableBroadcastInfo bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
