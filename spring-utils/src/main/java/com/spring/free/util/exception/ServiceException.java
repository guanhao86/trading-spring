package com.spring.free.util.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ServiceException(String code, String message, String url, Map map) {
		this.code = code;
		this.message = message;
		this.url = url;
		this.map = map;
	}

	private String code;
	private String message;
	private String url;
	private Map map;


}
