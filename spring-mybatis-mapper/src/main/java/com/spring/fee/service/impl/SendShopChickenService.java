package com.spring.fee.service.impl;/**
 * Created by hengpu on 2019/2/28.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring.fee.model.TableMemberGoods;
import com.spring.free.common.util.HttpUtil;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/28 9:56
 * @Version 1.0
 **/
@Slf4j
@Service
public class SendShopChickenService {

    @Value("${shop.domain}")
    private  String domain;

    /**
     * @Author bianyx
     * @Description //TODO 下发验证短信
     * @Date 17:30 2019/3/1
     * @Param [phone, request]
     * @return void
     **/
    public void addChicken(TableMemberGoods tableMemberGoods) {
        log.info("金鸡同步数据：{}", JSON.toJSONString(tableMemberGoods));
        try {
            Map<String,String> map = new HashMap();
            map.put("memberCode", tableMemberGoods.getMemberId());
            map.put("count", String.valueOf(tableMemberGoods.getAmount()));
            map.put("bussinesId", "1");
            String url = domain+"restful/v1/front/egg/addChicken";
            log.info("金鸡同步数据接口请求地址：{}", url);
            log.info("金鸡同步数据接口请求入参：{}", JSON.toJSONString(map));
            String responseText = HttpUtil.sendPost(url, map);
            JSONObject response = JSONObject.parseObject(responseText);
            log.info("金鸡同步数据接口返回结果：{}", JSON.toJSONString(response));
            String code = response.get("result")+"";
            //String errorCode = ((JSONObject)response.get("data")).getString("result");

            if(!"100".equals(code)){
                String errorMsg = response.get("message")+"";
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据错误【"+errorMsg+"】", "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据异常【"+e.getMessage()+"】", "", null);
        }
    }
}
