package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMemberGoods;
import com.spring.fee.model.TableMemberGoodsDZ;

import java.util.List;
import java.util.Map;

/**
 * 会员持有商品服务
 */
public interface ITableMemberGoodsBusiSV {

    TableMemberGoods insert(TableMemberGoods bo);

    TableMemberGoods update(TableMemberGoods bo);

    TableMemberGoods delete(TableMemberGoods bo);

    TableMemberGoods select(TableMemberGoods bo);

    /**
     * 计算积分
     */
    void calcScore();

    /**
     * 计算积分
     */
    void calcScoreRun(TableMemberGoods tableMemberGoods);

    List<TableMemberGoods> getListValid(TableMemberGoods tableMemberGoods);

    PageInfo<TableMemberGoods> queryListPage(TableMemberGoods bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    PageInfo<TableMemberGoodsDZ> queryListPageDZ(TableMemberGoodsDZ bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) ;

    List<TableMemberGoodsDZ> selectByGroup(TableMemberGoodsDZ bo) ;
}
