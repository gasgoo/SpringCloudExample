package com.common.model;

import java.io.Serializable;

/**
 * @Author gg.rao
 * @Date 2019/4/22 11:12
 */
public class BaseResponse<T>  {
    /**
     * 处理成功表示 true成功 false失败
     */
    private boolean success;
    /**
     * 结果信息
     */
    private String msg;
    /**
     * 响应码
     */
    private String code;

    /**
     * 结果数据
     */
    private T data;

    public BaseResponse(boolean success, String code, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public BaseResponse(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public BaseResponse(String code, String msg, T data) {
        super();
        this.msg = msg;
        this.code = code;
        this.data = data;
    }


    public BaseResponse() {
        super();
    }

    /**
     * 服务执行成功
     *
     * @param data
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "0000", "请求成功", data);
    }

    /**
     * 服务执行成功
     *
     * @param code
     * @param msg
     * @return
     */
    public static BaseResponse success(String code, String msg) {
        return new BaseResponse(true, code, msg);
    }

    /**
     * 服务执行成功
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> BaseResponse<T> success(String msg, T data) {
        return new BaseResponse<>(true, "0000", msg, data);
    }

    /**
     * 服务执行成功
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <T> BaseResponse<T> success(String code, String msg, T data) {
        return new BaseResponse<>(true, code, msg, data);
    }


    /**
     * 服务执行失败
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <T> BaseResponse<T> fail(String code, String msg, T data) {
        return new BaseResponse<>(false, code, msg, data);
    }


    /**
     * 服务执行失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static BaseResponse fail(String code, String msg) {
        return new BaseResponse(false, code, msg);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
