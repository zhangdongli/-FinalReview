package com.zdl.finalreview.common.enums;

/**
 * Created by zhangdongli on 16/11/28.
 * 异常枚举
 */
public enum ReturnCode {

    SUCCESS(200, "success"),

    PARAM_ERROR(1000, "param.error"),

    RUNTIME_ERROR(500, "runtime.error");

    private int code;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private ReturnCode(int code, String text) {
        this.text = text;
        this.code = code;
    }
}
