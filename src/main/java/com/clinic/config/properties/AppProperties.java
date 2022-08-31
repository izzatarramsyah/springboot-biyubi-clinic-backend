package com.clinic.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("app")
public class AppProperties {
    private String APP_NAME;
    private int HTTP_CLIENT_CONNECTION_TIMEOUT;
    private int HTTP_CLIENT_READ_TIMEOUT;
    private int HTTP_SOA_TIMEOUT;
    private int USER_IDLE_TIMEOUT;
}