package com.pre_order.order_service.global.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignOkHttpConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}
