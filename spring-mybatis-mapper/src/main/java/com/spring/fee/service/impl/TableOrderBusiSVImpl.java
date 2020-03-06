package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableOrderMapper;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableOrder;
import com.spring.fee.model.TableOrderExample;
import com.spring.fee.service.*;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 订单服务
 */
@Slf4j
@Service
@Transactional
public class TableOrderBusiSVImpl implements ITableOrderBusiSV {

    @Autowired
    TableOrderMapper iTableOrderMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableMemberLevelChangeDetailBusiSV iTableMemberLevelChangeDetailBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableOrder insert(TableOrder bo) {
        log.info("创建订单参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setCreateTime(sysdate);
        bo.setOrderId(DateUtils.getYYYYMMDDHHMISS(sysdate)+(int)(Math.random()*10000));
        iTableOrderMapper.insert(bo);
        return bo;
    }

    @Override
    public TableOrder update(TableOrder bo) {
        if (this.iTableOrderMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableOrder delete(TableOrder bo) {
        if (this.iTableOrderMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableOrder select(TableOrder bo) {
        return this.iTableOrderMapper.selectByPrimaryKey(bo.getId());
    }

    @Override
    public TableOrder buy(TableOrder bo) {
        UserInfo user = BaseGetPrincipal.getUser();

        TableMember member = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

        //计算总金额
        TableGoods goods = new TableGoods();
        goods.setId(bo.getGoodsId());
        goods = this.iTableGoodsBusiSV.select(goods);

        bo.setGoodsClass(goods.getGoodsClass());
        bo.setGoodsId(goods.getId());
        bo.setState(1);

        bo.setPrice(bo.getAmount() * goods.getPrice());

        String remark = "商品购买";

        //报单商品：
        if (0 == goods.getGoodsClass()) {

            if (bo.getAmount() > 1) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "报单商品只允许购买一个！", "", null);
            }

            //报单商品只能购买一个
            bo.setPrice(goods.getPrice());

            remark = "报单商品购买";

            //修改会员信息
            member.setLevel(goods.getIncomeVipLevel());
            member.setReportingAmount(bo.getPrice());
            //当前系统时间.month + 2 , day = 1号
            member.setPerfomanceTime(DateUtils.getFirstDayOfMonth(DateUtils.addMonths(DateUtils.getSysDate(), 2)));

            /*
                【逻辑2】
                更新table_member_level_change_detail表，记录会员级别变更履历
                remark = “报单商品购买”
            */
            iTableMemberLevelChangeDetailBusiSV.changeLevel(member.getMemberId(), goods.getIncomeVipLevel(), "报单商品购买");
        }

        //扣减金额
        this.iMemberAccountDetailBusiSV.changeMoney(
                member.getMemberId(),
                "2",
                bo.getPrice(),
                remark);


        //生成订单
        bo = this.insert(bo);

        return bo;
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
    public PageInfo<TableOrder> queryListPage(TableOrder bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取订单参数bo：{}", JSON.toJSON(bo));
        log.info("获取订单参数pageNum：{}", pageNum);
        log.info("获取订单参数pageSize：{}", pageSize);
        log.info("获取订单参数map：{}", JSON.toJSON(map));
        
        TableOrderExample example = new TableOrderExample();
        TableOrderExample.Criteria criteria = example.createCriteria();

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
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (StringUtils.isNotEmpty(bo.getExpressNumber())) {
            criteria.andExpressNumberEqualTo(bo.getExpressNumber());
        }

        PageInfo<TableOrder> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableOrderMapper.selectByExample(example));
        log.info("获取订单结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}