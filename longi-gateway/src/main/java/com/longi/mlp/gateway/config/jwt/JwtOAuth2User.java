package com.longi.mlp.gateway.config.jwt;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import com.longi.mlp.core.constant.MlpConstant;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

import lombok.Getter;

@Getter
public class JwtOAuth2User extends DefaultOAuth2User {
    private final String jwtTokenValue;
    private String userId;
    private String clientAppId;
    private String tenantId;

    public JwtOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String jwtTokenValue) {
        super(authorities, attributes, nameAttributeKey);
        this.userId = String.valueOf(attributes.get(nameAttributeKey));
        this.tenantId = String.valueOf(attributes.get("tenant_id"));
        this.clientAppId = String.valueOf(attributes.get("client_id"));
        this.jwtTokenValue = jwtTokenValue;
    }


    private JwtOAuth2User(String userId) {
        super(Lists.newArrayList(new SimpleGrantedAuthority("TEMP")), ImmutableMap.of("user_name", userId), "user_name");
        this.userId = userId;
        this.tenantId = this.userId;
        this.clientAppId = this.userId;
        this.jwtTokenValue = "";
    }

    public static JwtOAuth2User buildAnonJwtTokenUser() {
        return new JwtOAuth2User(MlpConstant.ANONYMOUS_USER);
    }

}