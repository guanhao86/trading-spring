package com.spring.free.common.service;/**
 * Created by hengpu on 2019/2/28.
 */

import com.spring.free.common.util.CacheKeyPrefix;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SendSmsService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/28 9:56
 * @Version 1.0
 **/
@Slf4j
@Service
public class SendSmsService {
    private static String signName="【道禾资本】";
//    private static String userId="jisuzl";
//    private static String password="6f97f19a714aebad5294b2409c2b4319";
//    private static String smsSingleRequestServerUrl="http://smslianyus.com:8022/sms/SendMsg";

//    @Value("${sms.send.signName}")
//    private  String signName;
    @Value("${sms.send.userId}")
    private  String userId;
    @Value("${sms.send.password}")
    private  String password;
    @Value("${sms.send.smsSingleRequestServerUrl}")
    private  String smsSingleRequestServerUrl;

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${trading.sms.validate.code.expire.time:3}")
    private Integer validateCodeExpireTime;

    @Value("${sms.on-off}")
    private Integer isSms;

    /**
     * @Author bianyx
     * @Description //TODO 登陆下发短信
     * @Date 17:30 2019/3/1
     * @Param [phone, request]
     * @return void
     **/
    public JSONObject sendMsgValidateCode(String phone, HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        try {
            // 短信内容
            String msg = "验证码为：";
            int smscode = (int)(Math.random()*(9999-1000+1))+1000;
//            HttpSession sessions = request.getSession();
//            sessions.setAttribute("LOGIN_SMS_CODE", String.valueOf(smscode)+"_"+phone);
//            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
//            String str = sdf.format(new Date());
//            sessions.setAttribute("LOGIN_SMS_TIME", str);
            msg = msg + String.valueOf(smscode);
            

            msg = signName + msg;
            //手机号码
            Map<String,String> map = new HashMap();
            map.put("userId", userId);
            map.put("password", password);
            map.put("mobile", phone);
            map.put("content", msg);
            Map response = sendSmsByPostLianyu(smsSingleRequestServerUrl, map);
            String code = response.get("rspCode")+"";
            String errorMsg = response.get("rspDesc")+"";
            String msgId = response.get("msgId")+"";
            if("0".equals(code)){
                String uuid = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid, String.valueOf(smscode), validateCodeExpireTime, TimeUnit.MINUTES);
                System.out.println(uuid);
                System.out.println(smscode);

                ret.put("result","00");
                ret.put("uuid",uuid);
                ret.put("code",smscode);
                ret.put("des","成功");

                return ret;
            }else{
                log.info(phone+"下发短信失败",errorMsg);
                ret.put("result","01");
                ret.put("des",errorMsg);
                return ret;
            }

        } catch (Exception e) {

            log.info(phone+"下发短信报错",e.getMessage());
            e.printStackTrace();
            ret.put("result","01");
            ret.put("des",e.getMessage());
            return ret;
        }
    }

    public Boolean validate(String uuid, String msgCode) {

        if(isSms.intValue()==0){
            return true;
        }

        log.info("短信验证码校验");
        Boolean flag = false;
        try {
            if (redisTemplate.hasKey(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid)) {
                log.info("找到 验证码key：{}", uuid);
                Object redisCode = redisTemplate.opsForValue().get(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid);
                if (redisCode != null) {
                    flag = redisCode.toString().equals(msgCode);
                    log.info("redis验证码key：{}， redis验证码：{}, 输入验证码： {}, flag:{}", uuid, redisCode, msgCode, flag);
                }
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
    }

    /**
     * @Author bianyx
     * @Description //TODO 下发验证短信
     * @Date 17:30 2019/3/1
     * @Param [phone, request]
     * @return void
     **/
    public JSONObject sendSms(String phone) {
        JSONObject ret = new JSONObject();

        if(isSms.intValue()==0){
            ret.put("result","00");
            ret.put("uuid",UUID.randomUUID().toString());
            ret.put("code","1234");
            ret.put("desc","成功");

            return ret;
        }
        try {
            // 短信内容
            String msg = "验证码为：";
            int smscode = (int)(Math.random()*(9999-1000+1))+1000;
            msg = msg + String.valueOf(smscode);


            msg = signName + msg;
            //手机号码
            Map<String,String> map = new HashMap();
            map.put("userId", userId);
            map.put("password", password);
            map.put("mobile", phone);
            map.put("content", msg);
            Map response = sendSmsByPostLianyu(smsSingleRequestServerUrl, map);
            String code = response.get("rspCode")+"";
            String errorMsg = response.get("rspDesc")+"";
            String msgId = response.get("msgId")+"";
            if("0".equals(code)){

                String uuid = UUID.randomUUID().toString();

                redisTemplate.opsForValue().set(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid, String.valueOf(smscode), validateCodeExpireTime, TimeUnit.MINUTES);
                System.out.println(uuid);
                System.out.println(smscode);
                ret.put("result","00");
                ret.put("uuid",uuid);
                ret.put("code",smscode);
                ret.put("desc","成功");

                return ret;
            }else{
                log.info(phone+"下发短信失败",errorMsg);
                ret.put("result","01");
                ret.put("desc",errorMsg);
                return ret;
            }

        } catch (Exception e) {

            log.info(phone+"下发短信报错",e.getMessage());
            e.printStackTrace();
            ret.put("result","01");
            ret.put("desc",e.getMessage());
            return ret;
        }
    }

    public JSONObject registerSendSms(String phone) {
        JSONObject ret = new JSONObject();

        if(isSms.intValue()==0){
            ret.put("result","00");
            ret.put("uuid",UUID.randomUUID().toString());
            ret.put("code","1234");
            ret.put("desc","成功");

            return ret;
        }
        try {
            // 短信内容
            String msg = "您正在注册道禾资本APP，验证码为：";
            int smscode = (int)(Math.random()*(9999-1000+1))+1000;
            msg = msg + String.valueOf(smscode) +" 如果不是本人注册，请忽略短信，勿要泄露";


            msg = signName + msg;
            //手机号码
            Map<String,String> map = new HashMap();
            map.put("userId", userId);
            map.put("password", password);
            map.put("mobile", phone);
            map.put("content", msg);
            Map response = sendSmsByPostLianyu(smsSingleRequestServerUrl, map);
            String code = response.get("rspCode")+"";
            String errorMsg = response.get("rspDesc")+"";
            String msgId = response.get("msgId")+"";
            if("0".equals(code)){

                String uuid = UUID.randomUUID().toString();

                redisTemplate.opsForValue().set(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid, String.valueOf(smscode), validateCodeExpireTime, TimeUnit.MINUTES);
                System.out.println(uuid);
                System.out.println(smscode);
                ret.put("result","00");
                ret.put("uuid",uuid);
                ret.put("code",smscode);
                ret.put("desc","成功");

                return ret;
            }else{
                log.info(phone+"下发短信失败",errorMsg);
                ret.put("result","01");
                ret.put("desc",errorMsg);
                return ret;
            }

        } catch (Exception e) {

            log.info(phone+"下发短信报错",e.getMessage());
            e.printStackTrace();
            ret.put("result","01");
            ret.put("desc",e.getMessage());
            return ret;
        }
    }

    public JSONObject loginSendSms(String phone) {
        JSONObject ret = new JSONObject();

        if(isSms.intValue()==0){
            ret.put("result","00");
            ret.put("uuid",UUID.randomUUID().toString());
            ret.put("code","1234");
            ret.put("desc","成功");

            return ret;
        }
        try {
            // 短信内容
            String msg = "您正在登录道禾资本APP，验证码为：";
            int smscode = (int)(Math.random()*(9999-1000+1))+1000;
            msg = msg + String.valueOf(smscode) +" 如果不是本人登录，请忽略短信，勿要泄露";


            msg = signName + msg;
            //手机号码
            Map<String,String> map = new HashMap();
            map.put("userId", userId);
            map.put("password", password);
            map.put("mobile", phone);
            map.put("content", msg);
            Map response = sendSmsByPostLianyu(smsSingleRequestServerUrl, map);
            String code = response.get("rspCode")+"";
            String errorMsg = response.get("rspDesc")+"";
            String msgId = response.get("msgId")+"";
            if("0".equals(code)){

                String uuid = UUID.randomUUID().toString();

                redisTemplate.opsForValue().set(CacheKeyPrefix.TRADING_SMS_VALIDATION_CODE_PREFIX + uuid, String.valueOf(smscode), validateCodeExpireTime, TimeUnit.MINUTES);
                System.out.println(uuid);
                System.out.println(smscode);
                ret.put("result","00");
                ret.put("uuid",uuid);
                ret.put("code",smscode);
                ret.put("desc","成功");

                return ret;
            }else{
                log.info(phone+"下发短信失败",errorMsg);
                ret.put("result","01");
                ret.put("desc",errorMsg);
                return ret;
            }

        } catch (Exception e) {

            log.info(phone+"下发短信报错",e.getMessage());
            e.printStackTrace();
            ret.put("result","01");
            ret.put("desc",e.getMessage());
            return ret;
        }
    }

    public JSONObject sendSmd(String phone, String msg) {
        JSONObject ret = new JSONObject();

        if(isSms.intValue()==0){
            ret.put("result","00");
            ret.put("code",msg);
            ret.put("desc","成功");

            return ret;
        }
        try {

            msg = signName + msg;
            //手机号码
            Map<String,String> map = new HashMap();
            map.put("userId", userId);
            map.put("password", password);
            map.put("mobile", phone);
            map.put("content", msg);
            Map response = sendSmsByPostLianyu(smsSingleRequestServerUrl, map);
            String code = response.get("rspCode")+"";
            String errorMsg = response.get("rspDesc")+"";
            String msgId = response.get("msgId")+"";
            if("0".equals(code)){

                System.out.println(msg);
                ret.put("result","00");
                ret.put("code",msg);
                ret.put("des","成功");

                return ret;
            }else{
                log.info(phone+"下发短信失败",errorMsg);
                ret.put("result","01");
                ret.put("desc",errorMsg);
                return ret;
            }

        } catch (Exception e) {

            log.info(phone+"下发短信报错",e.getMessage());
            e.printStackTrace();
            ret.put("result","01");
            ret.put("des",e.getMessage());
            return ret;
        }
    }




    public static Map sendSmsByPostLianyu(String path, Map<String, String> params){
        Map map = new HashMap<>();

        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化post方法
        HttpPost httpPost = new HttpPost(path);
        //处理参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        //结果
        CloseableHttpResponse response = null;
        String content="";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity  = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(content);
                if(content!=null&& StringUtils.isNotEmpty(content)){
                    String[] str = content.split("&");
                    if(str!=null&&str.length>0){
                        String[] code = str[0].split("=");
                        String[] rspDesc = str[1].split("=");

                        if(code!=null&&code.length>0){
                            if(code[0].equals("rspCode")&&code[1].equals("0")){
                                String[] msgId = str[2].split("=");
                                map.put("rspCode",code[1]);
                                map.put("rspDesc",rspDesc[1]);
                                map.put("msgId",msgId[1]);
                                return map;
                            }else{
                                map.put("rspCode",code[1]);
                                map.put("rspDesc",rspDesc[1]);
                                return map;
                            }

                        }
                    }
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void test(){
//        System.out.println("清空库中所有数据："+redisTemplate.flushDB());
//        // 判断key否存在
//        System.out.println("判断key999键是否存在："+redisTemplate.exists("key999"));
//        System.out.println("新增key001,value001键值对："+redisTemplate.set.set("key001", "value001"));
//        System.out.println("判断key001是否存在："+redisTemplate.exists("key001"));
    }

}
