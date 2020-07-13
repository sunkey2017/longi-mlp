package com.longi.mlp.core.bean;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.longi.mlp.core.exception.BaseException;
import com.longi.mlp.core.exception.errorCode.IErrorCodes;
import com.longi.mlp.core.exception.errorCode.SystemErrorCodes;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.Map;

import javax.validation.constraints.NotNull;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: Restful API请求响应结果
 */
@ApiModel(description = "Rest请求响应返回模型", value = "ResponseWrap")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrap<T> implements Serializable {

    public static final String SUCCESS_MSG = "处理成功";
    public static final String CODE_FORMAT = "%06d";
    public static final String SUCCESS_CODE = String.format(CODE_FORMAT, 0);

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;

    @ApiModelProperty(value = "处理结果摘要信息")
    private String message;

    @ApiModelProperty(value = "处理结果详细信息")
    private Instant timestamp;

    @ApiModelProperty(value = "处理结果")
    private T data;

    @ApiModelProperty(value = "处理错误信息")
    private LinkedList<ErrorBean> errors;

    private ResponseWrap() {

    }

    private ResponseWrap(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    private ResponseWrap(@NotNull IErrorCodes errorCodes, Object... params) {
        this.code = String.format(CODE_FORMAT, errorCodes.getCode());
        this.message = StrUtil.indexedFormat(errorCodes.getMessage(), params);
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    private ResponseWrap(@NotNull IErrorCodes errorCodes) {
        this(errorCodes, (Object[]) null);
    }

    private ResponseWrap(@NotNull T data, @NotNull IErrorCodes errorCodes, Object... params) {
        this(errorCodes, data, params);
        this.data = data;
    }

    private ResponseWrap(@NotNull T data, @NotNull IErrorCodes errorCodes) {
        this(errorCodes, data, null);
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @return Result
     */
    public static <T> ResponseWrap<T> success(T data) {
        return new ResponseWrap<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static <T> ResponseWrap<T> success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static <T> ResponseWrap<T> error() {
        return new ResponseWrap<>(SystemErrorCodes.SYSTEM_ERROR);
    }

    public static <T> ResponseWrap<T> error(IErrorCodes errorCodes) {
        return new ResponseWrap<>(errorCodes);
    }

    public static <T> ResponseWrap<T> error(IErrorCodes errorCodes, Object... params) {
        return new ResponseWrap<>(errorCodes, params);
    }

    public static <T> ResponseWrap<T> error(T data, IErrorCodes errorCodes) {
        return new ResponseWrap<>(data, errorCodes);
    }

    public static <T> ResponseWrap<T> error(T data, IErrorCodes errorCodes, Object... params) {
        return new ResponseWrap<>(data, errorCodes, params);
    }

    public static <T> ResponseWrap<T> error(T data, BaseException ex) {
        if (ex != null) {
            String code = String.format(CODE_FORMAT,
                    ex.getErrorCodes() != null ? ex.getErrorCodes().getCode() : SystemErrorCodes.SYSTEM_ERROR.getCode());
            return new ResponseWrap<>(code, ex.getMessage(), data);
        }
        return ResponseWrap.error(data, SystemErrorCodes.SYSTEM_ERROR);
    }

    public static <T> ResponseWrap<T> error(BaseException ex) {
        return ResponseWrap.error(null, ex);
    }

    public static <T> ResponseWrap<T> error(T data) {
        return ResponseWrap.error(data, SystemErrorCodes.SYSTEM_ERROR);
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 添加错误信息对象
     */
    public void addError(ErrorBean errorBean) {
        if (CollectionUtils.isEmpty(this.errors)) {
            this.errors = Lists.newLinkedList();
        }
        this.errors.addFirst(errorBean);
    }

    /**
     * 请求是否成功
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    /**
     * 请求是否出错
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }
}