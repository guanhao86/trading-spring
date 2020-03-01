package com.spring.free.common.util;/**
 * Created by hengpu on 2019/3/4.
 */

import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RestTemplateUtil
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/4 15:45
 * @Version 1.0
 **/
public class RestTemplateUtil {
    private RestTemplateUtil() {}
    private static RestTemplate restTemplate=null;
    private static AsyncRestTemplate asyncTestTemplate=null;
    //静态工厂方法
    public static RestTemplate getInstance() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }


    public static AsyncRestTemplate getAsyncInstance() {
        if (asyncTestTemplate == null) {
            asyncTestTemplate = new AsyncRestTemplate();
        }
        return asyncTestTemplate;
    }
}
