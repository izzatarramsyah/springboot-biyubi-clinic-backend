package com.clinic.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.clinic.config.properties.AppProperties;
import com.clinic.config.variable.ApplicationConstant;

@Configuration
public class RestTemplateConfig {
    @Autowired
    @Qualifier(ApplicationConstant.BEAN_APP_CONFIG)
    private AppProperties appProperties;

    @Bean(ApplicationConstant.BEAN_REST_TEMPLATE)
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(appProperties.getHTTP_CLIENT_CONNECTION_TIMEOUT());
        requestFactory.setReadTimeout(appProperties.getHTTP_CLIENT_READ_TIMEOUT());
        return new RestTemplate(requestFactory);
    }
}