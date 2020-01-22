package com.aling.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aling.common.cache.JetCacheFactory;
import com.aling.common.cache.SysUserId2SessionInfoCache;
import com.aling.common.constant.Constants;
import com.aling.common.entity.Application;
import com.aling.common.entity.ServiceProvider;
import com.aling.common.enums.FromIdEnum;
import com.aling.common.service.ApplicationService;
import com.aling.common.service.OperationLogService;
import com.aling.common.service.ServiceProviderService;
import com.aling.common.util.DateUtil;
import com.aling.common.util.ReflectUtil;
import com.aling.common.util.SpringUtil;
import com.aling.common.util.UUIDHelper;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.RespConstant;
import com.aling.core.bean.ResponseParams;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.log.OperationLogDTO;
import com.aling.core.dto.system.AppSessionDto;
import com.aling.core.exception.AppBusinessException;
import com.aling.core.exception.AppException;
import com.aling.core.exception.AppSessionException;
import com.aling.core.exception.BusinessException;
import com.aling.core.util.SecurityUtil;
import com.aling.core.util.Signature;
import com.aling.core.util.StreamUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appointPlatform")
public class UnifyController {

    private Logger logger = LoggerFactory.getLogger(UnifyController.class);

    private static final String NEED_CHECK_SESSION = "Y";
    private static final String NEED_CHECK_TOKEN = "Y";
    private static final String NEED_SAVE_LOG = "Y";
    private static final String SERVICE_ID_LOGIN = "admin.sysUser.login";

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private SysUserId2SessionInfoCache sysUserId2SessionInfoCache;

    @Autowired
    private JetCacheFactory jetCacheFactory;

