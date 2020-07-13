package com.longi.mlp.gateway.config.jwt;

import com.google.common.collect.Lists;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtToOAuth2UserConverter implements Converter<Jwt, JwtOAuth2User> {

    @Override
    public JwtOAuth2User convert(Jwt jwt) {
        return new JwtOAuth2User(toGrantedAuthorities(jwt.getClaims()), jwt.getClaims(), "user_name", jwt.getTokenValue());
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> toGrantedAuthorities(Map<String, Object> claims) {
        Collection<String> stringAuthorities = (Collection<String>) claims.get("authorities");
        if (stringAuthorities != null && stringAuthorities.size() > 0) {
            return stringAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
        }
    }
}