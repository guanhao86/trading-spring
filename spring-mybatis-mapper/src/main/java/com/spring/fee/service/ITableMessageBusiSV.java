package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMessage;

import java.util.Map;

/**
 * 留言服务
 */
public interface ITableMessageBusiSV {

    TableMessage insert(TableMessage bo);

    TableMessage update(TableMessage bo);

    TableMessage delete(TableMessage bo);

    TableMessage select(TableMessage bo);

    PageInfo<TableMessage> queryListPage(TableMessage bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
