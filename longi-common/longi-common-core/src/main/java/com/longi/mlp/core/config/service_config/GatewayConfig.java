package com.longi.mlp.core.config.service_config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: 网关服务属性配置
 *
 * @author liyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@ConfigurationProperties(prefix = "mlp.config.services.gateway")
public class GatewayConfig extends BaseServiceConfig {
    private String loginPermitServer;
    private String loginTokenServer;
    private String webLoginToken;
    private String permitApiService;
    private String licenseFilePath = null;
    // 默认的限流配置
    private Map<String, String> defaultLimiterConfig;
    private String cookieType;

    private String anonymousUserToken;

    private Long apiAccessTimeLimit;
    private List<String> apiSignVerifyClient;

    /**
     * 请求限制处理时长，用来控制日志级别
     */
    private Long requestLimitMin;

    /**
     * 授权Path列表
     */
    private String[] permitPathMatchers;

    public String getHostAddr() {
        String url;
        if (StringUtils.isBlank(this.getServiceUrl())) {
            url = this.getServiceAddr();
        } else {
            url = this.getServiceUrl();
        }
        return url;
    }

    public String getUrl() {
        String url;
        if (StringUtils.isBlank(this.getServiceUrl())) {
            url = this.getServiceAddr();
        } else {
            url = this.getServiceUrl();
        }
        if (StringUtils.isNotBlank(this.getContextPath())) {
            url = StringUtils.removeEnd(url, "/") + "/" + StringUtils.removeStart(this.getContextPath(), "/");
        }
        return "http://" + url;
    }
}
