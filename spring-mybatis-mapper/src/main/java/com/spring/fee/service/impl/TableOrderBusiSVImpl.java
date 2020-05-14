package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TableOrderMapper;
import com.spring.fee.dao.mapper.TableOrderMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.common.util.ExcelUtils;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableMemberLevelChangeDetailBusiSV iTableMemberLevelChangeDetailBusiSV;

    @Autowired
    TableOrderMapperDZ iTableOrderMapperDZ;

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
    public TableOrder selectByOrderId(String orderId) {
        TableOrderExample example = new TableOrderExample();
        TableOrderExample.Criteria criteria = example.createCriteria();

        criteria.andOrderIdEqualTo(orderId);

        List<TableOrder> tableOrderLIst = this.iTableOrderMapper.selectByExample(example);

        if (tableOrderLIst.size() == 1) {
            return tableOrderLIst.get(0);
        }

        return null;
    }


    @Override
    public TableOrder buy(TableOrder bo) {

        /**
         * 如果操作员和会员不相同，则从操作员扣款，其他操作均为会员信息
         */

        UserInfo user = BaseGetPrincipal.getUser();
        TableMember operMember = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());
        TableMember member;

        //判断操作员和会员是否是同一人
        if (StringUtils.isNotEmpty(bo.getMemberId()) && !user.getUsername().equals(bo.getMemberId())) {
            bo.setOrderType("2");
            member = this.iTableMemberBusiSV.selectByMemberId(bo.getMemberId());
        } else {
            bo.setOrderType("1");
            member = operMember;
        }

        //计算总金额
        TableGoods goods = new TableGoods();
        goods.setId(bo.getGoodsId());
        goods = this.iTableGoodsBusiSV.select(goods);

        bo.setGoodsClass(goods.getGoodsClass());
        bo.setGoodsId(goods.getId());
        bo.setState(1);

        Float price = bo.getAmount() * goods.getPrice();

        //是否购买了金鸡商品
        if (StringUtils.isNotEmpty(bo.getExtentGoodsId())) {
            //计算金鸡商品价格
            TableGoods ex = new TableGoods();
            ex.setId(Integer.parseInt(bo.getExtentGoodsId()));
            ex = this.iTableGoodsBusiSV.select(ex);
            price += ex.getPrice() * bo.getExtentGoodsCount();
            bo.setExtentGoodsPrice(ex.getPrice());
        }

        bo.setPrice(price);

        //计算总积分
        bo.setScorePrice(bo.getAmount() * (goods.getScorePrice()==null?0:goods.getScorePrice()));

        String remark = "商品购买";

        //报单商品：
        if (0 == goods.getGoodsClass()) {

            if (bo.getAmount() > 1) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "报单商品只允许购买一个！", "", null);
            }

            //报单商品只能购买一个
            bo.setPrice(goods.getPrice());

            if ("2".equals(bo.getOrderType())) {
                remark = "替会员【"+member.getMemberId()+"】购买报单商品";
            }else{
                remark = "报单商品购买";
            }

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
        if (bo.getPrice() > 0) {
            this.iMemberAccountDetailBusiSV.changeMoney(
                    operMember.getMemberId(),
                    "2",
                    bo.getPrice(),
                    remark,
                    null);
        }
        //扣减积分
        if (bo.getScorePrice() > 0) {
            this.iMemberAccountDetailBusiSV.changeMoney(
                    operMember.getMemberId(),
                    "2",
                    (float)bo.getScorePrice(),
                    remark,
                    6);
        }

        //生成订单
        bo = this.insert(bo);

        //购买的商品存在金鸡产品，把金鸡商品插入用户商品表
        if (StringUtils.isNotEmpty(goods.getExtentGoodsId())) {
            //购买的商品存在金鸡产品，把金鸡商品插入用户商品表
            log.info("购买的商品存在金鸡产品，把金鸡商品插入用户商品表");
            createMemberGoods(Integer.parseInt(goods.getExtentGoodsId()), goods.getExtentGoodsCount(), bo, InvestConstants.MemberGoodsType.type1);
        }
        if (StringUtils.isNotEmpty(bo.getExtentGoodsId())) {
            //购买的商品存在金鸡产品，把金鸡商品插入用户商品表
            log.info("订单额外购买金鸡产品，把金鸡商品插入用户商品表");
            createMemberGoods(Integer.parseInt(bo.getExtentGoodsId()), bo.getExtentGoodsCount(), bo, InvestConstants.MemberGoodsType.type2);
        }

        return bo;
    }

    public void createMemberGoods(int exGoogsId, int exGoodsCount, TableOrder order, String memberGoodsType){
        log.info("把金鸡商品插入用户商品表");
        //查询商品属性
        TableGoods ex = new TableGoods();
        ex.setId(exGoogsId);
        ex = this.iTableGoodsBusiSV.select(ex);

        TableMemberGoods memberGoods = new TableMemberGoods();
        memberGoods.setMemberId(order.getMemberId());
        memberGoods.setOrderId(order.getOrderId());
        memberGoods.setGoodsId(ex.getId());
        memberGoods.setGoodsClass(ex.getGoodsClass());
        memberGoods.setAddScoreByonegoods(ex.getAddScore());
        memberGoods.setAmount(exGoodsCount * order.getAmount());  //每个商品赠送金鸡数 * 商品数量
        memberGoods.setCreateTime(DateUtils.getSysDate());
        memberGoods.setType(memberGoodsType);
        memberGoods.setAddCount(0);
        this.iTableMemberGoodsBusiSV.insert(memberGoods);
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

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andCreateTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andCreateTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }

        example.setOrderByClause("create_time desc");

        PageInfo<TableOrder> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableOrderMapper.selectByExample(example));
        log.info("获取订单结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    @Override
    public TableOrderDZ selectByGroup(TableOrderDZ bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        return this.iTableOrderMapperDZ.selectByGroup(bo.getMemberId(), bo.getStart(), bo.getEnd());
    }

    /**
     * 导出订单
     *
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public HSSFWorkbook exportFile(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        PageInfo<TableOrder> pageInfo = this.queryListPage(bo, 1, 10000000, map);

        List<TableOrder> list = new ArrayList<>();
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            list = pageInfo.getList();
        } else {
            System.out.println("没有数据");
        }

        String sheetName = "传输计划";
        String[] title = {"ID", "快递单号", "订单编号", "会员编号", "商品ID", "商品数量", "总金额", "订单时间", "状态", "收货人姓名", "收货人电话", "收货人地址"};
        String[][] values = new String[list.size()+1][title.length];

        int i = 0;
        for (TableOrder order : list) {
            values[i][0] = String.valueOf(order.getId());
            values[i][1] = order.getExpressNumber();
            values[i][2] = order.getOrderId();
            values[i][3] = order.getMemberId();
            values[i][4] = String.valueOf(order.getGoodsId());
            values[i][5] = String.valueOf(order.getAmount());
            values[i][6] = String.valueOf(order.getPrice());
            values[i][7] = DateUtils.formatDateTime(order.getCreateTime());
            String state = "";
            if (1 == order.getState()) {
                state = "等待发货";
            } else if (2 == order.getState()) {
                state = "发货完成";
            }
            values[i][8] = state;
            values[i][9] = order.getReceiverName();
            values[i][10] = order.getReceiverPhone();
            values[i][11] = order.getReceiverAddr();
            i++;
        }

        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, values, null);
        return wb;
    }

    /**
     * 批量发货
     *
     * @param orderList
     * @return
     */
    @Override
    public List<TableOrder> sendOrder(List<TableOrder> orderList) {
        List<TableOrder> list = new ArrayList<>();
        for (TableOrder tableOrder : orderList) {
            if (StringUtils.isNotEmpty(tableOrder.getExpressNumber())) {
                //查询订单
                TableOrder orig = this.selectByOrderId(tableOrder.getOrderId());
                if (null == orig) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "订单编号="+tableOrder.getOrderId()+"未查询到订单信息，请确认订单编号是否正确", "", null);
                }

                if (orig.getState() == 2) {
                    continue;
                }

                if (StringUtils.isNotEmpty(orig.getExpressNumber())) {
                    continue;
                }

                TableOrder update = new TableOrder();
                update.setId(tableOrder.getId());
                update.setState(2);
                update.setExpressNumber(tableOrder.getExpressNumber());
                this.update(update);
                list.add(update);
            }

        }

        return list;
    }
}
