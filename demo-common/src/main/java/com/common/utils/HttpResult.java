package com.common.utils;

/**
 * @Date 2020/6/19 15:26
 * @name HttpResult
 */

public class HttpResult {
    private int code;
    private String result;

    public int getCode() {
        return this.code;
    }

    public String getResult() {
        return this.result;
    }

    public HttpResult(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public String toString() {
        return "HttpResult{code=" + this.code + ", result='" + this.result + '\'' + '}';
    }
}