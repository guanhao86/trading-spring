package com.spring.free.vo;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import lombok.Data;

/**
 * 商品结果
 */
@Data
public class GoodsRspVO {

    PageInfo<TableGoods> goodsPageInfo;

    TableMember tableMember;



}
