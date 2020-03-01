package com.spring.free.common.service;/**
 * Created by hengpu on 2019/3/4.
 */

import com.alibaba.fastjson.JSONObject;
import com.spring.free.common.domain.TelecomCard;
import com.spring.free.common.util.MD5Util;
import com.spring.free.common.util.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName AsyncRestService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/4 15:37
 * @Version 1.0
 **/
@Slf4j
@Service
public class AsyncRestService {

    @Value("${telecom.body.data_url}")
    private  String data_url;
    @Value("${telecom.body.key}")
    private String key;
    @Value("${telecom.body.addOrder}")
    private String addOrder;
    @Value("${telecom.body.queryOrder}")
    private String queryOrder;
    @Value("${telecom.body.account}")
    private String account;
    @Value("${telecom.body.productNo}")
    private String productNo;

    @Value("${dc.headers.accessKey}")
    private String accessKey;
    @Value("${dc.headers.secretKey}")
    private String secretKey;
    @Value("${url.center_url}")
    private  String center_url;
    @Value("${method.addToShop}")
    private  String addToShop;
    @Value("${method.orderToDC}")
    private  String orderToDC;
    @Value("${method.levelToDC}")
    private  String levelToDC;

    @Value("${param.merchantId}")
    private  String merchantId;
    @Value("${param.password}")
    private  String password;
    @Value("${wallet.url}")
    private String wallet_url;


