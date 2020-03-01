package com.spring.free.common.util;/**
 * Created by hengpu on 2019/5/21.
 */

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName JsonUtil
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/5/21 15:07
 * @Version 1.0
 **/
public class JsonUtil {
    public static Object objToJSON(Object obj){
        return JSONObject.toJSON(obj);
    }
}
