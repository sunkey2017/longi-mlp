package com.longi.mlp.gateway.license;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseManagerHolder {
    private static LicenseManager licenseManager;

    private LicenseManagerHolder() {
    }

    public static synchronized LicenseManager getLicenseManager(LicenseParam param) {
        if (licenseManager == null) {
            licenseManager = new LicenseManager(param);
        }
        return licenseManager;
    }
}
