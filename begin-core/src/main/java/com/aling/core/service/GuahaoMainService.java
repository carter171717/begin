package com.aling.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aling.core.bean.Pager;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.BaseReq;
import com.aling.core.enums.URLType;
import com.aling.core.exception.BusinessException;
import com.aling.core.util.*;


import java.util.List;
import java.util.Map;

public class GuahaoMainService {
	/**服务地址*/
	protected String serviceUrl;

	/**应用id*/
	protected String appId;

	/**应用密钥*/
	protected String appSecret;

	/**签名类型*/
	protected String signType;

	/**加密类型*/
	protected String encryptType;

	/**
	 * 请求版本
	 */
	protected String version;

	public GuahaoMainService(String serviceUrl, String appId, String appSecret, String signType, String encryptType) {
		this.serviceUrl = serviceUrl;
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = signType;
		this.encryptType = encryptType;
	}

	public GuahaoMainService(String serviceUrl, String appId, String appSecret, String signType, String encryptType, String version) {
		this.serviceUrl = serviceUrl;
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = signType;
		this.encryptType = encryptType;
		this.version = version;
	}

	public GuahaoMainService(String appId, String appSecret, String signType, String encryptType) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = signType;
		this.encryptType = encryptType;
	}

	/**
	 * 通用业务处理方法
	 */
	public <T> ResponseParams<T> doBusiness(String serviceId, Object params, Class<T> clz) throws BusinessException {
		try {
			String timestamp = DateUtil.getCurrentDateTime();

			// 创建请求参数对象
			RequestParams requestParams = new RequestParams();
			requestParams.setAppId(appId);
			requestParams.setTimestamp(timestamp);
			requestParams.setParam(params);
			requestParams.setServiceId(serviceId);
			requestParams.setSignType(signType);
			requestParams.setEncryptType(encryptType);
			requestParams.setVersion(version);
			requestParams.setSessionId(((BaseReq) params).getSessionId());
			requestParams.setToken(((BaseReq) params).getToken());
			((BaseReq) params).setSessionId(null);
			((BaseReq) params).setToken(null);
//			requestParams.setSessionId("APP_SESSION_CACHE1000#3b450dc2-d61c-48cb-ab11-571a64263638");
			// 创建签名信息
			String sign = Signature.createSign(requestParams, appSecret);
			requestParams.setSign(sign);

			// 加密报文
			try {
				System.out.println("加密前报文：" + JSONObject.toJSONString(requestParams));
				String encryptData = SecurityUtil.encrypt(JSONObject.toJSONString(requestParams.getParam()), encryptType, appSecret, appId);
				System.out.println("加密后报文：" + encryptData);
				requestParams.setEncryptData(encryptData);
			} catch (Exception e) {
				throw new BusinessException("请求报文加密失败");
			}

			// 清空明文
			requestParams.setParam(null);

			// 创建请求报文并发送请求
			String requestMessage = JSON.toJSONString(requestParams);
			System.out.println("请求参数报文：" + requestMessage);

			URLType urlType = URLType.HTTP;
			if (serviceUrl.toLowerCase().startsWith("https")) {
				urlType = URLType.HTTPS;
			}

			String responseMsg = HttpUtil.request(serviceUrl, requestMessage, urlType);

			if (StringUtil.isEmpty(responseMsg)) {
				throw new BusinessException("请求错误");
			}

			// 参数转换
			JSONObject res = JSON.parseObject(responseMsg);

			ResponseParams<T> responseParams = new ResponseParams<T>();
			responseParams.setSign(res.getString("sign"));
			responseParams.setSignType(res.getString("signType"));
			responseParams.setEncryptType(res.getString("encryptType"));
			responseParams.setTimestamp(res.getString("timestamp"));
			responseParams.setRespCode(res.getString("respCode"));
			responseParams.setRespMsg(res.getString("respMsg"));
			responseParams.setResultCode(res.getString("resultCode"));
			responseParams.setResultMsg(res.getString("resultMsg"));
			// 解密报文
			try {
				String encData = res.getString("encryptData");

				if (StringUtil.isNotEmpty(encData)) {
					System.out.println("解密前报文：" + JSONObject.toJSONString(responseParams));
					String decryptData = SecurityUtil.decrypt(encData, encryptType, appSecret, appId);
					res.put("param", JSON.parseObject(decryptData));
					responseParams.setParam(JSON.parseObject(decryptData, clz));
					System.out.println("解密后报文：" + JSONObject.toJSONString(responseParams));
				}
			} catch (Exception e) {
				throw new BusinessException("响应报文解密失败");
			}

			// 校验返回签名
			if (isSuccessful(responseParams) && !verifySign(res)) {
				throw new BusinessException("验签失败");
			}

			return responseParams;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public ResponseParams<Map<String,Object>> doBusinessMap(String serviceId, Object params) throws BusinessException {
		try {
			String timestamp = DateUtil.getCurrentDateTime();

			// 创建请求参数对象
			RequestParams requestParams = new RequestParams();
			requestParams.setAppId(appId);
			requestParams.setTimestamp(timestamp);
			requestParams.setParam(params);
			requestParams.setServiceId(serviceId);
			requestParams.setSignType(signType);
			requestParams.setEncryptType(encryptType);
			requestParams.setVersion(version);
			requestParams.setSessionId(((BaseReq) params).getSessionId());
			requestParams.setToken(((BaseReq) params).getToken());
			((BaseReq) params).setSessionId(null);
			((BaseReq) params).setToken(null);
//			requestParams.setSessionId("APP_SESSION_CACHE1000#3b450dc2-d61c-48cb-ab11-571a64263638");
			// 创建签名信息
			String sign = Signature.createSign(requestParams, appSecret);
			requestParams.setSign(sign);

			// 加密报文
			try {
				System.out.println("加密前报文：" + JSONObject.toJSONString(requestParams));
				String encryptData = SecurityUtil.encrypt(JSONObject.toJSONString(requestParams.getParam()), encryptType, appSecret, appId);
				System.out.println("加密后报文：" + encryptData);
				requestParams.setEncryptData(encryptData);
			} catch (Exception e) {
				throw new BusinessException("请求报文加密失败");
			}

			// 清空明文
			requestParams.setParam(null);

			// 创建请求报文并发送请求
			String requestMessage = JSON.toJSONString(requestParams);
			System.out.println("请求参数报文：" + requestMessage);

			URLType urlType = URLType.HTTP;
			if (serviceUrl.toLowerCase().startsWith("https")) {
				urlType = URLType.HTTPS;
			}

			String responseMsg = HttpUtil.request(serviceUrl, requestMessage, urlType);

			if (StringUtil.isEmpty(responseMsg)) {
				throw new BusinessException("请求错误");
			}

			// 参数转换
			JSONObject res = JSON.parseObject(responseMsg);

			ResponseParams<Map<String,Object>> responseParams = new ResponseParams<>();
			responseParams.setSign(res.getString("sign"));
			responseParams.setSignType(res.getString("signType"));
			responseParams.setEncryptType(res.getString("encryptType"));
			responseParams.setTimestamp(res.getString("timestamp"));
			responseParams.setRespCode(res.getString("respCode"));
			responseParams.setRespMsg(res.getString("respMsg"));
			responseParams.setResultCode(res.getString("resultCode"));
			responseParams.setResultMsg(res.getString("resultMsg"));
			// 解密报文
			try {
				String encData = res.getString("encryptData");

				if (StringUtil.isNotEmpty(encData)) {
					System.out.println("解密前报文：" + JSONObject.toJSONString(responseParams));
					String decryptData = SecurityUtil.decrypt(encData, encryptType, appSecret, appId);
					res.put("param", JSON.parseObject(decryptData));
					Map mapType = JSON.parseObject(decryptData,Map.class);
					responseParams.setParam(mapType);
					System.out.println("解密后报文：" + JSONObject.toJSONString(responseParams));
				}
			} catch (Exception e) {
				throw new BusinessException("响应报文解密失败");
			}

			// 校验返回签名
			if (isSuccessful(responseParams) && !verifySign(res)) {
				throw new BusinessException("验签失败");
			}

			return responseParams;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}


	/**
	 * 通用处理方法，适合返回列表请求
	 */
	@SuppressWarnings("unchecked")
	public <T> ResponseParams<List<T>> doBusinessArray(String serviceId, Object params, Class<T> clz) throws BusinessException {

		try {
			String timestamp = DateUtil.getCurrentDateTime();
			// 创建请求参数对象
			RequestParams requestParams = new RequestParams();
			requestParams.setAppId(appId);
			requestParams.setTimestamp(timestamp);
			requestParams.setParam(params);
			requestParams.setServiceId(serviceId);
			requestParams.setSignType(signType);
			requestParams.setVersion(version);
			requestParams.setEncryptType(encryptType);
			requestParams.setSessionId(((BaseReq) params).getSessionId());
			requestParams.setToken(((BaseReq) params).getToken());
			((BaseReq) params).setSessionId(null);
			((BaseReq) params).setToken(null);
			Pager pager = new Pager();
			pager.setPageNo(((BaseReq) params).getPageNo());
			pager.setRows(((BaseReq) params).getRows());
			requestParams.setPageParam(pager);
			// 创建签名信息
			String sign = Signature.createSign(requestParams, appSecret);
			requestParams.setSign(sign);

			// 加密报文
			System.out.println("加密前报文：" + JSONObject.toJSONString(requestParams));
			System.out.println("加密前报文：" + JSONObject.toJSONString(requestParams.getParam()));

			if (appId == "" || appId == null) {
				throw new BusinessException("appId不能为空");
			}

			if (appSecret == "" || appSecret == null) {
				throw new BusinessException("appSecret不能为空");
			}

			if (appId.length() < 8) {
				throw new BusinessException("appId长度至少不能小于8位");
			}

			if (appSecret.length() < 8) {
				throw new BusinessException("appSecret长度至少不能小于8位");
			}

			String encryptData = SecurityUtil.encrypt(JSONObject.toJSONString(requestParams.getParam()), encryptType, appSecret, appId);
			System.out.println("加密后报文：" + encryptData);
			requestParams.setEncryptData(encryptData);

			// 清空明文
			requestParams.setParam(null);

			// 创建请求报文并发送请求
			String requestMessage = JSON.toJSONString(requestParams);
			System.out.println("请求参数报文：" + requestMessage);

			URLType urlType = URLType.HTTP;
			if (serviceUrl.toLowerCase().startsWith("https")) {
				urlType = URLType.HTTPS;
			}

			String responseMsg = HttpUtil.request(serviceUrl, requestMessage, urlType);

			if (StringUtil.isEmpty(responseMsg)) {
				throw new BusinessException("请求错误");
			}

			// 参数转换
			JSONObject res = JSON.parseObject(responseMsg);

			// 参数转换
			ResponseParams<List<T>> responseParams = new ResponseParams<List<T>>();
			responseParams.setSign(res.getString("sign"));
			responseParams.setSignType(res.getString("signType"));
			responseParams.setEncryptType(res.getString("encryptType"));
			responseParams.setTimestamp(res.getString("timestamp"));
			responseParams.setRespCode(res.getString("respCode"));
			responseParams.setRespMsg(res.getString("respMsg"));
			responseParams.setResultMsg(res.getString("resultMsg"));
			responseParams.setResultCode(res.getString("resultCode"));
			responseParams.setPageParams(JSON.parseObject(res.getString("pageParams"), Map.class));
			try {
				// 解密报文
				String encData = res.getString("encryptData");
				if (!StringUtil.isEmpty(encData)) {
					System.out.println("解密前报文：" + JSONObject.toJSONString(responseParams));
					String decryptData = SecurityUtil.decrypt(encData, encryptType, appSecret, appId);

					// 解密结果
					res.put("param", JSONArray.parseArray(decryptData));
					responseParams.setParam(JSONArray.parseArray(decryptData, clz));
					System.out.println("解密后报文：" + JSONObject.toJSONString(responseParams));
				}
			} catch (Exception e) {
				throw new BusinessException("响应报文解密失败");
			}

			// 校验返回签名
			if (isSuccessful(responseParams) && !verifySign(res)) {
				throw new BusinessException("验签失败");
			}

			return responseParams;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	/**
	 * 通用业务处理方法
	 */
	public ResponseParams<JSONObject> execute(RequestParams requestParams) throws BusinessException {
		try {
			String timestamp = DateUtil.getCurrentDateTime();
			// 创建请求参数对象
			if (StringUtil.isEmpty(requestParams.getAppId()))
				requestParams.setAppId(appId);
			if (StringUtil.isEmpty(requestParams.getTimestamp()))
				requestParams.setTimestamp(timestamp);
			if (StringUtil.isEmpty(requestParams.getSignType()))
				requestParams.setSignType(signType);
			if (StringUtil.isEmpty(requestParams.getEncryptType()))
				requestParams.setEncryptType(encryptType);

			// 创建签名信息
			String sign = Signature.createSign(requestParams, appSecret);
			requestParams.setSign(sign);

			// 加密报文
			try {
				System.out.println("加密前报文：" + JSONObject.toJSONString(requestParams));
				String encryptData = SecurityUtil.encrypt(JSONObject.toJSONString(requestParams.getParam()), encryptType, appSecret, appId);
				System.out.println("加密后报文：" + encryptData);
				requestParams.setEncryptData(encryptData);
			} catch (Exception e) {
				throw new BusinessException("请求报文加密失败");
			}

			// 清空明文
			requestParams.setParam(null);

			// 创建请求报文并发送请求
			String requestMessage = JSON.toJSONString(requestParams);
			System.out.println("请求参数报文：" + requestMessage);

			URLType urlType = URLType.HTTP;
			if (serviceUrl.toLowerCase().startsWith("https")) {
				urlType = URLType.HTTPS;
			}

			String responseMsg = HttpUtil.request(serviceUrl, requestMessage, urlType);

			if (StringUtil.isEmpty(responseMsg)) {
				throw new BusinessException("请求错误");
			}

			// 参数转换
			JSONObject res = JSON.parseObject(responseMsg);

			ResponseParams<JSONObject> responseParams = new ResponseParams<JSONObject>();
			responseParams.setSign(res.getString("sign"));
			responseParams.setSignType(res.getString("signType"));
			responseParams.setEncryptType(res.getString("encryptType"));
			responseParams.setTimestamp(res.getString("timestamp"));
			responseParams.setRespCode(res.getString("respCode"));
			responseParams.setRespMsg(res.getString("respMsg"));
			// 解密报文
			try {
				String encData = res.getString("encryptData");

				if (StringUtil.isNotEmpty(encData)) {
					System.out.println("解密前报文：" + JSONObject.toJSONString(responseParams));
					String decryptData = SecurityUtil.decrypt(encData, encryptType, appSecret, appId);
					res.put("param", JSON.parseObject(decryptData));
					responseParams.setParam(JSON.parseObject(decryptData));
					System.out.println("解密后报文：" + JSONObject.toJSONString(responseParams));
				}
			} catch (Exception e) {
				throw new BusinessException("响应报文解密失败");
			}

			// 校验返回签名
			if (isSuccessful(responseParams) && !verifySign(res)) {
				throw new BusinessException("验签失败");
			}

			return responseParams;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	private boolean isSuccessful(ResponseParams<?> responseParams) {
		return responseParams != null && "000000".equals(responseParams.getRespCode());
	}

	/**
	 * 验证签名
	 */
	private boolean verifySign(JSONObject res) throws BusinessException {
		try {
			String oldSign = res.getString("sign");
			String newSign = Signature.createSign(res, appSecret);
			return oldSign.equals(newSign);
		} catch (Exception e) {
			throw new BusinessException("验证失败, " + e.getMessage());
		}
	}

	/**
	 * 验证签名
	 */
	public boolean verifySign(String responseMsg) throws BusinessException {
		return verifySign(JSON.parseObject(responseMsg));
	}

	/**
	 * 验证签名
	 */
	public boolean verifySign(ResponseParams<?> responseParams) throws BusinessException {
		return verifySign(JSON.parseObject(JSON.toJSONString(responseParams)));
	}

	/**
	 * 验证请求签名
	 */
	public boolean verifyRequestSign(String requestMessage) throws BusinessException {
		return verifySign(JSON.parseObject(requestMessage));
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getHospfaceUrl() {
		return serviceUrl;
	}

	public void setHospfaceUrl(String hospfaceUrl) {
		this.serviceUrl = hospfaceUrl;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}
}
