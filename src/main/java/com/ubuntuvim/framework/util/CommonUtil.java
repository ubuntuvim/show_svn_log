package com.ubuntuvim.framework.util;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

public class CommonUtil {

	/**
	 * 转换编码
	 * @param str
	 * @return
	 */
	public static String convertEncoding(String str) {
		if (StringUtils.isBlank(str))
			return "";
		
		try {
			str = new String(str.getBytes("ISO8859-1"), PropertiesUtil.GetValueByKey("PROJ_ENCODING"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	
	/**
	 * 设置返回到页面的信息
	 * @param out
	 * @param retMap
	 * @param jsonArray
	 * @param list
	 */
	public static void initRetInfo(PrintWriter out, boolean flag, String msg, List<Map<String, Object>> data) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		retMap.put("data", data);
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		
		out.print(gson.toJson(retMap));
	}
	
	/**
	 * 设置返回到页面的信息
	 * @param out
	 * @param retMap
	 * @param jsonArray
	 * @param list
	 */
	public static void initRetInfo(PrintWriter out, boolean flag, String msg, List<Object> data, int spare) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		retMap.put("data", data);
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		out.print(gson.toJson(retMap));
	}
}
