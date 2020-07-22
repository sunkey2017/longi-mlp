package com.longi.mlp.gateway.license;

import com.longi.mlp.core.exception.license.LicenseException;
import com.longi.mlp.core.exception.license.LicenseExpiredException;
import com.longi.mlp.core.exception.license.LicenseNotFoundException;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.prefs.Preferences;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LicenseVerify {
    /**
     * 证书名
     */
    public static final String LICENSE_FILE_NAME = "license.lic";
    private final static int HOURS_IN_DAY = 24;
    private final static int MINUTES_IN_HOUR = 60;
    private final static int MILLISECONDS_IN_SECOND = 1000;
    private final static int SECONDS_IN_MINUTE = 60;
    /**
     * 公钥别名
     */
    private static String pubAlias = "PublicCert";
    /**
     * 该密码是在使用keytool生成密钥对时设置的密钥库的访问密码
     */
    private static String keyStorePwd = "mlp123";
    /**
     * 系统的统一识别码
     */
    private static String onlykey = "mlp";
    /**
     * 公钥库路径
     */
    private static String pubPath = "classpath:PublicCerts.store";

    private LicenseManager licenseManager;

    public LicenseVerify() {
        this.licenseManager = LicenseManagerHolder.getLicenseManager(initLicenseParams());
    }

    private LicenseParam initLicenseParams() {
        Class<LicenseVerify> clazz = LicenseVerify.class;
        Preferences pre = Preferences.userNodeForPackage(clazz);
        CipherParam cipherParam = new DefaultCipherParam(keyStorePwd);
        KeyStoreParam pubStoreParam = new DefaultKeyStoreParamExt(clazz, pubPath, pubAlias, keyStorePwd, null);
        return new DefaultLicenseParam(onlykey, pre, pubStoreParam, cipherParam);
    }

    /**
     * 安装License文件
     *
     * @param licenseFile License文件路径
     */
    public void install(String licenseFile) {
        try {
            if (!FileUtil.exist(licenseFile)) {
                throw new LicenseNotFoundException(licenseFile);
            }
            File file = new File(licenseFile);
            licenseManager.install(file);
            log.debug("安装License证书成功！");
        } catch (LicenseNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new LicenseException(licenseFile, new Exception("安装License时发生错误，" + e.getMessage()));
        }
    }

    /**
     * 验证License是否有效
     */
    public void verifyLicense(String licenseFile) {
        try {
            LicenseContent context = licenseManager.verify();
            Date afterDate = context.getNotAfter();
            long days = calculateExpiredTime(afterDate);
            if (days == -1) {
                throw new LicenseExpiredException(licenseFile,
                        LocalDateTime.ofInstant(context.getNotBefore().toInstant(), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(afterDate.toInstant(), ZoneId.systemDefault()));
            }
        } catch (LicenseExpiredException e) {
            throw e;
        } catch (Exception e) {
            throw new LicenseException(licenseFile, new Exception("验证License是否有效时发生错误，" + e.getMessage()));
        }
    }

    private long calculateExpiredTime(Date licenseExpiredDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.trace("License到期时间为{}", dateFormat.format(licenseExpiredDate));
        long nowTime = System.currentTimeMillis();
        return licenseExpiredDate.getTime() > nowTime ?
                (licenseExpiredDate.getTime() - System.currentTimeMillis()) / (MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY)
                : -1;
    }

//    public long diffLicenseValidateDay(LicenseContent context) {
//        Date afterDate = context.getNotAfter();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        log.trace("License到期时间为{}", dateFormat.format(afterDate));
//        Long nowTime = System.currentTimeMillis();
//        return afterDate.getTime() > nowTime ?
//                (afterDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
//                : -1;
//    }
}

class DefaultKeyStoreParamExt extends DefaultKeyStoreParam {
    DefaultKeyStoreParamExt(Class var1, String var2, String var3, String var4, String var5) {
        super(var1, var2, var3, var4, var5);
    }

    public InputStream getStream() throws IOException {
        ClassPathResource resource = new ClassPathResource("PublicCerts.store");
        return resource.getInputStream();
    }
}