    /**
     * @Author bianyx
     * @Description //TODO 接收开卡数据
     * @Date 16:21 2019/3/4
     * @Param [telecomCard]
     * @return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject postOrderToTelecom(TelecomCard telecomCard, String shopOrder) {
//        String url = "http://bjw.38.zonghejiaofei.net:5006"+"/ecommerceNew/addOrder";
//        String url = "http://61.162.187.40:8946"+addOrder;
        String url = data_url+addOrder;



        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        try {
            String str = "account"+account+"productNo"+productNo+"shopOrder"+shopOrder+"userType20";
            String sign= MD5Util.md5Hex(str+key).toUpperCase();

            String bodyValTemplate = "account="+account+"&shopOrder="+shopOrder+"&productNo="+productNo+"&userName="+URLEncoder.encode(telecomCard.getUserName(), "utf-8")
                    +"&cardNo="+telecomCard.getCardNo()+"&receiveUser="+URLEncoder.encode(telecomCard.getReceiveUser(), "utf-8")+"&receiveMobile="+telecomCard.getReceiveMobile()+"&receiveAddress="+URLEncoder.encode(telecomCard.getReceiveAddress(), "utf-8")
                    +"&sign="+sign+"&userType=20"
                    +"&frontStr="+ URLEncoder.encode(telecomCard.getFrontStr(), "utf-8")+"".trim()
                    +"&backStr="+URLEncoder.encode(telecomCard.getBackStr(), "utf-8")+"".trim() +"&handStr="+URLEncoder.encode(telecomCard.getHandStr(), "utf-8")+"".trim();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity entity = new HttpEntity(bodyValTemplate, headers);
            log.info("领取电信卡请求开始："+new Date());
            System.out.println("领取电信卡请求开始："+new Date());
            response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
            log.info("领取电信卡请求结束："+new Date());
            System.out.println("领取电信卡请求结束："+new Date());
            System.out.println(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }
    /**
     * @Author bianyx
     * @Description //TODO 查询订单信息
     * @Date 16:22 2019/3/4
     * @Param [account, shopOrder]
     * @return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject postQueryOrderToTelecom(String shopOrder) {
        String url = data_url+queryOrder;
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        try {
            JSONObject json = new JSONObject();
            String str = "account"+account+"shopOrder"+shopOrder;
            String sign= MD5Util.md5Hex(str+key).toUpperCase();
            json.put("account",account);
            json.put("shopOrder",shopOrder);
            json.put("sign",sign);
            url = url+"?account="+account+"&shopOrder="+shopOrder+"&sign="+sign;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>("", headers), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }
//
//    private HttpEntity<JSONObject> getEntity(Object obj) {
//
//        System.out.println(JSONObject.toJSON(obj));
//        JSONObject json = (JSONObject) JSONObject.toJSON(obj);
//        System.out.println(key);
//        String str = "account"+json.get("account")+"productNo"+json.get("productNo")+"shopOrder"+json.get("shopOrder")+"userType"+json.get("userType");
//        String sign= MD5Util.md5Hex(str+key).toUpperCase();
//        json.put("sign",sign);
//        System.out.println(json);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        return new HttpEntity<JSONObject>(json, headers);
//    }

    public static JSONObject getFailJson() {
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", false);
        responseJson.put("msg", "远程连接异常");
        responseJson.put("data", false);
        return responseJson;
    }

    public	JSONObject addMemberToDc(String memberId ,String phone ,String recFirm){

        //商城端字段值
        JSONObject jsonToShop = new JSONObject();
        jsonToShop.put("phone", phone);
        jsonToShop.put("memberId", memberId);
        jsonToShop.put("email", "");
        jsonToShop.put("remoteAddr","");
        jsonToShop.put("memberGroup", "010");
        jsonToShop.put("levelType", "");
        jsonToShop.put("idCard", "");
        jsonToShop.put("gender", "");
        jsonToShop.put("name", "");
        jsonToShop.put("addressExtDetail","");
        jsonToShop.put("addressExtName","");
        jsonToShop.put("addressExtPhone","");
        jsonToShop.put("recFirm",recFirm==null?"":recFirm);
        jsonToShop.put("idCardFront","");
        jsonToShop.put("idCardBack","");
        jsonToShop.put("isMaster","1");


        return this.addToShop(jsonToShop);
    }

    public  JSONObject addToShop(JSONObject param) {
        String url = center_url+addToShop;
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        String sysDes = "33";
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getEntity(param,sysDes), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }

    private HttpEntity<JSONObject> getEntity(Object obj,String sysDes) {

        String reqTime=String.valueOf(System.currentTimeMillis());
        //String sign= MD5Util.getMessageDigest((JSON.toJSONString(obj)+reqTime+secretKey).getBytes()).toUpperCase();
        System.out.println(JSONObject.toJSON(obj));
        System.out.println(secretKey);
        System.out.println(reqTime);
        String car = JSONObject.toJSON(obj)+reqTime+secretKey;
        String sign=MD5Util.md5Hex(JSONObject.toJSON(obj)+reqTime+secretKey).toUpperCase();

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessKey", accessKey);
        headers.set("Content-Type", "application/json");
        headers.set("reqTime",reqTime);
        headers.set("sign",sign);

        headers.set("sysDes", sysDes);


        return new HttpEntity<JSONObject>((JSONObject) JSONObject.toJSON(obj), headers);
    }

    public	JSONObject orderToDc(String memberId ,Double fee ,String planName,String level,String type,String orderId){

        //商城端字段值
        JSONObject jsonToShop = new JSONObject();
        jsonToShop.put("memberId", memberId);
        jsonToShop.put("fee", fee);
        jsonToShop.put("planName",planName==null|| StringUtils.isEmpty(planName)?"":planName);
        jsonToShop.put("level", level==null|| StringUtils.isEmpty(level)?"":level);
        jsonToShop.put("type", type);
        jsonToShop.put("from", "商城");
        jsonToShop.put("orderId", orderId);
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String str = sdf.format(new Date());
        jsonToShop.put("time", str);



        return this.orderToMember(jsonToShop);
    }

    public  JSONObject orderToMember(JSONObject param) {
        String url = center_url+orderToDC;
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        String sysDes = "33";
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getEntity(param,sysDes), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }

    public	JSONObject levelToDc(String memberId ,String level){

        //商城端字段值
        JSONObject jsonToShop = new JSONObject();
        jsonToShop.put("memberId", memberId);
        jsonToShop.put("level", level);
        jsonToShop.put("type", "1");



        return this.levelToMember(jsonToShop);
    }
    public  JSONObject levelToMember(JSONObject param) {
        String url = center_url+levelToDC;
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        String sysDes = "33";
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getEntity(param,sysDes), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }





    /**
     * @Author bianyx
     * @Description //TODO 登陆
     * @Date 11:17 2019/5/16
     * @Param [shopOrder]
     * @return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject login(String url) {
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();

        JSONObject param = new JSONObject();
        param.put("merchantId",merchantId);
        param.put("password",password);
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getLoginEntity(param), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }
    private HttpEntity<JSONObject> getLoginEntity(Object obj) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        return new HttpEntity<JSONObject>((JSONObject) JSONObject.toJSON(obj), headers);
    }
    /**
     * @Author bianyx
     * @Description //TODO 第三方支付接口
     * @Date 11:13 2019/5/16
     * @Param [param]
     * @return com.alibaba.fastjson.JSONObject
     **/
    public  JSONObject payApply(JSONObject param,String url ,String jwt) {
        HttpEntity<JSONObject> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getPayEntity(param,jwt), JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            return response.getBody();
        }
    }

    private HttpEntity<JSONObject> getPayEntity(Object obj,String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("jwt",jwt);

        return new HttpEntity<JSONObject>((JSONObject) JSONObject.toJSON(obj), headers);
    }

    public JSONObject createAddress(String memberId) {
        String url = wallet_url+memberId;
        HttpEntity<String> response = null;
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        try {
            response =restTemplate.getForEntity(url,String.class);

            log.info("python"+response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return getFailJson();
        }

        if (response == null) {
            return getFailJson();
        } else {
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("msg", "操作成功");
            responseJson.put("data", response.getBody());
            return responseJson;
        }
    }

}
