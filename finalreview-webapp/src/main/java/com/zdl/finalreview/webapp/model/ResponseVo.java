package com.zdl.finalreview.webapp.model;

/**
 * Created by zhangdongli on 16/11/28.
 * 响应体
 */
public class ResponseVo {

    private int code = 0;

    private String message;

    private Object data;

    public ResponseVo(int code, Object data) {
        this.code = code;
        this.message = null;
        this.data = data;
    }

    public ResponseVo(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseVo(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
