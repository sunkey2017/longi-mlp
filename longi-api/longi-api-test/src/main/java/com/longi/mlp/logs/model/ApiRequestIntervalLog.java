package com.longi.mlp.logs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Api请求调用日志
 *
 * @author gaofan
 */
@ApiModel(description = "Api请求调用日志")

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequestIntervalLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询时间
     */
    @ApiModelProperty(value = "查询时间")


    @JsonProperty("queryTime")

    private String queryTime;

    /**
     * 调用次数
     */
    @ApiModelProperty(value = "调用次数")


    @JsonProperty("count")

    private Integer count;

    /**
     * 成功调用次数
     */
    @ApiModelProperty(value = "成功调用次数")


    @JsonProperty("successCount")

    private Integer successCount;

    /**
     * 错误调用次数
     */
    @ApiModelProperty(value = "错误调用次数")


    @JsonProperty("errorCount")

    private Integer errorCount;

    /**
     * 成功调用率
     */
    @ApiModelProperty(value = "成功调用率")


    @JsonProperty("successRate")

    private Double successRate;


}

