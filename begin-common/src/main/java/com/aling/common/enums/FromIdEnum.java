package com.aling.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 请求来源(渠道) 
 * @author fugl
 * @date 2019年1月31日
 */
public enum FromIdEnum implements IEnum {
	WX("WX", "微信公众号"), ALI("ALI", "支付宝生活号"), ANDROID("ANDROID", "安卓手机应用"), IOS("IOS", "苹果手机应用"), ADMIN("ADMIN",
			"后台管理");

	public final String value;
	public final String desc;

	FromIdEnum(final String value, final String desc) {
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

	static Map<Object, FromIdEnum> enumMap = new HashMap<Object, FromIdEnum>();
	static {
		for (FromIdEnum type : FromIdEnum.values()) {
			enumMap.put(type.getValue(), type);
		}
	}

	public static FromIdEnum getEnum(String value) {
		return enumMap.get(value);
	}
}
