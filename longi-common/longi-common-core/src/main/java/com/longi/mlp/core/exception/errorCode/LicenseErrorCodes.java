package com.longi.mlp.core.exception.errorCode;

public enum LicenseErrorCodes implements IErrorCodes {
    LICENSE_ERROR(900001, "License错误:%s"),
    LICENSE_EXPIRED(900002, "License已经过期"),
    LICENSE_NOT_FOUND(900003, "License文件没有找到！");

    private Integer code;
    private String message;

    LicenseErrorCodes(Integer code, String message) {
        this.code = code;
        this.message = message;
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
