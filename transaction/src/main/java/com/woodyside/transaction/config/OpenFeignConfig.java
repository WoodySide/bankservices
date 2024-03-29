package com.woodyside.transaction.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
     basePackages = "com.woodyside.services"
)
public class OpenFeignConfig {
}
