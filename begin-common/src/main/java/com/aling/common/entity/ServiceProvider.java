package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 应用表
 * </p>
 *
 * @author wangly
 * @since 2020-01-20
 */
@TableName("API_SERVICE_PROVIDER")
@KeySequence(value = "SEQ_SERVICE_PROVIDER", clazz = String.class)
public class ServiceProvider extends Model<ServiceProvider> {

    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @TableId(value = "SERVICE_ID", type = IdType.UUID)
    private String serviceId;
    /**
     * 服务描述
     */
    @TableField("SERVICE_DESC")
    private String serviceDesc;
    /**
     * 处理bean名称
     */
    @TableField("BEAN_NAME")
    private String beanName;
    /**
     * 处理方法名
     */
    @TableField("METHOD")
    private String method;
    /**
     * 处理页面
     */
    @TableField("PAGE_URL")
    private String pageUrl;
    /**
     * 记录日志标识Y=记录 N=不记录
     */
    @TableField("LOG_FLAG")
    private String logFlag;
    /**
     * Y=CHECK N=NO
     */
    @TableField("IS_CHECK_SESSIONID")
    private String isCheckSessionid;
    /**
     * Y=CHECK N=NO
     */
    @TableField("IS_CHECK_TOKEN")
    private String isCheckToken;


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getLogFlag() {
        return logFlag;
    }

    public void setLogFlag(String logFlag) {
        this.logFlag = logFlag;
    }

    public String getIsCheckSessionid() {
        return isCheckSessionid;
    }

    public void setIsCheckSessionid(String isCheckSessionid) {
        this.isCheckSessionid = isCheckSessionid;
    }

    public String getIsCheckToken() {
        return isCheckToken;
    }

    public void setIsCheckToken(String isCheckToken) {
        this.isCheckToken = isCheckToken;
    }

    @Override
    protected Serializable pkVal() {
        return this.serviceId;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
        "serviceId=" + serviceId +
        ", serviceDesc=" + serviceDesc +
        ", beanName=" + beanName +
        ", method=" + method +
        ", pageUrl=" + pageUrl +
        ", logFlag=" + logFlag +
        ", isCheckSessionid=" + isCheckSessionid +
        ", isCheckToken=" + isCheckToken +
        "}";
    }
}
