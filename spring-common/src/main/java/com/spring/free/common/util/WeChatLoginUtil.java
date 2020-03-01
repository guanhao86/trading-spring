package com.spring.free.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/26 0026.
 */
@Slf4j
public class WeChatLoginUtil {
//    /*
//     * 初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
//     * 添加的StringHttpMessageConverter非UTF-8 所以先要移除原有的StringHttpMessageConverter，
//     * 再添加一个字符集为UTF-8的StringHttpMessageConvert
//     */
//    private static void reInitMessageConverter(RestTemplate restTemplate) {
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//        HttpMessageConverter<?> converterTarget = null;
//        for (HttpMessageConverter<?> item : converterList) {
//            if (item.getClass() == StringHttpMessageConverter.class) {
//                converterTarget = item;
//                break;
//            }
//        }
//
//        if (converterTarget != null) {
//            converterList.remove(converterTarget);
//        }
//        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//        converterList.add(converter);
//    }

    //根据code获取token
    private static HttpResultEntity getAccessToken(String appid, String secret, String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type={grant_type}";

        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("appid", appid);
        uriVariables.put("secret", secret);
        uriVariables.put("code", code);
        uriVariables.put("grant_type", "authorization_code");

        String resultStr = restTemplate.getForObject(url, String.class, uriVariables);
        JSONObject jsonObject = JSONObject.fromObject(resultStr);
        log.info("getAccessToken:resultStr------"+resultStr);
        return handleResult(jsonObject);
    }


    //刷新access_token
    private static HttpResultEntity refreshAccessToken(String appid, String refresh_token) {

        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={appid}&grant_type={grant_type}&refresh_token={refresh_token}";

        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("appid", appid);
        uriVariables.put("grant_type", "refresh_token");
        uriVariables.put("refresh_token", refresh_token);

        String resultStr = restTemplate.getForObject(url, String.class, uriVariables);
        JSONObject jsonObject = JSONObject.fromObject(resultStr);
        log.info("refreshAccessToken:resultStr------"+resultStr);
        return handleResult(jsonObject);
    }


    //获取用户个人信息
    private static HttpResultEntity getUserInfo(String access_token, String openid){

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token={access_token}&openid={openid}";
        RestTemplate restTemplate = RestTemplateUtil.getInstance();

        Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("access_token", access_token);
        uriVariables.put("openid", openid);

        restTemplate.getMessageConverters();

        String resultStr = restTemplate.getForObject(url, String.class, uriVariables);
        try {
			resultStr=new String(resultStr.getBytes("ISO-8859-1"),"utf-8"); 
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        JSONObject jsonObject = JSONObject.fromObject(resultStr);
        log.info("getUserInfo:resultStr------"+resultStr);
        return handleResult(jsonObject);


//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
//        headers.setContentType(type);
//        HttpEntity<Map> t = new HttpEntity<Map>(null, headers);
//
//        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, t, String.class,uriVariables);
//
//
//        return handleResult(JSON.parseObject((String) responseEntity.getBody()));


    }


    //处理返回结果
    private static HttpResultEntity handleResult(JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        HttpResultEntity re = new HttpResultEntity();
        //返回错误
        if (jsonObject.containsKey("errcode")) {
            re.setSuccess(false);
            re.setData(null);
            re.setCode(jsonObject.getInt("errcode"));
            re.setMsg(jsonObject.getString("errmsg"));
            return re;
        } else {
            re.setSuccess(true);
            re.setData(jsonObject);
            re.setCode(200);
            re.setMsg("OK");
            return re;
        }
    }


    //微信app登录
    public static HttpResultEntity getWechatUserInfo(String appid, String secret, String code) throws UnsupportedEncodingException {
        HttpResultEntity hre1 = getAccessToken(appid, secret, code);
        if (!hre1.getSuccess()) {
            return hre1;
        }

        JSONObject json1 = hre1.getData();
        String refresh_token = json1.getString("refresh_token");

        HttpResultEntity hre2 = refreshAccessToken(appid, refresh_token);
        if (!hre2.getSuccess()) {
            return hre2;
        }
        JSONObject json2 = hre2.getData();
        String access_token = json2.getString("access_token");
        String openid = json2.getString("openid");

        HttpResultEntity hre3;
        hre3 = getUserInfo(access_token, openid);
        if (!hre3.getSuccess()) {
            return hre3;
        }

        return hre3;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String appid = "wx7b988ec35df700c8";
        String secret = "6a6f6739a0b3e151cdf46c3bbb2aa3f9";
        String code = "001ksrcq1Zic6n03ftaq1nRncq1ksrcT";

        HttpResultEntity hre = getWechatUserInfo(appid, secret, code);
        System.out.println(JSONObject.fromObject(hre)        );

    }


}
