package com.aling.web.feign;

import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "begin-service", fallback = BeginFeignClientFallback.class)
public interface BeginFeignClient {

    @RequestMapping(value = "/user/queryUser/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String,String> queryUser(@RequestParam String name) throws Exception;


    @RequestMapping(value = "/appointPlatform/unifyapi/", method = RequestMethod.POST)
    ResponseParams<Object> appointPlatform(@RequestBody(required = true) RequestParams requestParams) throws Exception;

}
