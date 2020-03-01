package com.spring.free.common.util;


/**
 * Created by Administrator on 2017/10/26 0026.
 */

import net.sf.json.JSONObject;

/**
 * Demo class
 *
 * @author bless
 * @date 2016/10/31
 */
public class HttpResultEntity {
    private Boolean success;
    private Integer code;
    private String msg;
    private JSONObject data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
