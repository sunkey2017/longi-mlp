package com.longi.mlp.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@ConfigurationProperties(prefix = "mlp")
@Data
public class MlpPlatformConfig {
    private final MlpConfig config;
    private String platform = "";
    private String platformDesc = "";
    private String platformVersion = "";
    private String platformLinkman = "";
    private String platformLinkEmail = "";
    private String serviceDesc = "";
    private String service = "";
    private String serviceClientId = "";
    private String serviceSecret = "";
    private String serviceTitle = "";
    private String serviceUrl = "";
    private String serviceVersion = "";

    @Autowired
    public MlpPlatformConfig(MlpConfig config) {
        this.config = config;
    }
}
