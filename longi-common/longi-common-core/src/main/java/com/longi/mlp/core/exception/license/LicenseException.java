package com.longi.mlp.core.exception.license;

import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.LicenseErrorCodes;

public class LicenseException extends BaseLicenseException {

    public LicenseException(String licencePath, Throwable cause) {
        super(cause.getMessage(), licencePath);
    }

    public LicenseException(String licencePath, IErrorCodes code, Object... params) {
        super(String.format(code.getMessage(), params), licencePath);
    }

    public LicenseException(String licencePath, Throwable cause, IErrorCodes code, Object... params) {
        super(String.format(code.getMessage(), params), cause, licencePath);
    }

    @Override
    public IErrorCodes getErrorCodes() {
        return LicenseErrorCodes.LICENSE_NOT_FOUND;
    }
}
