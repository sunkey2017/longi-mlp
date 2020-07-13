package com.longi.mlp.core.exception.token;

import com.longi.mlp.core.exception.BaseException;
import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.TokenErrorCodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Description:Token无效异常, Date: 2020/03/13 10:51
 *
 * @author liyi
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenInvalidException extends BaseException {

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(IErrorCodes errorCode, Object... params) {
        super(String.format(errorCode.getMessage(), params));
    }

    @Override
    public IErrorCodes getErrorCodes() {
        return TokenErrorCodes.TOKEN_INVALID;
    }


}
