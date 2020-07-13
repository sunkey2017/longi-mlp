package com.longi.mlp.gateway.config.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import reactor.core.publisher.Mono;

public class JwtOAuth2AuthenticationToken implements Converter<Jwt, Mono<OAuth2AuthenticationToken>> {


    private Converter<Jwt, JwtOAuth2User> jwtToOAuth2UserConverter = new JwtToOAuth2UserConverter();

    @Override
    public Mono<OAuth2AuthenticationToken> convert(Jwt jwt) {
        JwtOAuth2User jwtOAuth2User = jwtToOAuth2UserConverter.convert(jwt);
        if (jwtOAuth2User != null) {
            return Mono.just(new OAuth2AuthenticationToken(jwtOAuth2User, jwtOAuth2User.getAuthorities(), (String) jwt.getClaims().get("client_id")));
        } else {
            return Mono.empty();
        }
    }
}
