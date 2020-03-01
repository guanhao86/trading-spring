package com.spring.free.util.exception;

/**
 * 异常编码表
 */
public final class ExceptionConstants {

    //01开头
    public static final class Param{
        private Param(){}
        //请求参数为空
        public final static String NULL = "0101";
        //请求参数属性为空
        public final static String FEILD_NULL = "0102";
        //请求参数查询不到数据
        public final static String RESULT_NULL = "0103";
    }

    //02开头
    public static final class Result{
        private Result(){}
        //结果为空
        public final static String NULL = "0201";
        //结果不符合条件
        public final static String FEILD_NOT_MACTH = "0202";

    }

    //03开头
    public static final class Business{
        private Business(){}
        //校验不通过
        public final static String CHECK_ERR = "0301";

    }
}
