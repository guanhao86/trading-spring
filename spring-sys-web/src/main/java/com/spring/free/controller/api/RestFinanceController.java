package com.spring.free.controller.api;


import com.github.pagehelper.PageInfo;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.domain.PageParamDTO;
import com.spring.free.domain.CommissionRecord;
import com.spring.free.domain.Fee;
import com.spring.free.domain.VirtualRecord;
import com.spring.free.service.CommissionRecordService;
import com.spring.free.service.FeeService;
import com.spring.free.service.VirtualRecordService;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.websocket.WebSocket;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName RestFinanceController
 * @Description //财务管理相关接口
 * @Author hengpu
 * @Date 2019/5/16 11:07
 * @Version 1.0
 **/
@RequestMapping("/api/finance")
@Controller
public class RestFinanceController {
    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    @Autowired
    private FeeService feeService;
    @Autowired
    private CommissionRecordService commissionRecordService;
    @Autowired
    private VirtualRecordService virtualRecordService;

    //获取手续费
    @RequestMapping("/fee/{type}")
    @ResponseBody
    public AccessResponse getFee( @PathVariable Integer type,HttpServletRequest request, HttpServletResponse response){
        Fee fee=feeService.getByType(type);
        if (fee!=null) {
            return AccessResponse.builder().data(fee.getFee()).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }else{
            return AccessResponse.builder().data(null).success(false).rspcode(200).message("服务端处理请求成功。").build();
        }
    }
    //增加委托交易记录
    @RequestMapping("/commissionRecord/add")
    @ResponseBody
    public AccessResponse addcommissionRecordRecord(@RequestBody CommissionRecord commissionRecord, HttpServletRequest request, HttpServletResponse response){
        //{"amount":1.0,"fee":1.0,"memId":1,"mmeberId":"1","qcCoin":1.0,"type":1,"image":"123123"}
        commissionRecord.setState(0);
        commissionRecordService.insert(commissionRecord);


        return AccessResponse.builder().data("操作成功").success(true).rspcode(200).message("服务端处理请求成功。").build();

    }
    //查询委托交易列表
    @RequestMapping("/commissionRecord/list")
    @ResponseBody
    public AccessResponse commissionRecordList(@RequestBody CommissionRecord commissionRecord, HttpServletRequest request, HttpServletResponse response){
        PageInfo<CommissionRecord> pageInfo = commissionRecordService.pageList(commissionRecord,commissionRecord.getPageNum(),commissionRecord.getPageSize());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    //查看委托交易
    @RequestMapping("/commissionRecord/{id}")
    @ResponseBody
    public AccessResponse getCommissionRecord( @PathVariable Long id,HttpServletRequest request, HttpServletResponse response){
        CommissionRecord commissionRecord=commissionRecordService.get(id);
        if (commissionRecord!=null) {
            return AccessResponse.builder().data(commissionRecord).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }else{
            return AccessResponse.builder().data(null).success(false).rspcode(200).message("服务端处理请求成功。").build();
        }
    }

    //增加虚拟币交易记录
    @RequestMapping("/virtualRecord/add")
    @ResponseBody
    public AccessResponse addVirtualRecordRecord(@RequestBody VirtualRecord virtualRecord, HttpServletRequest request, HttpServletResponse response){
        //{"amount":1.0,"fee":1.0,"memId":1,"mmeberId":"1","qcCoin":1.0,"type":1,"image":"123123"}
        virtualRecord.setState(0);
        //提币计算手续费
        if(virtualRecord.getType()==2){
            Fee fee=feeService.getByType(2);
            Double f=virtualRecord.getQcCoin().doubleValue()*1000*fee.getFee()/100;
            virtualRecord.setFee(f.intValue());
        }
        virtualRecordService.insert(virtualRecord);


        return AccessResponse.builder().data("操作成功").success(true).rspcode(200).message("服务端处理请求成功。").build();

    }
    //查询虚拟币交易列表
    @RequestMapping("/virtualRecord/list")
    @ResponseBody
    public AccessResponse virtualRecordRecordList(@RequestBody VirtualRecord virtualRecord, HttpServletRequest request, HttpServletResponse response){
        PageInfo<VirtualRecord> pageInfo = virtualRecordService.pageList(virtualRecord,virtualRecord.getPageNum(),virtualRecord.getPageSize());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    //查看虚拟币交易
    @RequestMapping("/virtualRecord/{id}")
    @ResponseBody
    public AccessResponse getVirtualRecordRecord( @PathVariable Long id,HttpServletRequest request, HttpServletResponse response){
        VirtualRecord virtualRecord=virtualRecordService.get(id);
        if (virtualRecord!=null) {
            return AccessResponse.builder().data(virtualRecord).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }else{
            return AccessResponse.builder().data(null).success(false).rspcode(200).message("服务端处理请求成功。").build();
        }
    }

    //获取手续费
    @RequestMapping("/market/getMarket")
    @ResponseBody
    public AccessResponse getMarket(HttpServletRequest request, HttpServletResponse response){
        return AccessResponse.builder().data(WebSocket.getMarketList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
}
