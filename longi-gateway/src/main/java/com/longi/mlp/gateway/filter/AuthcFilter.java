package com.longi.mlp.gateway.filter;

import com.google.common.collect.Lists;

import com.longi.mlp.core.exception.errorCode.TokenErrorCodes;
import com.longi.mlp.core.exception.token.TokenInvalidException;
import com.longi.mlp.gateway.contants.GatewayConstant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * JwtToken验证
 *
 */
@Slf4j
public class AuthcFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String authorization = getToken(request);
        if (StringUtils.isNotBlank(authorization) &&
                authorization.toLowerCase().startsWith(GatewayConstant.AUTHORIZATION_TOKEN_TYPE.toLowerCase())) {
            String token = authorization.substring(GatewayConstant.AUTHORIZATION_TOKEN_TYPE.length()).trim();
            if (isJWTToken(token)) {
                return webFilterChain.filter(serverWebExchange.mutate()
                        .request(request.mutate()
                                .headers(httpHeaders -> httpHeaders.remove(HttpHeaders.AUTHORIZATION))
                                .header(HttpHeaders.AUTHORIZATION, authorization)
                                .build()).build());
            } else {
                return Mono.error(inValidTokenException(token));
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private boolean isJWTToken(String token) {
        return StringUtils.countMatches(token, ".") == 2;
    }

    private TokenInvalidException inValidTokenException(String token) {
        return new TokenInvalidException(TokenErrorCodes.TOKEN_INVALID, "获取到的Token不是Jwt Token！token:" + token);
    }

    private String getToken(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            MultiValueMap<String, String> paramMap = request.getQueryParams();
            if (paramMap.containsKey("token")) {
                authorization = paramMap.getFirst("token");
            }
        }
        log.trace("获取到的Token信息为{}", authorization);
        return authorization;
    }
}
