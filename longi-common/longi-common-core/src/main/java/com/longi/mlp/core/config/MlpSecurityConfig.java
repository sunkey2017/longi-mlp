package com.longi.mlp.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "mlp.config.security")
public class MlpSecurityConfig {
    private String jwtSigningKey = "";
    private Long jwtExpiration = 0L;
}
