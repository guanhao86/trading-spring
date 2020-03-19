package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableOrder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * 订单管理表服务
 */
public interface ITableOrderBusiSV {

    TableOrder insert(TableOrder bo);

    TableOrder update(TableOrder bo);

    TableOrder delete(TableOrder bo);

    TableOrder select(TableOrder bo);

    TableOrder selectByOrderId(String orderId);

    TableOrder buy(TableOrder bo);

    PageInfo<TableOrder> queryListPage(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 导出订单
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    HSSFWorkbook exportFile(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 批量发货
     * @param orderList
     * @return
     */
    List<TableOrder> sendOrder(List<TableOrder> orderList);
}
