package com.longi.mlp.gateway.repository;

import com.longi.mlp.core.config.MlpConfig;
import com.longi.mlp.core.config.service_config.GatewayConfig;
import com.longi.mlp.core.exception.errorCode.LicenseErrorCodes;
import com.longi.mlp.core.exception.license.BaseLicenseException;
import com.longi.mlp.core.exception.license.LicenseException;
import com.longi.mlp.gateway.license.LicenseVerify;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * License资源处理类，包含项目启启动时更新License、定期扫描更新License和获取License状态
 */
@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class LicenseRepository {

    private final GatewayConfig gatewayConfig;
    private Boolean licenseStatus = Boolean.TRUE;
    private MlpConfig mlpConfig;


    public Mono<Boolean> refreshLicenseStatus() {
        return Mono.defer(() -> {
            licenseStatus = this.refreshLicenseStatusTask();
            log.debug("Gateway启动时，检测到当前License状态为{}", licenseStatus);
            return Mono.justOrEmpty(licenseStatus);
        }).doOnError(BaseLicenseException.class, e -> {
            log.error("Gateway启动时," + e.getMessage(), e);
            System.exit(0);
        });
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void refreshLicenseStatusScheduled() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        licenseStatus = this.refreshLicenseStatusTask();
        log.debug("{}定时周期扫描License文件时，检测到当前License状态为{}", sdf.format(dt), licenseStatus);
    }

    private Boolean refreshLicenseStatusTask() {
        String licenseFile = gatewayConfig.getLicenseFilePath();
        if (StringUtils.isBlank(licenseFile)) {
            licenseFile = mlpConfig.getDataPath() + "/" + LicenseVerify.LICENSE_FILE_NAME;
        }
        log.debug("License文件路径为{}", licenseFile);
        try {
            LicenseVerify vlicense = new LicenseVerify();
            //生成的license文件的位置，绝对路径
            vlicense.install(licenseFile);
            vlicense.verifyLicense(licenseFile);
            licenseStatus = Boolean.TRUE;
        } catch (BaseLicenseException e) {
            licenseStatus = Boolean.FALSE;
            throw e;
        } catch (Exception e) {
            licenseStatus = Boolean.FALSE;
            throw new LicenseException(licenseFile, LicenseErrorCodes.LICENSE_ERROR, "安装并验证License是否有效时发生错误，" + e.getMessage());
        }
        return true;
    }

    /**
     * 验证License状态
     */
    public void verifyLicenseStatus() {
        new LicenseVerify().verifyLicense(mlpConfig.getDataPath() + "/" + LicenseVerify.LICENSE_FILE_NAME);
    }

    /**
     * 获取当前License状态
     */
    public Boolean geLicenseStatus() {
        return licenseStatus;
    }
}
