package com.zdl.finalreview.dao.enums;

/**
 * Created by zhangdongli on 16/11/25.
 * 提醒
 */
public enum QuestionType {

    MULTIPLECHOICE("选择题", 1), TRUEORFALSE("判断题", 2), FILLINTHEBLANK("填空题", 3);

    private String text;
    private int code;

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

    private QuestionType(String text, int code) {
        this.text = text;
        this.code = code;
    }
}
