package com.aling.common;


import com.aling.begin.kit.liquibase.LiquibaseApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@ComponentScan(basePackageClasses = {LiquibaseApplication.class})
@PropertySource({"mybatisplus.properties","jetcache.properties"})
@SpringBootApplication
@EnableDiscoveryClient
public class CommonApplication {

}
