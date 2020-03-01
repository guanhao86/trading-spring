package com.spring.free.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig
 *
 * @author liqiguang
 * @create 2017-09-12
 **/
@Configuration
public class RestTemplateConfig {
    @Value("${rest.ReadTimeout}")
    private int readTimeout;
    @Value("${rest.ConnectTimeout}")
    private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectionTimeout);
        factory.setChunkSize(409600);
        return factory;
    }
}
