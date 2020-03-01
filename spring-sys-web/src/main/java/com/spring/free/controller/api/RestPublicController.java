package com.spring.free.controller.api;

import com.spring.fee.model.TWheatMember;
import com.spring.fee.service.ITWheatFinancialBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.service.AsyncRestService;
import com.spring.free.common.service.ImageUploadService;
import com.spring.free.common.service.SendSmsService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/public")
@Controller
public class RestPublicController {

    @Autowired
    private SendSmsService sendSmsService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private AsyncRestService asyncRestService;
    @Autowired
    private ITWheatFinancialBusiSV iTWheatFinancialBusiSV;


    @RequestMapping(value = "/sendSms/{phone}")
    public @ResponseBody
    AccessResponse sendSms(@PathVariable String phone, HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObj = sendSmsService.sendSms(phone);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/registerSendSms/{phone}")
    public @ResponseBody
    AccessResponse registerSendSms(@PathVariable String phone, HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObj = sendSmsService.registerSendSms(phone);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/loginSendSms/{phone}")
    public @ResponseBody
    AccessResponse loginSendSms(@PathVariable String phone, HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObj = sendSmsService.loginSendSms(phone);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }




    /**
     * @Author bianyx
     * @Description //TODO 图片上传
     * @Date 16:04 2019/10/29
     * @Param [base64Data, pageParamDTO, request, response]
     * @return com.hengpu.national.common.domain.AccessResponse
     **/
    @RequestMapping(value = "/image/upload/uploadBase64")
    public @ResponseBody
    AccessResponse uploadImage(@RequestBody JSONObject base64Data){
        //返回体
        JSONObject jsonObj=new JSONObject();

        String base64Data1 = base64Data.getString("base64Data");

        System.out.println();
        String realpath = "";
        try{
            String dataPrix = "";
            String data = "";
            if(base64Data1 == null || "".equals(base64Data1)){
//                throw new Exception("上传失败，上传图片数据为空");
                jsonObj.put("result", "01");
                jsonObj.put("desc", "上传失败，上传图片数据为空");
                return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();

            }else{
                data=base64Data1;

            }
            String suffix = ".jpg";

            String tempFileName = UUID.randomUUID().toString() + suffix;

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bs = decoder.decodeBuffer(data);


            try{


                realpath = imageUploadService.ImageUploadRest(bs,tempFileName);


            }catch(Exception ee){
                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }
            jsonObj.put("result", "00");
            jsonObj.put("desc", "操作成功");
            jsonObj.put("realpath", realpath);
            return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();

        }catch (Exception e) {
            jsonObj.put("result", "01");
            jsonObj.put("desc", e.getMessage());
            return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();

        }

    }

    @RequestMapping(value = "/test")
    public @ResponseBody
    AccessResponse test(){
        //返回体
        JSONObject jsonObj=new JSONObject();


        asyncRestService.createAddress("01000000006");








        jsonObj.put("result", "00");
        jsonObj.put("desc", "操作成功");
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    @RequestMapping(value = "/test2/{day}")
    public @ResponseBody
    AccessResponse test2(@PathVariable Integer day){
        //返回体
        JSONObject jsonObj=new JSONObject();


        log.info("========理财产品订单奖金释放========时间:"+System.currentTimeMillis()+"_________START");
        iTWheatFinancialBusiSV.dealTWheatFinancial(day);
        log.info("========理财产品订单奖金释放========时间:"+System.currentTimeMillis()+"_________END");

        jsonObj.put("result", "00");
        jsonObj.put("desc", "操作成功");
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }




}
