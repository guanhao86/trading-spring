package com.spring.free.util.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户异常类
 * @author Memory
 * @version 1.0
 */
public class UserException extends AuthenticationException {


    private static final long serialVersionUID = -5518928175658000323L;

    public UserException() {

        super();

    }

    public UserException(String message, Throwable cause) {

        super(message, cause);

    }

    public UserException(String message) {

        super(message);

    }

    public UserException(Throwable cause) {

        super(cause);

    }


}
