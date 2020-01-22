package com.aling.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 状态
 * @author fugl
 * @date 2018年5月2日
 */
public enum StatusEnum implements IEnum {
	DISABLE("0", "禁用"), ENABLE("1", "启用");

	public final String value;
	public final String desc;

	StatusEnum(final String value, final String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Serializable getValue() {
		return this.value;
	}

	@JsonValue // Jackson 注解，可序列化该属性为中文描述【可无】
	public String getDesc() {
		return this.desc;
	}

	static Map<Object, StatusEnum> enumMap = new HashMap<Object, StatusEnum>();
	static {
		for (StatusEnum type : StatusEnum.values()) {
			enumMap.put(type.getValue(), type);
		}
	}

	public static StatusEnum getEnum(String value) {
		return enumMap.get(value);
	}
}
