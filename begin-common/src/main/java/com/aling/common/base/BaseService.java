package com.aling.common.base;

import com.alibaba.fastjson.JSON;
import com.aling.common.cache.JetCacheFactory;
import com.aling.common.cache.SysUserId2SessionInfoCache;
import com.aling.common.constant.RespConstant;
import com.aling.common.entity.User;
import com.aling.common.service.UserService;
import com.aling.core.bean.Pager;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.system.AppSessionDto;
import com.aling.core.exception.AppException;
import com.baomidou.mybatisplus.plugins.Page;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author fugl
 * @Description:
 * @date 2018年4月27日
 */
@Service
@Slf4j
public class BaseService {
    @Autowired
    private Validator validator;




    @Autowired
    private SysUserId2SessionInfoCache sessionCache;

    @Autowired
    private JetCacheFactory cacheFactory;

    @Autowired
    private UserService userService;

    public <T> ResponseParams<T> buildResponseParams(RequestParams requestParams) {
        ResponseParams<T> responseParams = new ResponseParams<T>();
        responseParams.setSignType(requestParams.getSignType());
        responseParams.setEncryptType(requestParams.getEncryptType());
        return responseParams;
    }

    public boolean needPagination(RequestParams requestParams) {
        if (requestParams.getPageParam() == null) {
            return false;
        } else {
            Pager pageParam = requestParams.getPageParam();
            return pageParam.getPageNo() != 0 && pageParam.getRows() != 0;
        }
    }

    public <T> Page<T> buildPage(RequestParams requestParams) {
        Pager pageParam = requestParams.getPageParam();
        return new Page<T>(pageParam.getPageNo(), pageParam.getRows());
    }

    public <T> void afterSelectPage(ResponseParams<?> responseParams, Page<T> page) {
        Pager pageParam = new Pager();
        pageParam.setTotal(page.getTotal());
        pageParam.setRows(page.getSize());
        pageParam.setPageNo(page.getCurrent());

        responseParams.setPageParam(pageParam);
    }

    public <T> T paramToObject(RequestParams requestParams, Class<T> clz) throws AppException {
        T t = null;
        try {
            if (requestParams.getParam() == null) {
                return clz.newInstance();
            }
            t = JSON.parseObject(JSON.toJSONString(requestParams.getParam()), clz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(RespConstant.ERR_PARAMETER_TYPE, RespConstant.ERR_PARAMETER_TYPE_MS);
        }

        Errors errors = new BeanPropertyBindingResult(t, t.getClass().getName());
        validator.validate(t, errors);

        if (errors.hasErrors()) {
            String msg = errors.getFieldErrors().get(0).getDefaultMessage();
            throw new AppException(RespConstant.ERR_VALIDATOR, msg);
        }
        return t;
    }

    public ResponseParams<Object> finish(ResponseParams<Object> response, String code, String message) {
        response.setRespCode(code);
        response.setRespMsg(message);
        return response;
    }

    public <T> ResponseParams<T> success(ResponseParams<T> responseParams) {
        responseParams.setRespCode(RespConstant.SUCCESS_CODE);
        responseParams.setRespMsg(RespConstant.SUCCESS_CODE_MS);
        return responseParams;
    }

    public <T> ResponseParams<T> success(ResponseParams<T> responseParams, String message) {
        responseParams.setRespCode(RespConstant.SUCCESS_CODE);
        if (StringUtils.isBlank(message)) {
            message = RespConstant.SUCCESS_CODE_MS;
        }
        responseParams.setRespMsg(message);
        return responseParams;
    }

    public <T> ResponseParams<T> error(ResponseParams<T> responseParams, String respCode, String respMsg) {
        responseParams.setRespCode(respCode);
        responseParams.setRespMsg(respMsg);
        if (StringUtils.isBlank(respCode)) {
            responseParams.setRespCode(RespConstant.ERROR_UNKOWN);
        }
        if (StringUtils.isBlank(respMsg)) {
            responseParams.setRespMsg(RespConstant.ERROR_UNKOWN_MS);
        }
        return responseParams;
    }

    public <T> ResponseParams<T> inValidParam(ResponseParams<T> responseParams, String respMsg) {
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

    public <T> ResponseParams<T> noRecordError(ResponseParams<T> responseParams, String respMsg) {
        responseParams.setRespCode(RespConstant.EROOR_NO_RECORD);
        if (StringUtils.isBlank(respMsg)) {
            respMsg = RespConstant.EROOR_NO_RECORD_MS;
        }
        responseParams.setRespMsg(respMsg);
        return responseParams;
    }


    public SessionInfo getSession(RequestParams requestParams) throws AppException {
        SessionInfo sessionInfo = null;
        try {
            String sessionId = requestParams.getSessionId();
            AppSessionDto dto = cacheFactory.getAppSessionCache().get(sessionId);
            sessionInfo = sessionCache.getSessionInfo(dto.getUserId());
        } catch (Exception e) {
            throw new AppException("获取SessionInfo失败！");
        }
        return sessionInfo;
    }

    public User getSysUserInfo(RequestParams requestParams) throws AppException {
        SessionInfo sessionInfo = getSession(requestParams);
        if (sessionInfo == null) {
            String warnMsg = "会话已过期,请重新登录!";
            throw new AppException(warnMsg);
        }

        String userId = sessionInfo.getUserId();
        //log.info("当前会话用户ID:{}", userId);
        User user = userService.selectById(userId);
        if (user == null) {
            String errMsg = "根据用户ID:" + userId + "查找不到用户!";
            log.error(errMsg);
            throw new AppException(errMsg);
        }

        return user;
    }

}
