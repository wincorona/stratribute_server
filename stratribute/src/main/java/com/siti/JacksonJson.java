package com.siti;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.siti.tool.DateOrTimeTrans;
import com.siti.tool.ReturnResult;


@SuppressWarnings("rawtypes")
public class JacksonJson {
	/**
	 * @author 支煜
	 * @param json 封装json串里包含bean的属性�?
	 * */
	public static Object getBean(String json, Class<?> beanclass)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DateFormat(mapper);
		Object beanJSON = null;
		try {
			beanJSON = mapper.readValue(json, beanclass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return beanJSON;
	}
	
	/**
	 * javabean转换为json串，并包含了日期的格式化处理
	 * */
	private static String beanToJson(Object object)
	{
		ObjectMapper mapper = new ObjectMapper();
		DateFormat(mapper);
		Writer strWriter = new StringWriter();
		try {
			mapper.writeValue(strWriter, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strWriter.toString();
	}
	
	/**
	 * 时间的格式化处理
	 * */
	private static void DateFormat(ObjectMapper mapper)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SerializationConfig serConfig = mapper.getSerializationConfig();
		serConfig.setDateFormat(dateFormat);
		DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
		deserializationConfig.setDateFormat(dateFormat);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	/**
	 * 封装列表信息
	 * 返回数据都用此方法，不管是返回List，还是单个Bean�?
	 * 可以通过TotalNum来判断是否查找到数据�?
	 * */
	public static String packageJson(List list)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("ErrorDescription", null);
		map.put("TotalNum",list.size());
		map.put("List", list);
		return beanToJson(map);
	}
	
	public static String SignalClassJson(Object oj){
		return beanToJson(oj);
	}
	
	public static String packageJsonClass(ReturnResult rr){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", rr.getCode());
		map.put("success",rr.isSuccess());
		map.put("data", rr.getData());
		map.put("message", rr.getMessage());
		return beanToJson(map);
	}
	
	/**
	 * 封装字符串信�?
	 * */
	public static String packageObjectJson(String status, Object data)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("result", data);
		return beanToJson(map);
	}
	
	/**
	 * 封装错误信息
	 * */
	public static String packageJson(String status, String ErrorDescription)
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", status);
		map.put("ErrorDescription", ErrorDescription);
		return beanToJson(map);
	}
	
}
