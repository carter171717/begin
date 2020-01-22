package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 应用表
 * </p>
 *
 * @author wangly
 * @since 2020-01-20
 */
@TableName("API_APPLICATION")
public class Application extends Model<Application> {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编号
     */
    @TableId(value = "APP_ID", type = IdType.UUID)
    private String appId;
    /**
     * 应用密钥
     */
    @TableField("APP_SECRET")
    private String appSecret;
    /**
     * 应用名称
     */
    @TableField("APP_NAME")
    private String appName;
    /**
     * 应用状态:01-正常,00-暂停
     */
    @TableField("STATUS")
    private String status;
    /**
     * 应用类型
     */
    @TableField("APP_TYPE")
    private String appType;
    /**
     * 加密类型:AES,DES等
     */
    @TableField("ENC_TYPE")
    private String encType;
    /**
     * 前面类型:MD5,Plain等
     */
    @TableField("SIGN_TYPE")
    private String signType;


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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getEncType() {
        return encType;
    }

    public void setEncType(String encType) {
        this.encType = encType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    @Override
    protected Serializable pkVal() {
        return this.appId;
    }

    @Override
    public String toString() {
        return "Application{" +
        "appId=" + appId +
        ", appSecret=" + appSecret +
        ", appName=" + appName +
        ", status=" + status +
        ", appType=" + appType +
        ", encType=" + encType +
        ", signType=" + signType +
        "}";
    }
}
