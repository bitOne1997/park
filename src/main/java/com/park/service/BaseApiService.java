package com.park.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.ResponseBase;

@Component
public class BaseApiService {
	
	public ResponseBase setResultError(String code,String msg) {
		return setResult(code, msg, null);
	}
	// 返回错误，可以传msg
	public ResponseBase setResultError(String msg) {
		return setResult("1", msg, null);
	}

	// 返回成功，可以传data值
	public ResponseBase setResultSuccess(Object data) {
		return setResult("0", "success", data);
	}

	// 返回成功，沒有data值
	public ResponseBase setResultSuccess() {
		return setResult("0", "success", null);
	}

	// 返回成功，沒有data值
	public ResponseBase setResultSuccess(String msg) {
		return setResult("0", msg, null);
	}

	// 通用封装
	public ResponseBase setResult(String code, String msg, Object data) {
		return new ResponseBase(code, msg, data);
	}

}
