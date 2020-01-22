package com.aling.web;

import com.aling.web.feign.BeginFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;


@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = { BeginFeignClient.class })
@PropertySource("feign.properties")
public class BeginWebApplication {
    public static void main(String[] args) {
        log.info("**********WebApplication BEGIN....**********");

        SpringApplication.run(BeginWebApplication.class, args);

        log.info("**********WebApplication END....**********");
    }
}
