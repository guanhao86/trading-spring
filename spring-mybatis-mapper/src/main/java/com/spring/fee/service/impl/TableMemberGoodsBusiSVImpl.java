package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TableMemberGoodsMapper;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableMemberGoods;
import com.spring.fee.model.TableMemberGoodsExample;
import com.spring.fee.model.TableTask;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableMemberGoodsBusiSV;
import com.spring.fee.service.ITableTaskBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    ITableTaskBusiSV iTableTaskBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    /**
     * 创建会员持有商品记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberGoods insert(TableMemberGoods bo) {
        log.info("会员持有商品参数bo：{}", JSON.toJSON(bo));
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
            Date sysdate = DateUtils.getSysDate();
            List<TableMemberGoods> tableMemberGoodsList = this.getList();
            for (TableMemberGoods tableMemberGoods : tableMemberGoodsList) {
                log.info("金鸡信息{}", JSONObject.toJSON(tableMemberGoods));

                //插入奖金表
                TableBonusDetail tableBonusDetail = new TableBonusDetail();
                tableBonusDetail.setMemberId(tableMemberGoods.getMemberId());
                tableBonusDetail.setRecodeTime(sysdate);
                tableBonusDetail.setBonus((float)tableMemberGoods.getAmount() * tableMemberGoods.getAddScoreByonegoods());
                tableBonusDetail.setBonusId(Integer.parseInt(InvestConstants.BonusId.BONUS_ID_7));
                tableBonusDetail.setBonusType(Integer.parseInt(InvestConstants.BonusType.BONUS_TYPE_5));
                tableBonusDetail.setRemark("金鸡下蛋");
                tableBonusDetail.setCloseState(1);

                iTableBonusDetailBusiSV.insert(tableBonusDetail);

                //插入账户变更表
                iMemberAccountDetailBusiSV.changeMoney(tableMemberGoods.getMemberId(),
                        "1",
                        (float)tableMemberGoods.getAmount() * tableMemberGoods.getAddScoreByonegoods(),
                                "金鸡下蛋",
                        6
                        );

                //更新最后一次下蛋时间
                tableMemberGoods.setLastTime(DateUtils.getSysDate());
                this.update(tableMemberGoods);
            }
        }
    }

    public List<TableMemberGoods> getList(){
        TableMemberGoodsExample example = new TableMemberGoodsExample();
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

        PageInfo<TableMemberGoods> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberGoodsMapper.selectByExample(example));
        log.info("获取会员持有商品结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
