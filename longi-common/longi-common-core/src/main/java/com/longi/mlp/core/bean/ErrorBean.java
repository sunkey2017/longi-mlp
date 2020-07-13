package com.longi.mlp.core.bean;

import java.util.HashMap;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class ErrorBean extends HashMap<String, String> {
    /**
     * 异常码
     */
    private String code;
    /**
     * 服务
     */
    private String service;
    /**
     * 错误信息
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

