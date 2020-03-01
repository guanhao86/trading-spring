package com.spring.free.util.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户禁用异常类
 * @author Memory
 * @version 1.0
 */
public class EnabledException extends AuthenticationException {
    private static final long serialVersionUID = -7771661762052161285L;

    public EnabledException() {

        super();

    }

    public EnabledException(String message, Throwable cause) {

        super(message, cause);

    }

    public EnabledException(String message) {

        super(message);

    }

    public EnabledException(Throwable cause) {

        super(cause);

    }


}
