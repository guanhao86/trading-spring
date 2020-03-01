package com.spring.free.util.restful;

import java.io.Serializable;

/**
 * @author ningjun
 * @date 2018/9/11 13:35
 * @param <T>
 */
public class ResponseResult<T> implements Serializable{
	private Integer state;
	private String message;
	private T data;

	public ResponseResult(Integer state, String message, T data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public Integer getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResponseResult<?> that = (ResponseResult<?>) o;

		if (state != null ? !state.equals(that.state) : that.state != null) return false;
		if (message != null ? !message.equals(that.message) : that.message != null) return false;
		return data != null ? data.equals(that.data) : that.data == null;
	}

	@Override
	public int hashCode() {
		int result = state != null ? state.hashCode() : 0;
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

	public ResponseResult() {
		super();
	}
}
