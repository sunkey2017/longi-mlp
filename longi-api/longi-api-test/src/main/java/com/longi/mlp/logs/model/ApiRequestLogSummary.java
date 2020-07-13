package com.longi.mlp.logs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 资源事件日志
 *
 * @author gaofan
 */
@ApiModel(description = "资源事件日志")

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequestLogSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志条数
     */
    @ApiModelProperty(value = "日志条数")


    @JsonProperty("count")

    private Integer count;

    /**
     * 错误日志条数
     */
    @ApiModelProperty(value = "错误日志条数")


    @JsonProperty("errorCount")

    private Integer errorCount;

    /**
     * 成功日志条数
     */
    @ApiModelProperty(value = "成功日志条数")


    @JsonProperty("successCount")

    private Integer successCount;

    /**
     * Get logs
     */
    @ApiModelProperty(value = "")

    @Valid

    @JsonProperty("logs")

    @Builder.Default
    private List<ApiRequestLog> logs = null;


    public ApiRequestLogSummary addLogsItem(ApiRequestLog logsItem) {
        if (this.logs == null) {
            this.logs = new ArrayList<>();
        }
        this.logs.add(logsItem);
        return this;
    }


}

