package com.longi.mlp.gateway.exception;

import com.google.common.collect.ImmutableMap;

import com.longi.mlp.core.exception.BaseException;
import com.longi.mlp.core.exception.errorCode.IErrorCodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

import javax.validation.constraints.NotNull;

import cn.hutool.core.util.StrUtil;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ApiLimitAccessException extends BaseException {

    private static String exMsg = "请求拒绝访问: %s";

    public ApiLimitAccessException(@NotNull IErrorCodes iErrorCodes, Object... params) {
        super(String.format(exMsg, StrUtil.indexedFormat(iErrorCodes.getMessage(), params)));
    }

    public ApiLimitAccessException(@NotNull IErrorCodes iErrorCodes, Throwable cause, Object... params) {
        super(String.format(exMsg, StrUtil.indexedFormat(iErrorCodes.getMessage(), params)), cause);
    }


    @Override
    public IErrorCodes getErrorCodes() {
        return null;
    }
}
