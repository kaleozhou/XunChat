package com.cn.ouan.xunchat.util;

public class Error {
	public static final int ERROR_NUM = 0x666;
	private  String error;

	public  String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return  getError();
	}
	
	
}
