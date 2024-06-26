package com.reddit.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotNull
    private String url;

}
