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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequestLogDetail extends ApiRequestLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求日志
     */
    @ApiModelProperty(value = "请求日志")


    @JsonProperty("requestLog")

    private String requestLog;

    /**
     * 响应日志
     */
    @ApiModelProperty(value = "响应日志")


    @JsonProperty("responseLog")

    private String responseLog;


}

