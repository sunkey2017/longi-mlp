package com.longi.mlp.gateway.exception.code;

import com.longi.mlp.core.exception.errorCode.IErrorCodes;

/**
 * 网关定义的异常编码枚举
 */
public enum GatewayErrorCodes implements IErrorCodes {
    LIMIT_API(200001,"API限流,{0}");

    private Integer code;
    private String message;

    GatewayErrorCodes(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Integer getCode() {
        return null;
    }
}
