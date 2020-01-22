package com.aling.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@TableName("SYS_MENU")
@KeySequence(value = "SEQ_SYS_MENU", clazz = String.class)
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 菜单名称
     */
    @TableField("MENU_NAME")
    private String menuName;

    /**
     * 菜单类型 1：菜单 2：按钮
     */
    @TableField("MENU_TYPE")
    private String menuType;

    /**
     * 父级菜单ID
     */
    @TableField("PID")
    private String pid;
    /**
     * url地址
     */
    @TableField("MENU_URL")
    private String menuUrl;
    /**
     * 菜单图标地址
     */
    @TableField("ICON_URL")
    private String iconUrl;
    /**
     * 服务ID
     */
    @TableField("SERVICE_ID")
    private Integer serviceId;
    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;
    /**
     * 状态，0-未启用，1-已经启用
     */
    @TableField("STATUS")
    private Integer status;
    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    private String description;


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Menu{" +
        "id=" + id +
        ", menuName=" + menuName +
        ", pid=" + pid +
        ", menuUrl=" + menuUrl +
        ", iconUrl=" + iconUrl +
        ", serviceId=" + serviceId +
        ", sort=" + sort +
        ", status=" + status +
        ", description=" + description +
        "}";
    }
}
