package com.longi.mlp.core.config.service_config;


import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * Description: 服务基础属性配置
 *
 * @author liyi
 */
@Data
public class BaseServiceConfig {
    /**
     * 服务地址（ip/host:port）
     */
    private String serviceAddr;
    /**
     * 服务路由地址（域名）
     */
    private String serviceUrl;
    /**
     * 网关设置的服务的 basePath
     */
    private String gatewayBasePath;
    /**
     * 是否接入网关
     */
    private boolean gatewayAccess;
    /**
     * true serviceUrl false serviceAddr
     */
    private boolean isRouteUrl;
    /**
     * 服务上下文路径
     */
    private String contextPath;
    /**
     * 服务分配的客户端clientId
     */
    private String clientId;
    /**
     * 服务
     */
    private String secret;

    String buildApiUrl(GatewayConfig gatewayConfig, String apiUrl) {
        String url;
        if (this.gatewayAccess) {
            url = StringUtils.removeEnd(this.isRouteUrl ? gatewayConfig.getServiceUrl() : gatewayConfig.getServiceAddr(), "/");
            String ctxPath = StringUtils.removeStart(gatewayConfig.getContextPath(), "/");
            if (StringUtils.isNotBlank(ctxPath)) {
                url = url + "/" + ctxPath;
            }
            String basePath = StringUtils.removeStart(this.getGatewayBasePath(), "/");
            if (StringUtils.isNotBlank(basePath)) {
                url = url + "/" + basePath;
            }
        } else {
            url = StringUtils.removeEnd(this.isRouteUrl ? this.getServiceUrl() : this.getServiceAddr(), "/");
            String ctxPath = StringUtils.removeStart(this.getContextPath(), "/");
            if (StringUtils.isNotBlank(ctxPath)) {
                url = url + "/" + ctxPath;
            }
        }
        if (StringUtils.isNotBlank(apiUrl)) {
            url = url + "/" + StringUtils.removeStart(apiUrl, "/");
        }
        return "http://" + url.replace("//", "/");
    }
}
