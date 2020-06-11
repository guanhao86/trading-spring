package com.spring.free.controller.api;


import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.PassToken;
import com.spring.free.config.TokenUtil;
import com.spring.free.vo.GoodsRspVO;
import com.spring.free.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品接口
 */
@RequestMapping("/api/goods")
@Controller
@Slf4j
public class RestGoodsController {

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    private ITableGoodsBusiSV iTableGoodsBusiSV;

    /**
     * 商品详情
     */
    @PassToken
    @RequestMapping(value = "/getDetail/{goodsId}")
    public @ResponseBody
    AccessResponse getDetail(@PathVariable String goodsId, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("商品ID:{}",goodsId);
        //返回体
        TableGoods tableGoods = new TableGoods();
        try {
            tableGoods.setId(Integer.parseInt(goodsId));
            tableGoods = iTableGoodsBusiSV.select(tableGoods);
        }catch (Exception e) {
            return AccessResponse.builder().data(tableGoods).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableGoods).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 商品列表
     * 金蛋商品列表  我的金蛋
     */
    @RequestMapping(value = "/getList")
    public @ResponseBody
    AccessResponse getList(@RequestBody GoodsVO goodsVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("商品列表:{}");
        //返回体
        TableGoods tableGoods = new TableGoods();
        GoodsRspVO goodsRspVO = new GoodsRspVO();
        PageInfo<TableGoods> tableGoodsPageInfo = new PageInfo<>();
        try {

            Map<String, Object> map = new HashMap<>();
            if (!StringUtils.isEmpty(goodsVO.getSort())){
                map.put("sort", goodsVO.getSort());
            }
            //商品详情
            String goodsClass = goodsVO.getGoodsClass();
            if (org.apache.commons.lang.StringUtils.isNotEmpty(goodsClass)) {
                if (goodsClass.indexOf(",") > 0) {
                    List<String> goodsClassList = Arrays.asList(goodsClass.split(","));
                    map.put("goodsClassIn", goodsClassList);
                } else {
                    tableGoods.setGoodsClass(Integer.parseInt(goodsClass));
                }
            }

            tableGoods.setState(InvestConstants.GoodsState.effect);

            tableGoodsPageInfo = iTableGoodsBusiSV.queryListPage(tableGoods, goodsVO.getPageNum(), goodsVO.getPageSize(), map);

            //会员详情
            String memberId = TokenUtil.getUserId(request);
            TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(memberId);

            goodsRspVO.setGoodsPageInfo(tableGoodsPageInfo);
            goodsRspVO.setTableMember(tableMember);
        }catch (Exception e) {
            return AccessResponse.builder().data(goodsRspVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(goodsRspVO).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

}
