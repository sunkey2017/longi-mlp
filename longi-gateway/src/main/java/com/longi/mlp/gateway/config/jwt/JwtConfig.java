package com.longi.mlp.gateway.config.jwt;

import com.google.common.collect.Maps;

import com.longi.mlp.core.config.MlpConfig;
import com.longi.mlp.core.exception.errorCode.TokenErrorCodes;
import com.longi.mlp.core.exception.token.TokenExpireException;
import com.longi.mlp.gateway.util.JwtTokenUtil;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import java.time.Instant;
import java.util.Map;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtConfig {
    private final MlpConfig mlpConfig;

    /**
     * 验证Jwt Token是否失效
     * @return
     */
    @SuppressWarnings("unchecked")
    @Bean
    ReactiveJwtDecoder reactiveJwtDecoder() {
        return token -> Mono.fromSupplier(() -> {
            MacSigner signingKey = new MacSigner(mlpConfig.getSecurity().getJwtSigningKey());
            org.springframework.security.jwt.Jwt jwt = JwtHelper.decodeAndVerify(token, signingKey);
            Map<String, Object> headerMap = Maps.newHashMap(JwtHelper.headers(token));
            Map<String, Object> claimMap = JSONUtil.toBean(jwt.getClaims(), Map.class);
            if (!claimMap.containsKey("exp") || claimMap.get("exp") == null
                    || StringUtils.isBlank(String.valueOf(claimMap.get("exp")))) {
                throw new RuntimeException("当前Token没有设置失效时间，为无效Token!");
            }
            Boolean isExpireToken = JwtTokenUtil.validateJwtExpire(Long.parseLong(String.valueOf(claimMap.get("exp"))));
            if (!isExpireToken) {
                throw new TokenExpireException(TokenErrorCodes.TOKEN_EXPIRED,
                        "当前Token已过期，过期时间为"+ JwtTokenUtil.convertSecondToDateStr(Long.parseLong(String.valueOf(claimMap.get("exp")))) +
                        "，请及时更新!");
            }
            return new Jwt(token, Instant.now(),
                    Instant.ofEpochSecond(Long.parseLong(String.valueOf(claimMap.get("exp")))), headerMap, claimMap);
        }).doOnError(throwable -> {
            throw new TokenExpireException("Invalid Jwt Token:" + throwable.getMessage());
        });

    }
}
