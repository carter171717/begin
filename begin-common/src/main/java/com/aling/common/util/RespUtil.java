package com.aling.common.util;

import com.aling.common.constant.RespConstant;
import com.aling.core.bean.ResponseParams;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author wangly
 * @Description:
 * @date 2020年1月21日
 */
@Component
@Slf4j
public class RespUtil {
    public static <T> ResponseParams<T> success(ResponseParams<T> responseParams) {
        responseParams.setRespCode(RespConstant.SUCCESS_CODE);
        responseParams.setRespMsg(RespConstant.SUCCESS_CODE_MS);
        return responseParams;
    }

    public static <T> ResponseParams<T> error(ResponseParams<T> responseParams, String respCode, String respMsg) {
        responseParams.setRespCode(RespConstant.ERROR_UNKOWN);
        responseParams.setRespMsg(RespConstant.ERROR_UNKOWN_MS);
        if (StringUtils.isBlank(respCode)) {
            responseParams.setRespMsg(respCode);
        }
        if (StringUtils.isBlank(respMsg)) {
            responseParams.setRespMsg(respMsg);
        }
        return responseParams;
    }

    public static <T> ResponseParams<T> inValidParam(ResponseParams<T> responseParams, String respMsg) {
        responseParams.setRespCode(RespConstant.ERROR_IN_VALID_PARAM);
        if (StringUtils.isBlank(respMsg)) {
            respMsg = RespConstant.ERROR_IN_VALID_PARAM_MS;
        }
        responseParams.setRespMsg(respMsg);
        return responseParams;
    }

    public static <T> ResponseParams<T> noParamError(ResponseParams<T> responseParams, String respMsg) {
        responseParams.setRespCode(RespConstant.ERROR_NO_PARAM);
        if (StringUtils.isBlank(respMsg)) {
            respMsg = RespConstant.ERROR_NO_PARAM_MS;
        }
        responseParams.setRespMsg(respMsg);
        return responseParams;
    }

    public static <T> ResponseParams<T> noRecordError(ResponseParams<T> responseParams, String respMsg) {
        responseParams.setRespCode(RespConstant.EROOR_NO_RECORD);
        if (StringUtils.isBlank(respMsg)) {
            respMsg = RespConstant.EROOR_NO_RECORD_MS;
        }
        responseParams.setRespMsg(respMsg);
        return responseParams;
    }

}
