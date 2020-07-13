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
public class ApiRequestLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志编码
     */
    @ApiModelProperty(value = "日志编码")


    @JsonProperty("logId")

    private String logId;

    /**
     * API编码
     */
    @ApiModelProperty(value = "API编码")


    @JsonProperty("apiId")

    private String apiId;

    /**
     * 客户端编码
     */
    @ApiModelProperty(value = "客户端编码")


    @JsonProperty("clientId")

    private String clientId;

    /**
     * 服务编码
     */
    @ApiModelProperty(value = "服务编码")


    @JsonProperty("serviceId")

    private String serviceId;

    /**
     * 用户编码
     */
    @ApiModelProperty(value = "用户编码")


    @JsonProperty("userId")

    private String userId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")


    @JsonProperty("beginAt")

    private String beginAt;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")


    @JsonProperty("endAt")

    private String endAt;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")


    @JsonProperty("operateBy")

    private String operateBy;

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")


    @JsonProperty("successFlag")

    private Integer successFlag;

    /**
     * 输出code
     */
    @ApiModelProperty(value = "输出code")


    @JsonProperty("outputCode")

    private String outputCode;

    /**
     * 输出Message
     */
    @ApiModelProperty(value = "输出Message")


    @JsonProperty("outputMessage")

    private String outputMessage;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")


    @JsonProperty("addtionalInfo")

    private String addtionalInfo;

    /**
     * 时间差
     */
    @ApiModelProperty(value = "时间差")


    @JsonProperty("timeDiff")

    private Long timeDiff;


}

