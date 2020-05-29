package com.spring.free.controller.api;

/**
 *
 */
public final class ResponseConstants {

    /**
     * 应答码
     */
    public static final class ResponseCode{
        private ResponseCode(){}
        //成功
        public static final int SUCCESS=0000;
        //其他原因失败
        public static final int FAIL=9999;
        //刷新TOKEN
        public static final int REFRESH_TOKEN=1000;
        //TOKEN无效
        public static final int TOKEN_FAIL=1001;


    }

}
