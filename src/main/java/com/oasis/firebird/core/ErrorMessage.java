package com.oasis.firebird.core;

import java.util.Map;

@Deprecated
public class ErrorMessage {

	private String tag;
	private String message;
	private Integer code;
	private Map<String, Object> extras;
	private Exception exception;

	public ErrorMessage(String tag, String message) {
		super();
		this.tag = tag;
		this.message = message;
	}

	public ErrorMessage(String tag, String message, Integer code) {
		super();
		this.tag = tag;
		this.message = message;
		this.code = code;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Map<String, Object> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "ErrorMessage [tag=" + tag + ", message=" + message + ", code=" + code + ", extras=" + extras + "]";
	}

}
