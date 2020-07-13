package com.longi.mlp.core.exception.errorCode;

public enum SystemErrorCodes implements IErrorCodes {
    SYSTEM_ERROR(1, "系统错误"),
    SERVICE_ERROR(5000, "业务异常"),
    API_INVOKE_ERR(5001, "请求异常"),
    API_NOT_IMPLEMENTED(5010, "接口尚未实现"),
    INNER_SRV_INVOKE_ERROR(5002, "内部服务间调用异常"),
    EXT_SRV_INVOKE_ERROR(5001, "外部服务调用异常"),
    PARSE_RESOURCE_ERROR(5003, "解析资源异常"),
    REQUEST_PARAM_ERROR(5004, "请求参数异常:{0}"),
    UPLOAD_FILE_ERROR(5005, "上传文件出错"),
    ILLEGAL_REQUEST(5005, "不合法的请求"),
    RESOURCE_NOT_EXIST(4101, "资源已经不存在"),
    UPLOAD_FILES_ERROR(4102, "上传文件到临时目录失败"),
    COPY_FILES_ERROR(4102, "复制文件到本地目录失败"),
    API_LIMIT_ACCESS(5011, "接口访问达到上限，解决访问");

    SystemErrorCodes(Integer code, String message){
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
