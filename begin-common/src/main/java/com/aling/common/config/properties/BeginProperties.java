package com.aling.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "begin")
@Data
public class BeginProperties {

    /**
     * 后台登录需要检查验证码是否正确
     */
    private boolean needCheckLoginRandomCode = false;
    /**
     * 是否只能1个设备登录
     */
    private boolean enableLimitSingleTermTypeLogin = true;

    private String testName = "詹姆斯";
}
