package com.jwBlog.base.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回值
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", "0");
	}

	public Result(String code,String msg) {
		put("code", code);
		put("msg", msg);
	}

	public static Result error() {
		return new Result(RESULT.CODE_NO.getValue(), RESULT.MSG_NO.getValue());
	}

	public static Result error(String msg) {
		return error(RESULT.CODE_NO.getValue(), msg);
	}

	public static Result error(String code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok() {
		return new Result(RESULT.CODE_YES.getValue(), RESULT.MSG_YES.getValue());
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	/**
	 * 返回状态值
	 */
	public enum RESULT{
		/**
		 * 成功
		 */
		CODE_YES("0"),
		/**
		 * 失败
		 */
		CODE_NO("-1"),
		/**
		 * 失败msg
		 */
		MSG_YES("操作成功"),
		/**
		 * 失败msg
		 */
		MSG_NO("操作失败");
		private String value;

		private RESULT(String value){
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}
}
