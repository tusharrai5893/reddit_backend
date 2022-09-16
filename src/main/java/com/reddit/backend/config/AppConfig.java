package com.reddit.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotNull
    private String Url;


    public void setAppUrl(String appUrl) {
        this.Url = appUrl;
    }

    public String getAppUrl() {
        return Url;
    }
}
