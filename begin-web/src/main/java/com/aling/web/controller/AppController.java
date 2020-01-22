package com.aling.web.controller;

import com.alibaba.fastjson.JSON;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.RespConstant;
import com.aling.core.bean.ResponseParams;
import com.aling.core.util.StreamUtil;
import com.aling.web.feign.BeginFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/web")
public class AppController {

    @Autowired
    private BeginFeignClient beginFeignClient;

    @RequestMapping(value = "/begin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> begin(@RequestParam String name) throws Exception{
        log.info("web请求过来的参数是：name={}",name);
        Map<String,String> result = beginFeignClient.queryUser(name);
        return  result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,String> test(@RequestParam String name) throws Exception{
        log.info("web请求过来的参数是：name={}",name);
        Map<String,String> result = new HashMap<>();
        result.put("name",name);
        return  result;
    }

    //接口请求统一入口
    /**
     * 漳州市预约平台接口请求处理
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/appointPlatform/unifyapi", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseParams<Object> appointPlatform(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("AppController.vaccination()...");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        ResponseParams<Object> responseParams = new ResponseParams<Object>();
        String respCode = RespConstant.SUCCESS_CODE;
        String respMsg = RespConstant.SUCCESS_CODE_MS;

        responseParams.setRespCode(respCode);
        responseParams.setRespMsg(respMsg);
        try {
            inputStream = request.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            String requestMessage = StreamUtil.readInputStream(inputStreamReader);
            log.info("接收到前端请求，请求报文为：{}", requestMessage);
            RequestParams requestParams = JSON.parseObject(requestMessage, RequestParams.class);
            return beginFeignClient.appointPlatform(requestParams);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseParams;
    }


}
