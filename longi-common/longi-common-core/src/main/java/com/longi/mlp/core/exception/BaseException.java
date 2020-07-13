package com.longi.mlp.core.exception;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.longi.mlp.core.exception.errorCode.IErrorCodes;

import cn.hutool.json.JSONUtil;

public abstract class BaseException extends RuntimeException{
    public abstract IErrorCodes getErrorCodes();

    public BaseException(String message){
        super(message);
    }

    public BaseException(IErrorCode errorCode){
        super(errorCode.getMsg());
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
