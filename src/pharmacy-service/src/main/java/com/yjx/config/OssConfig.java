package com.yjx.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OssConfig {

    @Value("${oss.accessKey}")
    private String accessKey;

    @Value("${oss.secretKey}")
    private String secretKey;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.host}")
    private String host;

    // 这里可以添加其他 Bean 或配置信息
    @Bean
    public void printOssConfig() {
        System.out.println("Access Key: " + accessKey);
        System.out.println("Secret Key: " + secretKey);
        System.out.println("Endpoint: " + endpoint);
        System.out.println("Host: " + host);
    }
}