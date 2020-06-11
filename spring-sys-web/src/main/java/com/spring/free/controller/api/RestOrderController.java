package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableOrder;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.PassToken;
import com.spring.free.config.TokenUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单接口
 */
@RequestMapping("/api/order")
@Controller
@Slf4j
public class RestOrderController {

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    private ITableOrderBusiSV iTableOrderBusiSV;

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

    /**
     * 订单列表
     */
    @RequestMapping(value = "/getList")
    public @ResponseBody
    AccessResponse getList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("订单列表:{}");
        //返回体
        TableOrder query = new TableOrder();
        GoodsRspVO goodsRspVO = new GoodsRspVO();
        PageInfo<OrderRspVO> pageInfo = new PageInfo<>();
        List<OrderRspVO> list = new ArrayList();
        try {

            String memberId = TokenUtil.getUserId(request);

            BeanUtils.copyProperties(queryReqVO, query);
            query.setMemberId(memberId);
            PageInfo<TableOrder> tableGoodsPageInfo = this.iTableOrderBusiSV.queryListPage(query, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);
            BeanUtils.copyProperties(tableGoodsPageInfo, pageInfo);

            //获取商品信息
            Map<Integer, TableGoods> goodsMap = iTableGoodsBusiSV.selectMap(new TableGoods());
            for (TableOrder order : tableGoodsPageInfo.getList()) {
                OrderRspVO vo = new OrderRspVO();
                BeanUtils.copyProperties(order, vo);
                vo.setTableGoods(goodsMap.get(vo.getGoodsId()));
                list.add(vo);
            }
            pageInfo.setList(list);

        }catch (Exception e) {
            return AccessResponse.builder().data(goodsRspVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/getDetail")
    public @ResponseBody
    AccessResponse getList(@RequestBody TableOrder tableOrder, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("订单详情:{}");
        //返回体
        TableOrderVO vo = new TableOrderVO();
        try {

            tableOrder = this.iTableOrderBusiSV.select(tableOrder);
            BeanUtils.copyProperties(tableOrder, vo);

            //获取商品信息
            TableGoods tableGoods = new TableGoods();
            tableGoods.setId(tableOrder.getGoodsId());
            tableGoods = iTableGoodsBusiSV.select(tableGoods);
            vo.setTableGoods(tableGoods);

        }catch (Exception e) {
            return AccessResponse.builder().data(vo).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(vo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 商品购买(预购买)
     */
    @RequestMapping(value = "/buyIndex")
    public @ResponseBody
    AccessResponse buyIndex(@RequestBody TableOrder tableOrder, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("商品购买:{}");

        TableOrderVO tableOrderVO = new TableOrderVO();
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMember member = this.iTableMemberBusiSV.selectByMemberId(memberId);

            //计算总金额
            TableGoods goods = new TableGoods();
            goods.setId(tableOrder.getGoodsId());
            goods = this.iTableGoodsBusiSV.select(goods);

            float price = tableOrder.getAmount() * goods.getPrice();
            float scorePrice = tableOrder.getAmount() * (goods.getScorePrice()==null?0:goods.getScorePrice());

            //额外购买金鸡商品，目前无此场景
            if (StringUtils.isNotEmpty(tableOrder.getExtentGoodsId())) {
                TableGoods extGoods = new TableGoods();
                extGoods.setId(Integer.parseInt(tableOrder.getExtentGoodsId()));
                extGoods = this.iTableGoodsBusiSV.select(extGoods);

                price += extGoods.getPrice() * (tableOrder.getExtentGoodsCount()==null?0:tableOrder.getExtentGoodsCount());
            }

            tableOrder.setPrice(new BigDecimal(price));
            tableOrder.setScorePrice((int)scorePrice);
            tableOrder.setGoodsClass(goods.getGoodsClass());

            //计算报单商品，购买升级产品金额
            try {
                tableOrder.setOperMemberId(memberId);
                this.iTableOrderBusiSV.getBDGoodsOrderPrice(tableOrder);
            }catch (Exception e) {
                e.printStackTrace();
                return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
            }

            if (member.getAccountMoney().floatValue() < tableOrder.getPrice().floatValue()) {
                return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("余额不足").build();
            }
            if (member.getScore() < tableOrder.getScorePrice()) {
                return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("金蛋不足").build();
            }

            BeanUtils.copyProperties(tableOrder, tableOrderVO);
            tableOrderVO.setTableGoods(goods);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableOrderVO).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }


    /**
     * 订单确认
     */
    @RequestMapping(value = "/create")
    public @ResponseBody
    AccessResponse create(@RequestBody TableOrder tableOrder, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("商品购买:{}", JSON.toJSON(tableOrder));
        //返回体
        try {

            String memberId = TokenUtil.getUserId(request);
            tableOrder.setOperMemberId(memberId);
            tableOrder = this.iTableOrderBusiSV.buy(tableOrder);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableOrder).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

}
