package com.longi.mlp.core.exception.errorCode;

import java.io.Serializable;

public interface IErrorCodes extends Serializable {
    /**
     * 获取错误描述
     */
    String getMessage();

    /**
     * 获取错误代码
     */
    Integer getCode();
}
