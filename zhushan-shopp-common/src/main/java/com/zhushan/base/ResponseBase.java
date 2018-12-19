package com.zhushan.base;

public class ResponseBase {
	
	private Integer code;
	
	private String msg;
	
	private Object obj;
	
	public ResponseBase() {
	}
	
	public ResponseBase(Integer code, String msg, Object obj) {
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	public ResponseBase(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
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

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "ResponseBase [code=" + code + ", msg=" + msg + ", obj=" + obj
				+ "]";
	}

}
