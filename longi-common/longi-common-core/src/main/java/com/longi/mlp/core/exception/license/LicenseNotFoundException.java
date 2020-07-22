package com.longi.mlp.core.exception.license;

import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.LicenseErrorCodes;

/**
 * Description:licence文件未找到 Date: 2020/03/15 11:15
 *
 * @author liyi
 */
public class LicenseNotFoundException extends BaseLicenseException {
    private static String exMsg = "license文件未找到";


    public LicenseNotFoundException() {
        super(exMsg, null);
    }

    public LicenseNotFoundException(String licencePath) {
        super(exMsg, licencePath);
    }

    public LicenseNotFoundException(String licencePath, Throwable cause) {
        super(exMsg, cause, licencePath);
    }

    @Override
    public IErrorCodes getErrorCodes() {
        return LicenseErrorCodes.LICENSE_NOT_FOUND;
    }
}
