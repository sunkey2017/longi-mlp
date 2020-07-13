package com.longi.mlp.core.exception.token;

import com.longi.mlp.core.exception.BaseException;
import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.TokenErrorCodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpireException extends BaseException {

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(IErrorCodes errorCode, Object... params) {
        super(String.format(errorCode.getMessage(), params));
    }

    @Override
    public IErrorCodes getErrorCodes() {
        return TokenErrorCodes.TOKEN_EXPIRED;
    }

}
