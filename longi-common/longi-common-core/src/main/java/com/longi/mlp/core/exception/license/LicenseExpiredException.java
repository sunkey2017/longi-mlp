package com.longi.mlp.core.exception.license;

import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.LicenseErrorCodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * Description:Licence过期异常  Date: 2020/03/13 10:38
 *
 * @author liyi
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LicenseExpiredException extends BaseLicenseException {
    private static String exMsg = "license已过期";
    /**
     * licence 生效时间
     */
    private LocalDateTime beginDate;
    /**
     * licence 失效时间
     */
    private LocalDateTime expireDate;

    public LicenseExpiredException() {
        super(exMsg, null);
    }

    public LicenseExpiredException(String licencePath) {
        super(exMsg, licencePath);
    }

    public LicenseExpiredException(String licencePath, Throwable cause) {
        super(exMsg, cause, licencePath);
    }

    public LicenseExpiredException(String licencePath, LocalDateTime beginDate, LocalDateTime expireDate) {
        super(exMsg, licencePath);
        this.beginDate = beginDate;
        this.expireDate = expireDate;
    }

    public LicenseExpiredException(String licencePath, LocalDateTime beginDate, LocalDateTime expireDate, Throwable cause) {
        super(exMsg, cause, licencePath);
        this.beginDate = beginDate;
        this.expireDate = expireDate;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }


    @Override
    public IErrorCodes getErrorCodes() {
        return LicenseErrorCodes.LICENSE_EXPIRED;
    }
}
