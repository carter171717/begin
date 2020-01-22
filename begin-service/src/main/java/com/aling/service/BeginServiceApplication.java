package com.aling.service;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.aling.common.CommonApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {CommonApplication.class, BeginServiceApplication.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableMethodCache(basePackages = "com.aling.service")
@EnableCreateCacheAnnotation
public class BeginServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(BeginServiceApplication.class, args);

    }

}
