package com.common.utils;

/**
 * @Date 2020/6/19 15:30
 * @name HttpException
 */


public class HttpException extends RuntimeException {
    public static final String MISS_TYPE = "不支持提交类型：";

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String reason, String message) {
        super(reason + message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}