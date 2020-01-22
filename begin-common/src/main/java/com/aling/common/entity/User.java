package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@TableName("SYS_USER")
@KeySequence(value = "SEQ_SYS_USER", clazz = String.class)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 联系方式
     */
    @TableField("PHONE")
    private String phone;
    /**
     * 用户状态,1-启用,0-禁用
     */
    @TableField("USER_STATE")
    private Integer userState;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private String createTime;
    /**
     * 描述
     */
    @TableField("USER_DESC")
    private String userDesc;
    /**
     * 头像
     */
    @TableField("USER_IMG")
    private String userImg;
    /**
     * 部门主键
     */
    @TableField("DEPT_ID")
    private String deptId;
    /**
     * 用户类型
     */
    @TableField("STAFF_TYPE")
    private String staffType;
    /**
     * 员工工号
     */
    @TableField("STAFF_NO")
    private String staffNo;
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
     * 科室名称
     */
    @TableField("DEPT_NAME")
    private String deptName;
    /**
     * 密码加密
     */
    @TableField("SALT")
    private String salt;

    /**
     * 上次登录时间
     */
    @TableField("LAST_LOGIN_TIME")
    private Date lastLoginTime;
    /**
     * 错误次数
     */
    @TableField("ERROR_TIMES")
    private Integer errorTimes;
    /**
     * 冻结时间
     */
    @TableField("FREEZE_TIME")
    private Date freezeTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public Date getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Date freezeTime) {
        this.freezeTime = freezeTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", userName=" + userName +
        ", password=" + password +
        ", phone=" + phone +
        ", userState=" + userState +
        ", createTime=" + createTime +
        ", userDesc=" + userDesc +
        ", userImg=" + userImg +
        ", deptId=" + deptId +
        ", staffType=" + staffType +
        ", staffNo=" + staffNo +
        ", hospitalId=" + hospitalId +
        ", hospitalName=" + hospitalName +
        ", deptName=" + deptName +
        ", salt=" + salt +
        "}";
    }
}
