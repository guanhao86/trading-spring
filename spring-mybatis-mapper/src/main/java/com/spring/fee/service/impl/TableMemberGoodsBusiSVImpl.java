package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TableMemberGoodsMapper;
import com.spring.fee.dao.mapper.TableMemberGoodsMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员持有商品服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberGoodsBusiSVImpl implements ITableMemberGoodsBusiSV {

    @Autowired
    TableMemberGoodsMapper iTableMemberGoodsMapper;

    @Autowired
    TableMemberGoodsMapperDZ iTableMemberGoodsMapperDZ;

    @Autowired
    ITableTaskBusiSV iTableTaskBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    /**
     * 创建会员持有商品记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberGoods insert(TableMemberGoods bo) {
        log.info("会员持有商品参数bo：{}", JSON.toJSON(bo));
        bo.setAddTimes(0);
        bo.setAddCount(0);
        bo.setCreateTime(DateUtils.getSysDate());
        bo.setInvalidTime(DateUtils.getNextYear(DateUtils.getSysDate()));
        iTableMemberGoodsMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberGoods update(TableMemberGoods bo) {
        if (this.iTableMemberGoodsMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberGoods delete(TableMemberGoods bo) {
        if (this.iTableMemberGoodsMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberGoods select(TableMemberGoods bo) {
        return this.iTableMemberGoodsMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 计算积分
     */
    @Override
    public void calcScore() {
        TableTask tableTask = new TableTask();
        tableTask.setTaskType(1);
        //避免重复执行（集群）
        if (iTableTaskBusiSV.insert(tableTask)) {
            log.info("开始下蛋");
            //获取所有金鸡

            List<TableMemberGoods> tableMemberGoodsList = this.getListValid(null);
            for (TableMemberGoods tableMemberGoods : tableMemberGoodsList) {
                try {
                    iTableMemberGoodsBusiSV.calcScoreRun(tableMemberGoods);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 计算积分
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void calcScoreRun(TableMemberGoods tableMemberGoods) {
        try {
            log.info("金鸡信息{}", JSONObject.toJSON(tableMemberGoods));
            Date sysdate = DateUtils.getSysDate();
            if (tableMemberGoods.getLastTime() != null && DateUtils.getYYYYMMDD(sysdate).equals(DateUtils.getYYYYMMDD(tableMemberGoods.getLastTime()))){
                log.info("已经执行过不能重复执行");
                return;
            }
            //判断会员是否存在
            TableMember tableMember = iTableMemberBusiSV.selectByMemberId(tableMemberGoods.getMemberId());
            if (tableMember == null || InvestConstants.MemberState.INVALID.equals(tableMember.getState())) {
                //会员不存在
                log.info("会员编号：" + tableMemberGoods.getMemberId() + "会员信息不存在，或已删除");
                return;
            }

            //插入奖金表
            TableBonusDetail tableBonusDetail = new TableBonusDetail();
            tableBonusDetail.setMemberId(tableMemberGoods.getMemberId());
            tableBonusDetail.setRecodeTime(sysdate);
            tableBonusDetail.setBonus(new BigDecimal(tableMemberGoods.getAmount() * tableMemberGoods.getAddScoreByonegoods()));
            tableBonusDetail.setBonusId(Integer.parseInt(InvestConstants.BonusId.BONUS_ID_7));
            tableBonusDetail.setBonusType(Integer.parseInt(InvestConstants.BonusType.BONUS_TYPE_5));
            tableBonusDetail.setRemark("金鸡下蛋");
            tableBonusDetail.setCloseState(1);

            iTableBonusDetailBusiSV.insert(tableBonusDetail);

            int price = tableMemberGoods.getAmount() * tableMemberGoods.getAddScoreByonegoods();

            //插入账户变更表
            iMemberAccountDetailBusiSV.changeMoney(tableMemberGoods.getMemberId(),
                    "1",
                    (float) price,
                    "金鸡下蛋",
                    6
            );

            //更新最后一次下蛋时间
            tableMemberGoods.setLastTime(DateUtils.getSysDate());
            //已下金蛋数量
            tableMemberGoods.setAddCount(tableMemberGoods.getAddCount() + price);
            //已下金蛋次数
            tableMemberGoods.setAddTimes(tableMemberGoods.getAddTimes() + 1);
            this.update(tableMemberGoods);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<TableMemberGoods> getListValid(TableMemberGoods tableMemberGoods){
        TableMemberGoodsExample example = new TableMemberGoodsExample();
        TableMemberGoodsExample.Criteria criteria = example.createCriteria();

        if (tableMemberGoods != null) {
            if (StringUtils.isNotEmpty(tableMemberGoods.getMemberId())) {
                criteria.andMemberIdEqualTo(tableMemberGoods.getMemberId());
            }
        }

        criteria.andInvalidTimeGreaterThanOrEqualTo(DateUtils.getSysDate());

        return this.iTableMemberGoodsMapper.selectByExample(example);
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
    public PageInfo<TableMemberGoods> queryListPage(TableMemberGoods bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取会员持有商品参数bo：{}", JSON.toJSON(bo));
        log.info("获取会员持有商品参数pageNum：{}", pageNum);
        log.info("获取会员持有商品参数pageSize：{}", pageSize);
        log.info("获取会员持有商品参数map：{}", JSON.toJSON(map));
        
        TableMemberGoodsExample example = new TableMemberGoodsExample();
        TableMemberGoodsExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getOrderId())) {
            criteria.andOrderIdEqualTo(bo.getOrderId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getGoodsId()) {
            criteria.andGoodsIdEqualTo(bo.getGoodsId());
        }
        if (null != bo.getGoodsClass()) {
            criteria.andGoodsClassEqualTo(bo.getGoodsClass());
        }
        if (null != bo.getAmount()) {
            criteria.andAmountEqualTo(bo.getAmount());
        }
        if (null != bo.getPrice()) {
            criteria.andPriceEqualTo(bo.getPrice());
        }
        if (null != bo.getAddScoreByonegoods()) {
            criteria.andAddScoreByonegoodsEqualTo(bo.getAddScoreByonegoods());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }

        example.setOrderByClause(" create_time desc");

        PageInfo<TableMemberGoods> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberGoodsMapper.selectByExample(example));
        log.info("获取会员持有商品结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
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
    public PageInfo<TableMemberGoodsDZ> queryListPageDZ(TableMemberGoodsDZ bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取会员持有商品参数bo：{}", JSON.toJSON(bo));
        log.info("获取会员持有商品参数pageNum：{}", pageNum);
        log.info("获取会员持有商品参数pageSize：{}", pageSize);
        log.info("获取会员持有商品参数map：{}", JSON.toJSON(map));

        PageInfo<TableMemberGoodsDZ> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberGoodsMapperDZ.selectByExample(bo));
        log.info("获取会员持有商品结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    @Override
    public List<TableMemberGoodsDZ> selectByGroup(TableMemberGoodsDZ bo) {
        return this.iTableMemberGoodsMapperDZ.selectByGroup(bo);
    }
}