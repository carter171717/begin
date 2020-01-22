package com.aling.service.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.aling.common.util.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 初始化相关配置
 * @author wangly
 * @date 2020年01月21日
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		/**
		 * 1.先定义一个convert转换消息的对象
		 * 2.添加fastjson的配置信息，比如：是否要格式化返回的json数据
		 * 3.在convertzhong 添加配置信息
		 * 4.将convert添加到converters当中
		 */
		//1.先定义一个convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		//2.添加fastjson的配置信息，比如：是否要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

		//处理中文乱码问题(不然出现中文乱码)
		List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);

		//3.在convertzhong 添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);

		//4.将convert添加到converters当中
		converters.add(fastConverter);

		//converters.add(responseBodyConverter());
	}

	@Bean
	public SpringUtil springContextUtil() {
		return new SpringUtil();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
	}

}
