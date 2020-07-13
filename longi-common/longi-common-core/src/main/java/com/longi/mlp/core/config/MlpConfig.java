package com.longi.mlp.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "mlp.config")
public class MlpConfig {
    /**
     * 安全相关配置
     */
    private final MlpSecurityConfig security;
}
