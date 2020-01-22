package com.aling.web.feign;

import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BeginFeignClientFallback implements BeginFeignClient {
    @Override
    public Map<String, String> queryUser(@RequestParam String name) throws Exception {
        Map<String, String> map = new HashMap<String,String>();
        log.info("sorry! 网络异常，服务暂时无法访问");
        map.put("Error","sorry! 网络异常，服务暂时无法访问");
        return map;
    }

    @Override
    public ResponseParams<Object> appointPlatform(RequestParams requestParams) throws Exception {
        ResponseParams<Object> responseParams = new ResponseParams<Object>();
        Map<String, String> map = new HashMap<String,String>();
        log.error("sorry! 网络异常，服务暂时无法访问");
        responseParams.setRespCode("9999");
        responseParams.setRespMsg("网络异常，服务暂时无法访问");
        return responseParams;
    }
}
