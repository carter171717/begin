package com.aling.core.dto.log;

import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import lombok.Data;

import java.util.Map;

@Data
public class OperationLogDTO {
	private RequestParams reqParam;

	@SuppressWarnings("rawtypes")
	private ResponseParams resParam;

	private Map<String, String> map;

	private String moduleCode = "APPOINTMENT";//模块代码

	private String moduleName = "预约平台";//模块名称

}
