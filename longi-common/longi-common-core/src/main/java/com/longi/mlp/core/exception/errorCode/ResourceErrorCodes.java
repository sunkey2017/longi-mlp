package com.longi.mlp.core.exception.errorCode;

public enum ResourceErrorCodes implements IErrorCodes{

    RESOURCE_REPEAT_CONFLICT(400001, "资源重复冲突"),
    RESOURCE_STATUS_CONFLICT(400002, "资源状态冲突，不运行此操作"),
    RESOURCE_NOT_FOUND(400003, "资源不存在"),
    RESOURCE_NOT_ENOUGH(400004, "资源不够申请"),
    RESOURCE_OCCUPIED(400005, "资源已被占用");

    ResourceErrorCodes(Integer code, String message){
        this.code=code;
        this.message=message;
    }
    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Integer getCode() {
        return null;
    }
}
