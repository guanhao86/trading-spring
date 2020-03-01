package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableGoods;

import java.util.Map;

/**
 * 商品服务
 */
public interface ITableGoodsBusiSV {

    TableGoods insert(TableGoods bo);

    TableGoods update(TableGoods bo);

    TableGoods delete(TableGoods bo);

    TableGoods select(TableGoods bo);

    PageInfo<TableGoods> queryListPage(TableGoods bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
