package com.longi.mlp.core.exception.license;

import com.longi.mlp.core.exception.BaseException;

/**
 * Description: licence异常 Date: 2020/03/15 11:27
 *
 * @author liyi
 */
public abstract class BaseLicenseException extends BaseException {
    /**
     * licence文件路径
     */
    private String licencePath;

    public BaseLicenseException(String message, String licencePath) {
        super(message);
        this.licencePath = licencePath;
    }

    public BaseLicenseException(String message, Throwable cause, String licencePath) {
        super(message, cause);
        this.licencePath = licencePath;
    }

    public String getLicencePath() {
        return licencePath;
    }

    public void setLicencePath(String licencePath) {
        this.licencePath = licencePath;
    }
}