    @Autowired
    private OperationLogService operationLogService;
    /**
     * 接口请求处理
     *
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/unifyapi", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseParams<Object> receive(HttpServletRequest request, HttpServletResponse response) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        ResponseParams<Object> responseParams = new ResponseParams<Object>();
        RequestParams requestParams = new RequestParams();

        String appSecret = null;
        OperationLogDTO operationLogDTO = new OperationLogDTO();
        Map<String, String> logParam = new HashMap<>();
        String logId = UUIDHelper.getUUID("LOG", 32);

        ServiceProvider sysServiceProvider = null;
        try {
            inputStream = request.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            String requestMessage = StreamUtil.readInputStream(inputStreamReader);
            logger.info("接收到APP请求，请求报文为：{}, 日志标识：{}", requestMessage, logId);

            logParam.put("reqTime", DateUtil.getCurrentDateTime());
            logParam.put("reqTimestamp", String.valueOf(System.currentTimeMillis()));

            //处理报文验证
            Map<String, Object> map = doDealMessage(requestMessage);
            requestParams = (RequestParams) map.get("requestParams");
            appSecret = (String) map.get("appSecret");

            String respCode = RespConstant.SUCCESS_CODE;
            String respMsg = RespConstant.SUCCESS_CODE_MS;

            // 交易类型为空需要返回错误
            if (StringUtils.isBlank(requestParams.getServiceId())) {
                return error(responseParams, RespConstant.EROOR_NULL_TRANSTYPE, RespConstant.EROOR_NULL_TRANSTYPE_MS);
            }

            sysServiceProvider = serviceProviderService.selectById(requestParams.getServiceId());

            // 未知的服务ID返回错误
            if (sysServiceProvider == null) {
                return error(responseParams, RespConstant.ERROR_UNKOWN_SERVICEID, RespConstant.ERROR_UNKOWN_SERVICEID_MS);
            }

            Map<String, Object> externalMap = createUrl(request, requestParams.getExternalMap());
            if (StringUtils.equals(requestParams.getServiceId(), SERVICE_ID_LOGIN)) {
                logger.info("请求登录接口的sessionId:" + request.getRequestedSessionId());
                logger.info("session中的randomCode:" + request.getSession().getAttribute("randomCode"));
                externalMap.put("randomCode", request.getSession().getAttribute("randomCode"));
            }
            requestParams.setExternalMap(externalMap);

            Map<String, Object> param = (Map<String, Object>) requestParams.getParam();
            String requestParamsStr = JSONObject.toJSONString(param, SerializerFeature.DisableCircularReferenceDetect);
            logParam.put("reqParam", requestParamsStr);

            Object obj = SpringUtil.getBean(sysServiceProvider.getBeanName());

            // 校验session
            checkSession(sysServiceProvider, requestParams, externalMap,logParam);


            // 完成代理
            logger.info("【请求服务为】:{}, 日志标识：{}", sysServiceProvider.getServiceDesc(), logId);
            Object responseObj = ReflectUtil.invokeMethod(obj, sysServiceProvider.getMethod(), new Object[]{requestParams});

            if (responseObj instanceof ResponseParams) {
                responseParams = (ResponseParams<Object>) responseObj;
                respCode = responseParams.getRespCode();
                respMsg = responseParams.getRespMsg();
            } else {
                respCode = RespConstant.EROOR_RETURN_TYPE;
                respMsg = RespConstant.EROOR_RETURN_TYPE_MS;
            }

            responseParams.setRespCode(respCode);
            responseParams.setRespMsg(respMsg);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            if (throwable instanceof RuntimeException) {
                logger.error(e.getMessage(), e);
                RuntimeException realException = (RuntimeException) throwable;
                try {
                    BusinessException businessException = (BusinessException) realException;
                    responseParams.setRespCode(StringUtils.isBlank(businessException.getErrorCode()) ? RespConstant.ERROR_UNKOWN : businessException.getErrorCode());
                    responseParams.setRespMsg(StringUtils.isBlank(businessException.getErrorMsg()) ? RespConstant.ERROR_UNKOWN_MS : businessException.getErrorMsg());
                } catch (Exception e2) {
                    responseParams.setRespCode(RespConstant.ERROR_UNKOWN);
                    responseParams.setRespMsg(RespConstant.ERROR_UNKOWN_MS);
                }
            } else if (throwable instanceof AppBusinessException) {
                AppBusinessException realException = (AppBusinessException) throwable;
                responseParams.setRespCode(StringUtils.isBlank(realException.getErrorCode()) ? RespConstant.ERROR_UNKOWN : realException.getErrorCode());
                responseParams.setRespMsg(StringUtils.isBlank(realException.getMessage()) ? RespConstant.ERROR_UNKOWN_MS : realException.getMessage());
            } else if (throwable instanceof AppSessionException) {
                AppSessionException realException = (AppSessionException) throwable;
                responseParams.setRespCode(StringUtils.isBlank(realException.getErrorCode()) ? RespConstant.ERROR_NOT_LOGIN : realException.getErrorCode());
                responseParams.setRespMsg(StringUtils.isBlank(realException.getErrorMsg()) ? RespConstant.ERROR_NOT_LOGIN_MSG : realException.getErrorMsg());
            } else if (throwable instanceof AppException) {
                AppException realException = (AppException) throwable;
                responseParams.setRespCode(StringUtils.isBlank(realException.getErrorCode()) ? RespConstant.ERROR_UNKOWN : realException.getErrorCode());
                responseParams.setRespMsg(StringUtils.isBlank(realException.getErrorMsg()) ? RespConstant.ERROR_UNKOWN_MS : realException.getErrorMsg());
            } else {
                responseParams.setRespCode(RespConstant.ERROR_UNKOWN);
                responseParams.setRespMsg(RespConstant.ERROR_UNKOWN_MS);
            }
            logger.error(throwable.getMessage(), throwable);
        } catch (java.lang.reflect.UndeclaredThrowableException ue) {
            logger.error(ue.getMessage());
        } catch (NoSuchBeanDefinitionException e) {
            responseParams.setRespCode(RespConstant.EROOR_NO_DEFINED_BEAN);
            responseParams.setRespMsg(RespConstant.EROOR_NO_DEFINED_BEAN_MS);
            logger.error(e.getMessage(), e);
        } catch (AppSessionException e) {
            responseParams.setRespCode(StringUtils.isBlank(e.getErrorCode()) ? RespConstant.ERROR_NOT_LOGIN : e.getErrorCode());
            responseParams.setRespMsg(StringUtils.isBlank(e.getErrorMsg()) ? RespConstant.ERROR_NOT_LOGIN_MSG : e.getErrorMsg());
            logger.error(e.getMessage(), e);
        } catch (BusinessException e) {
            responseParams.setRespCode(StringUtils.isBlank(e.getErrorCode()) ? RespConstant.ERROR_UNKOWN : e.getErrorCode());
            responseParams.setRespMsg(StringUtils.isBlank(e.getErrorMsg()) ? RespConstant.ERROR_UNKOWN_MS : e.getErrorMsg());
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            responseParams.setRespCode(RespConstant.ERROR_UNKOWN);
            responseParams.setRespMsg(RespConstant.ERROR_UNKOWN_MS);
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }

                //返回报文进密码处理
                // 创建响应报文
                responseParams.setSignType(requestParams.getSignType());
                responseParams.setEncryptType(requestParams.getEncryptType());
                responseParams.setTimestamp(DateUtil.getCurrentDateTime());

                // 参数转换
                JSONObject res = JSON.parseObject(JSONObject.toJSONString(responseParams));

                //请求报文签名不为空,响应报文 创建签名信息
                if (StringUtils.isNotBlank(requestParams.getSign())) {
                    String sign = Signature.createSign(res, appSecret);
                    responseParams.setSign(sign);
                }

                String responseParamsStr = JSONObject.toJSONString(responseParams.getParam());
                logger.info("请求返回内容：{}, 日志标识：{}", responseParamsStr, logId);

                //请求报文加密不为空,响应报文进行报文加密
                if (StringUtils.isNotBlank(requestParams.getEncryptData())) {
                    responseParams.setEncryptData(responseParamsStr);

                    // 加密报文
                    logger.info("加密前响应报文:{}", JSONObject.toJSONString(responseParams));
                    String encryptData = SecurityUtil.encrypt(responseParamsStr, requestParams.getEncryptType(), appSecret, requestParams.getAppId());
                    logger.info("加密后响应报文:{}", encryptData);
                    responseParams.setEncryptData(encryptData);

                    // 清空明文
                    responseParams.setParam(null);
                }

                logParam.put("respTime", DateUtil.getCurrentDateTime());
                logParam.put("respTimestamp", String.valueOf(System.currentTimeMillis()));
                logParam.put("respParam", responseParamsStr);

                operationLogDTO.setReqParam(requestParams);
                operationLogDTO.setResParam(responseParams);
                operationLogDTO.setMap(logParam);

                operationLogDTO.setModuleCode("AppointPlatform");
                operationLogDTO.setModuleName("漳州预约平台");

                if (needSaveLog(sysServiceProvider)) {
                   // asyncTaskService.saveOperationLog(operationLogDTO);
                    operationLogService.addLog(operationLogDTO);
                }

            } catch (Exception e1) {
                logger.error("构造响应消息出现异常，异常信息：" + e1.getMessage(), e1);
            }
        }

        return responseParams;
    }






    private ResponseParams<Object> error(ResponseParams<Object> responseParams, String respCode, String respMsg) {
        responseParams.setRespCode(respCode);
        responseParams.setRespMsg(respMsg);
        return responseParams;
    }

    private void checkSession(ServiceProvider sysServiceProvider, RequestParams requestParams, Map<String, Object> externalMap, Map<String, String> logParam) throws BusinessException {
        String isNeedCheckSessionId = sysServiceProvider.getIsCheckSessionid();
        logger.info("是否需要验证sessionId={}",isNeedCheckSessionId);

        if (!NEED_CHECK_SESSION.equals(isNeedCheckSessionId)) {//无需验证
            return;
        }

        String sessionId = requestParams.getSessionId();
        logger.info("请求参数sessionId:{}", sessionId);

        if (StringUtils.isBlank(sessionId)) {
            throw new BusinessException(RespConstant.ERR_SESSION_MISS, RespConstant.ERR_SESSION_MISS_MS);
        }

        AppSessionDto dto = jetCacheFactory.getAppSessionCache().get(sessionId);
        if (dto == null) {
            throw new BusinessException(RespConstant.ERR_NO_APPSESSIONDTO, RespConstant.ERR_NO_APPSESSIONDTO_MS);
        }

        if (dto.isSys()) {// 系统管理用户校验即可
            SessionInfo sessionInfo = sysUserId2SessionInfoCache.getSessionInfo(dto.getUserId());
            if (sessionInfo == null) {
                throw new BusinessException(RespConstant.ERR_SESSION_TIMEOUT, RespConstant.ERR_SESSION_TIMEOUT_MS);
            }
            logParam.put("userName",sessionInfo.getUserName()); //日志记录
            logParam.put("userId",sessionInfo.getUserId()); //日子记录
        }

        externalMap.put(Constants.CHANNEL, FromIdEnum.ADMIN.value);
    }


    private Map<String, Object> createUrl(HttpServletRequest request, Map<String, Object> externalMap) {
        String url = request.getContextPath();
        if (null == externalMap) {
            externalMap = new HashMap<String, Object>();
        }
        externalMap.put("url", url);
        return externalMap;
    }

    private Map<String, Object> doDealMessage(String requestMessage) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(requestMessage)) {
            throw new BusinessException(RespConstant.ERROR_REQUEST_MSG_EMPTY, RespConstant.ERROR_REQUEST_MSG_EMPTY_MS);
        }
        RequestParams requestParams = JSON.parseObject(requestMessage, RequestParams.class);
        if (requestParams == null) {
            throw new BusinessException(RespConstant.ERROR_REQUEST_MSG_ILLEGAL, RespConstant.ERROR_REQUEST_MSG_ILLEGAL_MS);
        }
        // 获取appid、APPSecret并判断是否为空
        String appId = requestParams.getAppId();
        if (StringUtils.isBlank(appId)) {
            throw new BusinessException(RespConstant.ERROR_APP_ID_EMPTY, RespConstant.ERROR_APP_ID_EMPTY_MS);
        }
        Application application = applicationService.selectById(appId);
        if (application == null) {
            throw new BusinessException(RespConstant.ERROR_APP_ID_ILLEGAL, RespConstant.ERROR_APP_ID_ILLEGA_MS);
        }

        String appSecret = application.getAppSecret();
        String version = requestParams.getVersion();
        String serviceId = requestParams.getServiceId();
        String timestamp = requestParams.getTimestamp();

        //时间戳为空返回错误
        if (StringUtils.isBlank(timestamp)) {
            throw new BusinessException(RespConstant.ERROR_TIMESTAMP_EMPTY, RespConstant.ERROR_TIMESTAMP_EMPTY_MS);
        }
        // 未知的接口版本返回错误
        if (StringUtils.isBlank(version)) {
            throw new BusinessException(RespConstant.ERROR_VERSION_EMPTY, RespConstant.ERROR_VERSION_EMPTY_MS);
        }
        // 未知服务ID返回错误
        if (StringUtils.isBlank(serviceId)) {
            throw new BusinessException(RespConstant.ERROR_UNKOWN_SERVICEID, RespConstant.ERROR_UNKOWN_SERVICEID_MS);
        }

        try {
            String encryptData = requestParams.getEncryptData();
            //支持h5不加密
            if (StringUtils.isNotBlank(encryptData)) {
                logger.info("请求密文:{}", encryptData);
                String decryptData = SecurityUtil.decrypt(encryptData, requestParams.getEncryptType(), appSecret, appId);
                requestParams.setParam(JSON.parseObject(decryptData, Object.class));
            }
        } catch (Exception e) {
            logger.error("解密失败:", e);
            throw new BusinessException(RespConstant.ERROR_REPORT_ENC_FAIL, RespConstant.ERROR_REPORT_ENC_FAIL_MS);
        }

        try {
            String signOld = requestParams.getSign();
            //支持h5不签名
            if (StringUtils.isNotBlank(signOld)) {
                String signNew = Signature.createSign(requestParams, appSecret);
                logger.info("报文签名，报文签名的秘钥 appSecret：{}",appSecret);
                logger.info("报文签名，报文签名值：{}，服务端构造的签名值：{}", signOld, signNew);
                boolean isVerify = signOld.equals(signNew);
                if (!isVerify) {
                    throw new BusinessException(RespConstant.EROOR_FAIL_VERIFY, RespConstant.EROOR_FAIL_VERIFY_MS);
                }
            }
        } catch (Exception e) {
            logger.error("验证签名失败:", e);
            throw new BusinessException(RespConstant.EROOR_FAIL_VERIFY, RespConstant.EROOR_FAIL_VERIFY_MS);
        }

        result.put("requestParams", requestParams);
        result.put("appSecret", appSecret);
        return result;
    }

    /**
     * @Description:
     * @author wangly
     * @date 2019年1月31日
     */
    private boolean needSaveLog(ServiceProvider sysServiceProvider) {

        if (sysServiceProvider != null && NEED_SAVE_LOG.equals(sysServiceProvider.getLogFlag())) {
            return true;
        }else {
            return false;
        }

    }

}
