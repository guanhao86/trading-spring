package com.spring.free.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

/**
 * 
 * Created on 2016年3月23日
 * <p>Title:       类说明</p>
 * <p>Description: [JSON工具类]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     北京盖勒克丝环保科技有限公司</p>
 * <p>Department:  沈阳软件开发中心</p>
 * @author: zhoun
 * @email:  zhoun@gailekesi.com
 * @version 1.0
 */
public class JsonUtils {

	private static ObjectMapper objectMapper = null;
	private static JsonUtils jsonUtils =  null;
	
	static {
		objectMapper = new ObjectMapper();

		// 允许/n['"<等特殊字符12['34"<
		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 识别单引号
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	}

	/**
	 * 对象转JSON字符串
	 * 
	 * @param obj
	 * 		Bean/List
	 * @return
	 * @throws Exception
	 */
	public static String objToJson(Object obj) throws Exception {
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * JSON字符串转对象
	 * 
	 * @param json
	 * 		JSON格式字符串
	 * 
	 * @param clazz
	 * 		转换的Class
	 * 
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToBean(String json, Class<T> clazz) throws Exception {
		return objectMapper.readValue(json, clazz);
	}
	
	
	/**
	 * 
	 * @Description 
	 * 		JSON字符串转LIST
	 * 
	 * @param json
	 * 		JSON格式字符串
	 * 
	 * @param typeRef
	 * 		样例：new TypeReference<List<User>>(){}
	 * 
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToList(String json, TypeReference<T> typeRef) throws Exception {
		return objectMapper.readValue(json, typeRef);
	}
	
	/**
	 * 
	 * @Description 
	 * 		JSON字符串转MAP
	 * 
	 * @param json
	 * 		JSON格式字符串
	 * 
	 * @param typeRef
	 * 		样例：new TypeReference<Map<String, User>>(){}
	 * 
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToMap(String json, TypeReference<T> typeRef) throws Exception {
		return objectMapper.readValue(json, typeRef);
	}

	/**
	 * 读取JSON文件转换为对象
	 * 
	 * @param path
	 * @param clazz
	 * @return
	 */
	public static <T> T readJsonFile(String path, Class<T> clazz) throws Exception {
		return objectMapper.readValue(new File(path), clazz);
	}
	
	/**
	 * 读取JSON文件转换为Map
	 * 
	 * @param path
	 * @return
	 */
	public static <T> T readJsonFile(String path, TypeReference<T> typeRef) throws Exception {
		return objectMapper.readValue(new File(path), typeRef);
	}
	
	/**
	 * 将对象以JSON格式写入文件
	 * 
	 * @param path
	 * @param obj
	 */
	public static void writeJsonFile(String path, Object obj) throws Exception {
		objectMapper.writeValue(new File(path), obj);
	}

	/**
	 * 获取ObjectMapper实例
	 *
	 * @return
	 */
	public static synchronized JsonUtils getInstance(boolean igNoreNull) {
		if (jsonUtils == null) {
			jsonUtils = new JsonUtils();
		}

		// 空值不序列化
		if (igNoreNull) {
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}

		// 允许/n['"<等特殊字符12['34"<
		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		// 识别单引号
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		return jsonUtils;
	}

}
