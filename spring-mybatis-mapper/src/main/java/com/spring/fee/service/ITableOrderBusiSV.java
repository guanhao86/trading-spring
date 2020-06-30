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
    /**
     * 计算报单商品订单金额
     * @param bo
     */
    void getBDGoodsOrderPrice(TableOrder bo);

    PageInfo<TableOrder> queryListPage(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    List<TableOrder> queryList(TableOrder bo, Map<String, Object> map);

    TableOrderDZ selectByGroup(TableOrderDZ bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 总业绩=》所有订单表（报单商品、复消商品、金鸡商品）提货商品的不算，被删除的（退货订单）不算，所有这些符合条件的订单，时间也是截至到昨日的订单金额求和
     * @return
     */
    TableOrderDZ selectByGroup2(String memberId, Date start, Date end);

    /**
     * 13、结算管理菜单中加入一个子菜单“管理业绩分析”
     * 进入界面后输入会员编号和时间段（开始时间和结束时间），“分析”按钮
     * 列表为左区和右区，点击分析按钮查询该会员在设定时间段内的左区和右区的业绩
     * 业绩计算方法：根据网体结构找到会员的所有子节点，根据时间段获取该时间段内这些会员子节点的所有报单商品的订单求和，左区的所有会员订单求和后显示在左区，右区的所有订单求和后显示在右区
     * @param memberList
     * @param start
     * @param end
     * @return
     */
    List<TableOrderDZ> selectByGroup3(List<String> memberList, Date start, Date end);


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
