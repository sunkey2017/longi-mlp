package com.longi.mlp.gateway.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenUtil {

    /**
     * 验证token是否过期
     *
     * @param expSecondTime token过期时间（秒级）
     */
    public static boolean validateJwtExpire(Long expSecondTime) {
        Long currentTime = Instant.now().getEpochSecond();
        log.debug("Current Second Date:{}", convertSecondToDateStr(currentTime));
        log.debug("Token Expire Date:{}", convertSecondToDateStr(expSecondTime));
        if (currentTime > expSecondTime) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static String convertSecondToDateStr(Long secondTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(secondTime * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
