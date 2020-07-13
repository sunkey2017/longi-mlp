package com.longi.mlp.logs.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 模型发布相关文件类型 * HOUR:按小时查询 * DAY:按天查询 * MONTH:按月查询
 *
 * @author gaofan
 */
public enum LogQueryInterval {

    /**
     * 按小时查询
     */
    HOUR("HOUR", "按小时查询"),

    /**
     * 按天查询
     */
    DAY("DAY", "按天查询"),

    /**
     * 按月查询
     */
    MONTH("MONTH", "按月查询");

    private String value;
    private String description;

    LogQueryInterval(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static LogQueryInterval fromValue(String value) {
        for (LogQueryInterval b : LogQueryInterval.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

