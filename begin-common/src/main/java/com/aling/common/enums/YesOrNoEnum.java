package com.aling.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 
 * @author wangly
 * @date 2018年4月27日
 */
public enum YesOrNoEnum implements IEnum {
	NO("0", "否"), YES("1", "是");

	public final String value;
	public final String desc;

	YesOrNoEnum(final String value, final String desc) {
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

	static Map<Object, YesOrNoEnum> enumMap = new HashMap<Object, YesOrNoEnum>();
	static {
		for (YesOrNoEnum type : YesOrNoEnum.values()) {
			enumMap.put(type.getValue(), type);
		}
	}

	public static YesOrNoEnum getEnum(String value) {
		return enumMap.get(value);
	}
}
