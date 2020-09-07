package com.spring.fee.service.impl;/**
 * Created by hengpu on 2019/2/28.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring.fee.model.TableMember;
import com.spring.free.common.util.CacheKeyPrefix;
import com.spring.free.common.util.HttpUtil;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SendShopMemberService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/28 9:56
 * @Version 1.0
 **/
@Slf4j
@Service
public class SendShopMemberService {

    @Value("${shop.domain}")
    private  String domain;

    /**
     * @Author bianyx
     * @Description //TODO 下发验证短信
     * @Date 17:30 2019/3/1
     * @Param [phone, request]
     * @return void
     **/
    public void register(TableMember member,TableMember referenceMember) {
        log.info("注册同步数据：{}", JSON.toJSONString(member));
        try {
            Map<String,String> map = new HashMap();
            map.put("memberCode", member.getMemberId());
            map.put("mobile", member.getPhone());
            map.put("level", String.valueOf(member.getLevel()));
            map.put("references", referenceMember.getPhone());
            map.put("voucher", String.valueOf(member.getAccountDjPoint()));
            String url = domain+"/restful/v1/front/special/synchronize/register";
            log.info("注册同步数据接口请求地址：{}", url);
            log.info("注册同步数据接口请求入参：{}", JSON.toJSONString(map));
            String responseText = HttpUtil.sendPost(url, map);
            JSONObject response = JSONObject.parseObject(responseText);
            log.info("注册同步数据接口返回结果：{}", JSON.toJSONString(response));
            String code = response.get("result")+"";
            String errorCode = ((JSONObject)response.get("data")).getString("result");

            if(!"00".equals(errorCode)){
                String errorMsg = ((JSONObject)response.get("data")).getString("des");
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据错误【"+errorMsg+"】", "", null);
            }
        } catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据错误【"+e.getMessage()+"】", "", null);
        }
    }

    /**
     * @Author bianyx
     * @Description //TODO 下发验证短信
     * @Date 17:30 2019/3/1
     * @Param [phone, request]
     * @return void
     **/
    public void changeLevel(TableMember member) {
        log.info("会员变更级别同步数据：{}", JSON.toJSONString(member));
        try {
            Map<String,String> map = new HashMap();
            map.put("memberCode", member.getMemberId());
            map.put("level", String.valueOf(member.getLevel()));
            String url = domain+"restful/v1/front/special/synchronize/changeLevel";
            log.info("会员变更级别同步数据接口请求地址：{}", url);
            log.info("会员变更级别同步数据接口请求入参：{}", JSON.toJSONString(map));
            String responseText = HttpUtil.sendPost(url, map);
            JSONObject response = JSONObject.parseObject(responseText);
            log.info("会员变更级别同步数据接口返回结果：{}", JSON.toJSONString(response));
            String code = response.get("result")+"";
            String errorCode = ((JSONObject)response.get("data")).getString("result");

            if(!"00".equals(errorCode)){
                String errorMsg = ((JSONObject)response.get("data")).getString("des");
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据错误【"+errorMsg+"】", "", null);
            }
        } catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "同步数据错误【"+e.getMessage()+"】", "", null);
        }
    }

}
