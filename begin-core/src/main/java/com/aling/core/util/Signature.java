package com.aling.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.enums.SignType;


import java.util.*;
import java.util.Map.Entry;

public class Signature {
	private static List<String> ignoreSign = new ArrayList<String>();

	static {
		ignoreSign.add("sign");
		ignoreSign.add("encryptData");
		ignoreSign.add("externalMap");
		ignoreSign.add("pageParam");
		ignoreSign.add("externalNo");
		ignoreSign.add("reqNo");
	}

	/**
	 * 请求签名
	 */
	public static String createSign(RequestParams requestParams, String appSecret) {
		return createSign(JSON.parseObject(JSON.toJSONString(requestParams)), appSecret);
	}

	/**
	 * 响应签名
	 */
	public static String createSign(ResponseParams<?> responseParams, String appId, String appSecret) {
		return createSign(JSON.parseObject(JSON.toJSONString(responseParams)), appSecret);
	}

	/**
	 * 创建签名
	 */
	public static String createSign(JSONObject jsonObject, String appSecret) {
		// 签名数据集合
		Map<String, String> signMap = new TreeMap<String, String>();
		Set<Entry<String, Object>> entrys = jsonObject.entrySet();

		// 获取签名键值
		for (Entry<String, Object> entry : entrys) {
			// 非空 且 非过滤签名组合
			if (!StringUtil.isEmpty(entry.getValue()) && !ignoreSign.contains(entry.getKey())) {
				signMap.put(entry.getKey(), getValue(entry.getValue()));
			}
		}

		// 创建签名
		String sign = Signature.getSign(signMap, appSecret);
		return sign;
	}

	/**
	 * 取值
	 */
	private static String getValue(Object value) {
		if (value instanceof String)
			return getObjString(value);
		else
			return treeJsonParam(value);
	}

	/**
	 * 跳转签名
	 */
	public static String createSign(String appId, int chargeAmt, String outChargeNo, String status, String channel, String timestamp, String appSecret, String signType) {
		String result = appId + chargeAmt + outChargeNo + status + channel + timestamp + appSecret;
		System.out.println("signType:" + signType);
		if (StringUtil.isEmpty(signType) || SignType.MD5.toString().equals(signType)) {
			System.out.println("Sign Before MD5:" + result);
			result = MD5Util.encrypt(appId + chargeAmt + outChargeNo + status + channel + timestamp + appSecret);
			System.out.println("Sign Result MD5:" + result);
		} else if (SignType.RSA.name().equals(signType)) {
			System.out.println("Sign Before RSA:" + result);
			// TODO RSA 签名
			System.out.println("Sign Result RSA:" + result);
		}
		return result;
	}

