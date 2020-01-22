package com.aling.core.dto.system;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 系统菜单响应
 * @author fugl
 * @date 2018年8月31日
 */
public class SysMenuRes implements Serializable {
	private static final long serialVersionUID = -5714352410261746429L;

	private String id;

	private String menuId;

	private String menuName;

	private String description;

	private String pid;

	private String menuUrl;

	private String uri;

	private String serviceId;

	private Integer sortIndex;

	private String status;

	private String iconUrl;

	private String menuType;

	List<SysMenuRes> menuList;

	List<SysMenuRes> buttonAuthList;

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

	public List<SysMenuRes> getButtonAuthList() {
		return buttonAuthList;
	}

	public void setButtonAuthList(List<SysMenuRes> buttonAuthList) {
		this.buttonAuthList = buttonAuthList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public List<SysMenuRes> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenuRes> menuList) {
		this.menuList = menuList;
	}
}
