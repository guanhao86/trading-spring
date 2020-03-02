package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableOrder;

import java.util.Map;

/**
 * 订单管理表服务
 */
public interface ITableOrderBusiSV {

    TableOrder insert(TableOrder bo);

    TableOrder update(TableOrder bo);

    TableOrder delete(TableOrder bo);

    TableOrder select(TableOrder bo);

    PageInfo<TableOrder> queryListPage(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
