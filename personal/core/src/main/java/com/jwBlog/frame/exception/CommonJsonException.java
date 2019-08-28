package com.jwBlog.frame.exception;

import com.alibaba.fastjson.JSONObject;
import com.jwBlog.frame.CommonUtil;
import com.jwBlog.frame.constants.ErrorEnum;

public class CommonJsonException extends RuntimeException {
	private JSONObject resultJson;

	/**
	 * 调用时可以在任何代码处直接throws这个Exception,
	 * 都会统一被拦截,并封装好json返回给前台
	 *
	 * @param errorEnum 以错误的ErrorEnum做参数
	 */
	public CommonJsonException(ErrorEnum errorEnum) {
		this.resultJson = CommonUtil.errorJson(errorEnum);
	}

	public CommonJsonException(JSONObject resultJson) {
		this.resultJson = resultJson;
	}

	public JSONObject getResultJson() {
		return resultJson;
	}
}