package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableGoodsMapper;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableGoodsExample;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品服务
 */
@Slf4j
@Service
@Transactional
public class TableGoodsBusiSVImpl implements ITableGoodsBusiSV {

    @Autowired
    TableGoodsMapper iTableGoodsMapper;

    /**
     * 创建商品记录
     * @param bo
     * @return
     */
    @Override
    public TableGoods insert(TableGoods bo) {
        log.info("创建商品参数bo：{}", JSON.toJSON(bo));
        iTableGoodsMapper.insert(bo);
        return bo;
    }

    @Override
    public TableGoods update(TableGoods bo) {
        if (this.iTableGoodsMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableGoods delete(TableGoods bo) {
        if (this.iTableGoodsMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableGoods select(TableGoods bo) {
        return this.iTableGoodsMapper.selectByPrimaryKey(bo.getId());
    }

    @Override
    public Map<Integer, TableGoods> selectMap(TableGoods bo) {
        TableGoodsExample example = new TableGoodsExample();
        List<TableGoods> list = this.iTableGoodsMapper.selectByExample(example);
        Map<Integer, TableGoods> map = new HashMap<>();
        if (list != null) {
            for (TableGoods tableGoods : list) {
                map.put(tableGoods.getId(), tableGoods);
            }
        }
        return map;
    }

    /**
     * 数据列表分页
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TableGoods> queryListPage(TableGoods bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取商品参数bo：{}", JSON.toJSON(bo));
        log.info("获取商品参数pageNum：{}", pageNum);
        log.info("获取商品参数pageSize：{}", pageSize);
        log.info("获取商品参数map：{}", JSON.toJSON(map));
        
        TableGoodsExample example = new TableGoodsExample();
        TableGoodsExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (null != bo.getGoodsClass()) {
            criteria.andGoodsClassEqualTo(bo.getGoodsClass());
        }
        if (StringUtils.isNotEmpty(bo.getThunmbanilImgSrc())) {
            criteria.andThunmbanilImgSrcEqualTo(bo.getThunmbanilImgSrc());
        }
        if (StringUtils.isNotEmpty(bo.getDetailImgSrc())) {
            criteria.andDetailImgSrcEqualTo(bo.getDetailImgSrc());
        }
        if (null != bo.getPrice()) {
            criteria.andPriceEqualTo(bo.getPrice());
        }
        if (null != bo.getIncomeVipLevel()) {
            criteria.andIncomeVipLevelEqualTo(bo.getIncomeVipLevel());
        }
        if (null != bo.getIncomeDjPoint()) {
            criteria.andIncomeDjPointEqualTo(bo.getIncomeDjPoint());
        }
        if (null != bo.getIncomeJysPoint()) {
            criteria.andIncomeJysPointEqualTo(bo.getIncomeJysPoint());
        }
        if (null != bo.getPePrice()) {
            criteria.andPePriceEqualTo(bo.getPePrice());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (StringUtils.isNotEmpty(bo.getGoodsName())) {
            criteria.andGoodsNameEqualTo(bo.getGoodsName());
        }

        if (map != null) {
            if(null != map.get("goodsClassIn")){
                criteria.andGoodsClassIn((List)map.get("goodsClassIn"));
            }

            if(null != map.get("sort")){
                example.setOrderByClause((String)map.get("sort"));
            }
        }



        PageInfo<TableGoods> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableGoodsMapper.selectByExample(example));
        log.info("获取商品结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
