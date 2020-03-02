package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableRepurchaseDetail;

import java.util.Map;

/**
 * 复消记录表服务
 */
public interface ITableRepurchaseDetailBusiSV {

    TableRepurchaseDetail insert(TableRepurchaseDetail bo);

    TableRepurchaseDetail update(TableRepurchaseDetail bo);

    TableRepurchaseDetail delete(TableRepurchaseDetail bo);

    TableRepurchaseDetail select(TableRepurchaseDetail bo);

    PageInfo<TableRepurchaseDetail> queryListPage(TableRepurchaseDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