	/**
	 * 对MAP签名 过滤空值 拼接&key=***
	 */
	public static String getSign(Map<String, String> map, String key) {
		if (map == null)
			return "";

		ArrayList<String> list = new ArrayList<String>();

		for (Entry<String, String> entry : map.entrySet()) {
			if (isNotEmpty(getObjString(entry.getValue()))) {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}

		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		String signType = map.get("signType");

		if (StringUtil.isEmpty(signType) || SignType.MD5.toString().equals(signType)) {
			System.out.println("Sign Before MD5:" + result);
			result = MD5Util.encrypt(result).toUpperCase();
			System.out.println("Sign Result MD5:" + result);
		} else if (SignType.RSA.name().equals(signType)) {
			System.out.println("Sign Before RSA:" + result);
			// TODO RSA 签名

			System.out.println("Sign Result RSA:" + result);
		}

		return result;
	}

	/**
	 * 对象转换为字符串
	 */
	public static String getObjString(Object object) {
		return (object == null ? "" : (String) object);
	}

	private static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	private static boolean isEmpty(String string) {
		if (string == null || "".equals(string.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 转换PARAM
	 */
	private static String treeJsonParam(Object value) {
		String jsoNParam = null;

		if (value instanceof Map<?, ?>) {
			Map<String, Object> treeNestedMap = new TreeMap<String, Object>();

			Map<?, ?> nestedMap = (Map<?, ?>) value;

			for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
				treeNestedMap.put((String) nestedEntry.getKey(), nestedEntry.getValue());
			}

			jsoNParam = JSONObject.toJSONString(treeParams(treeNestedMap));
		} else if (value instanceof JSONObject) {
			Map<String, Object> jsonMap = new TreeMap<String, Object>();

			JSONObject nestedMap = (JSONObject) value;

			for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
				jsonMap.put((String) nestedEntry.getKey(), nestedEntry.getValue());
			}

			jsoNParam = JSONObject.toJSONString(treeParams(jsonMap));
		} else if (value instanceof ArrayList<?>) {
			ArrayList<?> ar = (ArrayList<?>) value;
			jsoNParam = JSONObject.toJSONString(treeList((ar)));
		} else if (value instanceof JSONArray) {
			JSONArray jarr = (JSONArray) value;
			jsoNParam = JSONObject.toJSONString(treeJsonArray((jarr)));
		} else if (value != null && value.getClass().getPackage().getName().startsWith("com.ylzinfo.onepay.sdk.domain")) {
			jsoNParam = JSONObject.toJSONString(treeParams(JSONObject.parseObject(JSON.toJSONString(value))));
		} else if (value == null) {

		} else {
			jsoNParam = value.toString();
		}

		return jsoNParam;
	}

	/**
	 * 获取响应签名参数
	 */
	protected static Map<String, String> signParams(ResponseParams<?> responseParams) {
		Map<String, String> signMap = new TreeMap<String, String>();
		signMap.put("respCode", responseParams.getRespCode());
		signMap.put("respMsg", responseParams.getRespMsg());
		signMap.put("signType", responseParams.getSignType());
		signMap.put("encryptType", responseParams.getEncryptType());
		signMap.put("timestamp", responseParams.getTimestamp());
		signMap.put("param", treeJsonParam(responseParams.getParam()));
		return signMap;
	}

	/**
	 * 签名集合算法 -- 排序
	 */
	private static Map<String, Object> treeParams(Map<String, Object> params) {
		if (params == null) {
			return new TreeMap<String, Object>();
		}

		Map<String, Object> treeParams = new TreeMap<String, Object>();

		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof Map<?, ?>) {
				Map<String, Object> treeNestedMap = new TreeMap<String, Object>();

				Map<?, ?> nestedMap = (Map<?, ?>) value;

				for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					treeNestedMap.put((String) nestedEntry.getKey(), nestedEntry.getValue());
				}

				treeParams.put(key, treeParams(treeNestedMap));
			} else if (value instanceof JSONObject) {
				Map<String, Object> treeNestedMap = new TreeMap<String, Object>();

				JSONObject nestedMap = (JSONObject) value;

				for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					treeNestedMap.put(key, nestedEntry.getValue());
				}

				treeParams.put(key, treeParams(treeNestedMap));
			} else if (value instanceof ArrayList<?>) {
				ArrayList<?> ar = (ArrayList<?>) value;
				treeParams.put(key, treeList(ar));
			} else if (value instanceof JSONArray) {
				JSONArray ar = (JSONArray) value;
				treeParams.put(key, treeJsonArray(ar));
			} else if ("".equals(value)) {
				// flatParams.put(key, "");
			} else if (value == null) {
				// flatParams.put(key, "");
			} else if (value != null && value.getClass().getPackage().getName().startsWith("com.ylzinfo.onepay.sdk.domain")) { // 实体类
				treeParams.put(key, treeParams(JSONObject.parseObject(JSON.toJSONString(value))));
			} else {
				treeParams.put(key, value.toString());
			}
		}

		return treeParams;
	}

	/**
	 * JsonArray排序
	 */
	private static JSONArray treeJsonArray(JSONArray jarr) {
		if (jarr == null || jarr.size() == 0)
			return null;

		JSONArray jsonArray = new JSONArray();

		int size = jarr.size();

		for (int i = 0; i < size; i++) {
			Object value = jarr.get(i);

			if (value instanceof Map<?, ?>) {
				Map<String, Object> treeNestedMap = new TreeMap<String, Object>();

				Map<?, ?> nestedMap = (Map<?, ?>) value;

				for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					treeNestedMap.put((String) nestedEntry.getKey(), nestedEntry.getValue());
				}

				jsonArray.add(i, treeParams(treeNestedMap));
			} else if (value instanceof JSONObject) {
				Map<String, Object> treeNestedMap = new TreeMap<String, Object>();

				JSONObject nestedMap = (JSONObject) value;

				for (Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					treeNestedMap.put((String) nestedEntry.getKey(), nestedEntry.getValue());
				}

				jsonArray.add(i, treeParams(treeNestedMap));
			} else if (value instanceof ArrayList<?>) {
				ArrayList<?> ar = (ArrayList<?>) value;
				jsonArray.add(i, treeList(ar));
			} else if (value instanceof JSONArray) {
				JSONArray ar = (JSONArray) value;
				jsonArray.add(i, treeJsonArray(ar));
			} else if (value == null || "".equals(value)) {
				// flatParams.put(key, "");
			} else if (value != null && value.getClass().getPackage().getName().startsWith("com.ylzinfo.onepay.sdk.domain")) { // 实体类
				jsonArray.add(i, treeParams(JSONObject.parseObject(JSON.toJSONString(value))));
			} else {
				jsonArray.add(i, value.toString());
			}
		}

		return jsonArray;
	}

	/**
	 * List排序
	 */
	private static JSONArray treeList(ArrayList<?> list) {
		if (list == null || list.size() == 0)
			return null;

		JSONArray jsonArray = new JSONArray();
		int size = list.size();

		for (int i = 0; i < size; i++) {
			jsonArray.add(i, list.get(i));
		}

		return treeJsonArray(jsonArray);
	}
}
