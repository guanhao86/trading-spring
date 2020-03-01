package com.spring.free.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatFinancial;
import com.spring.fee.model.TWheatFinancialOrder;
import com.spring.fee.model.TWheatFinancialStream;
import com.spring.fee.service.ITWheatFinancialBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.domain.PageParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/financial")
@Controller
public class RestFinancialController {
    @Autowired
    private ITWheatFinancialBusiSV iTWheatFinancialBusiSV;

    /*
        理财列表
     */
    @RequestMapping(value = "/getFinancialList/{page}")
    public @ResponseBody
    AccessResponse getFinancialList(TWheatFinancial tWheatFinancial ,@PathVariable Integer page, PageParamDTO pageParamDTO){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        tWheatFinancial.setState(1);
        tWheatFinancial.setDueTime(new Date());
        PageInfo<TWheatFinancial> pageInfo = iTWheatFinancialBusiSV.pageList(tWheatFinancial,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        理财详情
     */
    @RequestMapping(value = "/getFinancialInfo/{id}")
    public @ResponseBody
    AccessResponse getFinancialList(TWheatFinancial tWheatFinancial ,@PathVariable Long id, PageParamDTO pageParamDTO){
        tWheatFinancial.setId(id);
        TWheatFinancial bean = iTWheatFinancialBusiSV.select(tWheatFinancial);
        return AccessResponse.builder().data(bean).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        购买理财
     */
    @RequestMapping(value = "/createOrder/{memberId}")
    public @ResponseBody
    AccessResponse createOrder(TWheatFinancial tWheatFinancial ,@PathVariable String memberId,Long id,Integer num){
        //返回体
        JSONObject jsonObj = iTWheatFinancialBusiSV.createOrder(memberId,id,num);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        累计理财收益
     */
    @RequestMapping(value = "/financialMoney/{memberId}")
    public @ResponseBody
    AccessResponse financialMoney(TWheatFinancial tWheatFinancial ,@PathVariable String memberId){
        //返回体
        JSONObject jsonObj = iTWheatFinancialBusiSV.financialMoney(memberId);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        理财订单列表
     */
    @RequestMapping(value = "/getFinancialOrderList/{page}")
    public @ResponseBody
    AccessResponse getFinancialOrderList(@PathVariable Integer page, String memberId ,PageParamDTO pageParamDTO){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();

        PageInfo<TWheatFinancialOrder> pageInfo = iTWheatFinancialBusiSV.orderPageList(memberId,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        理财收益明细
     */
    @RequestMapping(value = "/getFinancialStreamList/{memberId}")
    public @ResponseBody
    AccessResponse getFinancialStreamList(@PathVariable String memberId ,PageParamDTO pageParamDTO){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();

        List<TWheatFinancialStream> list = iTWheatFinancialBusiSV.streamList(memberId);
        return AccessResponse.builder().data(list).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
}
