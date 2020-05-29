package com.spring.free.controller.api;


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
import com.spring.free.vo.GoodsRspVO;
import com.spring.free.vo.GoodsVO;
import com.spring.free.vo.OrderRspVO;
import com.spring.free.vo.QueryReqVO;
import lombok.extern.slf4j.Slf4j;
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

}
