package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author wangly
 * @since 2020-01-21
 */
@TableName("API_OPERATION_LOG")
@KeySequence(value = "SEQ_OPERATION_LOG", clazz = String.class)
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 日志跟踪
     */
    @TableField("OPER_TRACE")
    private String operTrace;
    /**
     * 登录用户名称
     */
    @TableField("USER_NAME")
    private String userName;
    /**
     * 日志类型:REQ-请求,RESP-应答
     */
    @TableField("LOG_TYPE")
    private String logType;
    /**
     * 应用ID
     */
    @TableField("APP_ID")
    private String appId;
    /**
     * 应用名称
     */
    @TableField("APP_NAME")
    private String appName;
    /**
     * 医院ID
     */
    @TableField("HOSPITAL_ID")
    private String hospitalId;
    /**
     * 医院名称
     */
    @TableField("HOSPITAL_NAME")
    private String hospitalName;
    /**
     * 模块代码
     */
    @TableField("MODULE_CODE")
    private String moduleCode;
    /**
     * 模块名称
     */
    @TableField("MODULE_NAME")
    private String moduleName;
    /**
     * 接口ID
     */
    @TableField("FUNC_ID")
    private String funcId;
    /**
     * 接口名称
     */
    @TableField("FUNC_NAME")
    private String funcName;
    /**
     * 加密类型:DES,AES,PLAIN
     */
    @TableField("ENCRYPT_TYPE")
    private String encryptType;
    /**
     * 签名类型
     */
    @TableField("SIGN_TYPE")
    private String signType;
    /**
     * 版本
     */
    @TableField("VERSION")
    private String version;
    /**
     * 耗时
     */
    @TableField("COST_TIME")
    private String costTime;
    /**
     * 请求日期
     */
    @TableField("REQ_DATE")
    private String reqDate;
    /**
     * 请求时间
     */
    @TableField("REQ_TIME")
    private String reqTime;
    /**
     * 请求签名
     */
    @TableField("REQ_SIGN")
    private String reqSign;
    /**
     * 请求参数
     */
    @TableField("REQ_PARAM")
    private String reqParam;
    /**
     * 应答时间
     */
    @TableField("RESP_TIME")
    private String respTime;
    /**
     * 应答参数
     */
    @TableField("RESP_PARAM")
    private String respParam;
    /**
     * 应答代码
     */
    @TableField("RESP_CODE")
    private String respCode;
    /**
     * 应答消息
     */
    @TableField("RESP_MSG")
    private String respMsg;
    /**
     * 结果代码
     */
    @TableField("RESULT_CODE")
    private String resultCode;
    /**
     * 结果消息
     */
    @TableField("RESULT_MSG")
    private String resultMsg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperTrace() {
        return operTrace;
    }

    public void setOperTrace(String operTrace) {
        this.operTrace = operTrace;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqSign() {
        return reqSign;
    }

    public void setReqSign(String reqSign) {
        this.reqSign = reqSign;
    }

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRespParam() {
        return respParam;
    }

    public void setRespParam(String respParam) {
        this.respParam = respParam;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
        "id=" + id +
        ", operTrace=" + operTrace +
        ", userName=" + userName +
        ", logType=" + logType +
        ", appId=" + appId +
        ", appName=" + appName +
        ", hospitalId=" + hospitalId +
        ", hospitalName=" + hospitalName +
        ", moduleCode=" + moduleCode +
        ", moduleName=" + moduleName +
        ", funcId=" + funcId +
        ", funcName=" + funcName +
        ", encryptType=" + encryptType +
        ", signType=" + signType +
        ", version=" + version +
        ", costTime=" + costTime +
        ", reqDate=" + reqDate +
        ", reqTime=" + reqTime +
        ", reqSign=" + reqSign +
        ", reqParam=" + reqParam +
        ", respTime=" + respTime +
        ", respParam=" + respParam +
        ", respCode=" + respCode +
        ", respMsg=" + respMsg +
        ", resultCode=" + resultCode +
        ", resultMsg=" + resultMsg +
        "}";
    }
}
