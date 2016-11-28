package com.zdl.finalreview.webapp.model;

import java.util.List;

/**
 * Created by zhangdongli on 16/11/28.
 * 题型
 */
public class QuestionVo {

    /**
     * 类型
     */
    private int type;

    /**
     * 标题
     */
    private String title;

    /**
     * 选项集合
     */
    private List<String> options;

    /**
     * 答案集合
     */
    private List<String> anwers;

    public QuestionVo(int type, String title, List<String> options, List<String> anwers) {
        this.type = type;
        this.title = title;
        this.options = options;
        this.anwers = anwers;
    }

    public QuestionVo() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getAnwers() {
        return anwers;
    }

    public void setAnwers(List<String> anwers) {
        this.anwers = anwers;
    }


    @Override
    public String toString() {
        return "QuestionVo:{type=" + this.type + ",title='" + this.title + "'}";
    }
}
