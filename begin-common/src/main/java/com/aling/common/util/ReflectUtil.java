package com.aling.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

/**
 * 反射工具类
 * 
 * @author zwsun
 * @version 1.0.0
 */

public class ReflectUtil {
	// 指定哪些字段不复制
	private static HashSet<String> ignoreSignList = new HashSet<String>();

	static {
		ignoreSignList.add("class");
		ignoreSignList.add("sign");
		ignoreSignList.add("serialVersionUID");
		ignoreSignList.add("pageParams");
		ignoreSignList.add("isSub2Page");
	}

	@SuppressWarnings("unchecked")
	public static <T> TreeMap<String, String> getDomainFieldMap(T domain) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		TreeMap<String, String> result = new TreeMap<String, String>();

		// 如果是JSONObject类型的话则完成对JSONObject解析，并返回
		if (domain instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) domain;
			Set<Entry<String, Object>> set = jsonObject.entrySet();
			Iterator<Entry<String, Object>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				Object value = entry.getValue();

				if (value != null) {
					if (value instanceof Map) {
						result.putAll(getDomainFieldMap(value));
					} else if (value instanceof String && StringUtil.isEmpty((String) value)) {
						continue;
					} else {
						result.put(entry.getKey(), value.toString());
					}
				}

			}
			return result;
		}

		Field[] fields = domain.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fileName = field.getName();
			// System.out.println(fileName);

			// 如果是签名字段的话直接忽略
			if ("sign".equals(fileName) || "serialVersionUID".equals(fileName) || "pageParams".equals(fileName) || "isSub2Page".equals(fileName)) {
				continue;
			}
			String getFieldMethod = "get" + captureName(fileName);
			Type type = field.getGenericType();
			String filedType = type.toString();

			// 如果是list不参与摘要算法
			if (type instanceof List || type instanceof ArrayList) {
				continue;
			}

			Method fieldMethod = domain.getClass().getMethod(getFieldMethod);
			Object obj = fieldMethod.invoke(domain);

			if (obj == null) {
				continue;
			}

			if (obj instanceof JSONObject) {
				result.putAll(getDomainFieldMap(obj));
				continue;
			}

			if (obj instanceof String)
				if (StringUtil.isEmpty((String) obj))
					continue;

			// 如果字段是自定义的类的成员变量则取相应的值
			if (filedType.startsWith("class com.ylzinfo.onepay.sdk.domain.")) {
				if (obj != null) {
					result.putAll(getDomainFieldMap(obj));
				}
				continue;
			}

			if ("param".equals(fileName) && !(obj instanceof Map)) {
				if (obj != null && !(obj instanceof List)) {
					result.putAll(getDomainFieldMap(obj));
				}
				continue;
			}

			if (obj instanceof Map && !(obj instanceof JSONObject)) {
				Map<String, String> map = (Map<String, String>) obj;
				Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					Object value = entry.getValue();

					if (value != null) {
						if (value instanceof String && StringUtil.isEmpty((String) value))
							continue;
						result.put(entry.getKey(), value.toString());
					}

				}
			} else {
				result.put(fileName, getParam(obj, filedType));
			}
		}
		return result;
	}

	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		int tmp7_6 = 0;
		char[] tmp7_5 = cs;
		tmp7_5[tmp7_6] = (char) (tmp7_5[tmp7_6] - ' ');
		return String.valueOf(cs);
	}

	private static String getParam(Object value, String filedType) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public static Object invokeMethod(Object obj, String functionName, Object[] params) throws Exception {
		Method method = null;
		if (null == params || params.length == 0) {
			// 处理无参调用
			method = obj.getClass().getMethod(functionName);
		} else {
			// 处理有参调用
			int p = params.length;
			@SuppressWarnings("rawtypes")
			Class[] paraTypes = new Class[p];
			for (int i = 0; i < paraTypes.length; i++) {
				paraTypes[i] = params[i].getClass();
			}
			method = obj.getClass().getMethod(functionName, paraTypes);
		}

		if (method == null) {
			throw new RuntimeException("方法为空");
		}
		Object rs = null;
		// 调用返回数据
		rs = method.invoke(obj, params);
		return rs;
	}

	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Object obj, String functionName, Object[] params, Class[] paramsTypes) throws Exception {
		Method method = null;
		if (null == params || params.length == 0) {
			// 处理无参调用
			method = obj.getClass().getMethod(functionName);
		} else {
			// 处理有参调用
			method = obj.getClass().getMethod(functionName, paramsTypes);
		}

		if (method == null) {
			throw new RuntimeException("方法为空");
		}
		Object rs = null;
		// 调用返回数据
		rs = method.invoke(obj, params);
		return rs;
	}

	/**
	 * getIndexMapFromList
	 * 
	 * @author zwsun 2015年9月8日
	 * @param args
	 * @throws Exception
	 */
	public static <T> Map<T, Integer> getIndexMapFromList(List<T> list) {
		if (list == null) {
			return null;
		}
		Map<T, Integer> map = new HashMap<T, Integer>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			map.put(list.get(i), i);
		}
		return map;
	}

	/**
	 * 获取 constant 内的 fieldName : fieldValue
	 * 
	 * @author zwsun 2015年11月5日
	 * @param constantClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getDomainContant(Class constantClazz) throws Exception {
		Field[] fields = constantClazz.getDeclaredFields();
		Map<String, String> name_value_map = new HashMap<String, String>();
		for (Field field : fields) {
			String name = field.getName();
			String value = field.get(constantClazz).toString();
			name_value_map.put(name, value);
		}
		return name_value_map;
	}

	/**
	 * 获取 constant 内的 fieldValue ：fildValue_ms
	 * 
	 * @author zwsun 2015年11月5日
	 * @param constantClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getDomainContantMS(Class constantClazz, String msKey) throws Exception {
		Field[] fields = constantClazz.getDeclaredFields();
		Map<String, String> name_value_map = new HashMap<String, String>();
		for (Field field : fields) {
			String name = field.getName();
			String value = field.get(constantClazz).toString();
			name_value_map.put(name, value);
		}
		Map<String, String> targetMap = new HashMap<String, String>();
		for (Entry<String, String> entry : name_value_map.entrySet()) {
			String name = entry.getKey();

			if (name.equals(msKey)) {
				continue;
			}

			int lastIndexof = name.lastIndexOf(msKey);
			// 过滤 name 不是 描述内容 的字段，
			if (lastIndexof == -1 && lastIndexof != name.length() - msKey.length()) {
				// 获取对应的 ms : fieldName
				String nameMs = name + msKey;
				if (!name_value_map.containsKey(nameMs)) {
					continue;// 跳过不存在描述的;
				}
				// 得到 描述内容
				String valueMs = name_value_map.get(name + msKey);
				String value = entry.getValue();
				// 将 code : 描述内容 放入map
				targetMap.put(value, valueMs);
			}
		}
		return targetMap;
	}

	/**
	 * 转换 目标类 demo : map
	 * 
	 * @author zwsun 2015年12月11日
	 * @param s
	 * @param t
	 * @return
	 */
	public static <T, S> T turnObject(S s, Class<T> t) {
		try {
			String jsonStr = JSON.toJSONString(s);
			return JSON.parseObject(jsonStr, t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeErrorException(null, "转换失败");
		}
	}

//	/**
//	 * 实体映射
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	public static <T> Map<String, Object> convertBeanToMap(T obj) {
//		return convertBeanToMap(null, obj);
//	}
//
//	/**
//	 * 键值 转换
//	 * 
//	 * @param prefix
//	 * @param value
//	 * @return
//	 */
//	public static Map<String, Object> convertBeanToMap(String prefix, Object obj) {
//		if (obj == null) {
//			return null;
//		}
//
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		try {
//			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//
//			for (PropertyDescriptor property : propertyDescriptors) {
//				String key = property.getName();
//
//				// 过滤属性
//				if (!ignoreSignList.contains(key)) {
//					// 得到property对应的getter方法
//					Method getter = property.getReadMethod();
//					
//					if (getter == null)
//						continue;
//					
//					Object value = getter.invoke(obj);
//
//					if (prefix != null)
//						map.put(String.format("%s[%s]", prefix, key), value);
//					else 
//						map.put(key, value);
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("convertBeanToMap Error " + e);
//		}
//
//		return map;
//
//	}

}
