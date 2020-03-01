package com.spring.free.util.exception;

/**
 * @author Memory
 * @date 2018/1/29
 * @Inc 恒谱科技
 */
public enum ExceptionCodeEnum {

    /**
     * 业务异常CODE
     */

    RESTMEM_ERROR_CODE("01", "业务处理异常"),
    SERVICE_ERROR_CODE("401", "业务处理异常"),
    AUTH_ERROR_CODE("403", "您没有权限，请联系管理员"),
    SYSTEM_ERROR_CODE("500", "系统发生异常，请联系管理员");

    private String code;
    private String msg;

    ExceptionCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
