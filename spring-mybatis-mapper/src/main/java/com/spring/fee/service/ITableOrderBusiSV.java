package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableOrder;
import com.spring.fee.model.TableOrderDZ;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Date;
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

    List<TableOrder> queryList(TableOrder bo, Map<String, Object> map);

    TableOrderDZ selectByGroup(TableOrderDZ bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 总业绩=》所有订单表（报单商品、复消商品、金鸡商品）提货商品的不算，被删除的（退货订单）不算，所有这些符合条件的订单，时间也是截至到昨日的订单金额求和
     * @return
     */
    TableOrderDZ selectByGroup2(String memberId, Date start, Date end);


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
