package com.mall.jelly.utils;

import java.util.HashMap;
import java.util.Map;

public class Global {

	//线程本地变量
	private static ThreadLocal<Map<String, Object>> param = new ThreadLocal<>();

	public static Object get(String key) {
		if (param.get() == null) {
			return null;
		}
		return param.get().get(key);
	}

	public static void set(String key, Object value) {
		Map<String, Object> paramMap = null;
		if ((paramMap = Global.param.get()) == null) {
			paramMap = new HashMap<>();
		}
		paramMap.put(key, value);
		param.set(paramMap);
	}

	public static void remove(){
		param.remove();
	}

}
