package com.aling.common.service.impl;

import com.aling.common.entity.Application;
import com.aling.common.entity.OperationLog;
import com.aling.common.entity.ServiceProvider;
import com.aling.common.mapper.OperationLogMapper;
import com.aling.common.service.ApplicationService;
import com.aling.common.service.OperationLogService;
import com.aling.common.service.ServiceProviderService;
import com.aling.common.util.DateUtil;
import com.aling.common.util.StringUtil;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.log.OperationLogDTO;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-21
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {


    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @SuppressWarnings("unchecked")
    @Override
    public void addLog(OperationLogDTO operationLogDTO) {
        OperationLog operationLog = new OperationLog();
        RequestParams requestParams = operationLogDTO.getReqParam();
        ResponseParams<?> responseParams = operationLogDTO.getResParam();
        Map<String, String> logParam = operationLogDTO.getMap();

        //日志类型，默认为REQ
        operationLog.setLogType("REQ");
        //接口编号
        operationLog.setFuncId(requestParams.getServiceId());
        //应用编号
        //登录用户名称
        operationLog.setUserName(logParam.get("userName"));

        Application application = applicationService.selectById(requestParams.getAppId());
        operationLog.setAppId(requestParams.getAppId());
        if (application != null) {
            operationLog.setAppName(application.getAppName());
        }

        ServiceProvider sysServiceProvider = serviceProviderService.selectById(requestParams.getServiceId());
        if (sysServiceProvider != null) {
            operationLog.setFuncName(sysServiceProvider.getServiceDesc());
        }

        //医院编号
        Map<String, Object> para;
        if (null != requestParams.getParam()) {
            para = (Map<String, Object>) requestParams.getParam();
        } else {
            para = new HashMap<>();
        }
        if (StringUtil.isNotEmpty(String.valueOf(para.get("merchId")))) {
            operationLog.setHospitalId(String.valueOf(para.get("merchId")));
        } else if (StringUtil.isNotEmpty(String.valueOf(para.get("hospitalId")))) {
            operationLog.setHospitalId(String.valueOf(para.get("hospitalId")));
        }

        //系统跟踪号
        operationLog.setOperTrace(requestParams.getReqNo());
        //签名
        operationLog.setReqSign(requestParams.getSign());
        //签名类型
        operationLog.setSignType(requestParams.getSignType());
        //加密类型
        operationLog.setEncryptType(requestParams.getEncryptType());
        //版本号
        operationLog.setVersion(requestParams.getVersion());

        //业务返回码
        operationLog.setRespCode(responseParams.getRespCode());

        //业务返回文字信息
        operationLog.setRespMsg(responseParams.getRespMsg());

        //请求报文
        operationLog.setReqParam(logParam.get("reqParam"));

        //响应报文
        operationLog.setRespParam(logParam.get("respParam"));

        operationLog.setReqTime(logParam.get("reqTime"));
        operationLog.setRespTime(logParam.get("respTime"));
        Long respTimestamp = Long.parseLong(logParam.get("respTimestamp"));
        Long reqTimestamp = Long.parseLong(logParam.get("reqTimestamp"));
        operationLog.setCostTime(String.valueOf(respTimestamp - reqTimestamp));

        operationLog.setModuleCode(operationLogDTO.getModuleCode());
        operationLog.setModuleName(operationLogDTO.getModuleName());
        operationLog.setReqDate(DateUtil.getCurrDate());

        operationLogMapper.insert(operationLog);

        //添加到预警分析
        //JedisClient.getInstance().lpush(CacheConstant.QUEUE_MONITOR_KEY, operatorLog);
        //添加到操作日志
        //JedisClient.getInstance().lpush(CacheConstant.QUEUE_OPERATE_KEY, operatorLog);
    }
}
