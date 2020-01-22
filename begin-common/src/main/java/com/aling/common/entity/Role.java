package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@TableName("SYS_ROLE")
@KeySequence(value = "SEQ_SYS_ROLE", clazz = String.class)
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField("ROLE_DESC")
    private String roleDesc;
    /**
     * 状态,1-启用,0-禁用
     */
    @TableField("ROLE_STATE")
    private Integer roleState;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private String createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Role{" +
        "id=" + id +
        ", roleName=" + roleName +
        ", roleDesc=" + roleDesc +
        ", roleState=" + roleState +
        ", createTime=" + createTime +
        "}";
    }
}
