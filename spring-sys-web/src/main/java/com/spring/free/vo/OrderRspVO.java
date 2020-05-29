package com.spring.free.vo;

import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableOrder;
import lombok.Data;

/**
 * 商品结果
 */
@Data
public class OrderRspVO extends TableOrder {

    TableGoods tableGoods;

}
