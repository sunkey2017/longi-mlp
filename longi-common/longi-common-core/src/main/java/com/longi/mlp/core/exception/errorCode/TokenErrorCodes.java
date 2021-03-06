package com.longi.mlp.core.exception.errorCode;

public enum TokenErrorCodes implements IErrorCodes {
    TOKEN_NOT_FOUND(910001, "未传入token"),
    TOKEN_INVALID(910002, "token无效"),
    TOKEN_EXPIRED(910003, "token已经过期,%s"),
    TOKEN_EXCHANGE_ERR(910004, "token换取失败");

    private Integer code;
    private String message;

    TokenErrorCodes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
