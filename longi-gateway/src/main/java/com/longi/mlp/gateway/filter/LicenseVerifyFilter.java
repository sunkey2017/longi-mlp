package com.longi.mlp.gateway.filter;

import com.longi.mlp.gateway.repository.LicenseRepository;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * License校验过滤器
 * </BR> 查看License是否在有效期
 *
 * @author gaofan3
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LicenseVerifyFilter implements WebFilter, Ordered {

    private final LicenseRepository licenseRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        MDC.clear();
        log.debug("接入的请求地址为：{}", serverWebExchange.getRequest().getPath());
        return Mono.defer(
                () -> {
                    Boolean licenseState = licenseRepository.geLicenseStatus();
                    log.debug("api接入访问[{}]时，判断当前License状态为{}", serverWebExchange.getRequest().getPath(), licenseState);
                    if (!licenseState) {
                        licenseRepository.verifyLicenseStatus();
                    }
                    return Mono.just(licenseState);
                })
                .doOnError(Mono::error)
                .flatMap(licenseState -> webFilterChain.filter(serverWebExchange));
    }

    @Override
    public int getOrder() {
        return SecurityWebFiltersOrder.FIRST.getOrder();
    }


}
