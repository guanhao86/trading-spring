package com.spring.free.util.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常类
 * @author Memory
 * @version 1.0
 */
public class CaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 6959391706529790181L;

    public CaptchaException() {

        super();

    }

    public CaptchaException(String message, Throwable cause) {

        super(message, cause);

    }

    public CaptchaException(String message) {

        super(message);

    }

    public CaptchaException(Throwable cause) {

        super(cause);

    }


}
