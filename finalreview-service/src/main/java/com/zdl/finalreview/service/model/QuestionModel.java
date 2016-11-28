package com.zdl.finalreview.service.model;

import java.util.List;

/**
 * Created by zhangdongli on 16/11/28.
 * 题型
 */
public class QuestionModel {
    /**
     * 序号
     */
    private String order;

    /**
     * 类型
     */
    private int type;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题拼音集合
     */
    private List<String> titleSpells;

    /**
     * 标题拼音简写集合
     */
    private List<String> titleSimpSpells;

    /**
     * 选项集合
     */
    private List<String> options;

    /**
     * 答案集合
     */
    private List<String> anwers;

    public QuestionModel(String order, int type, String title, List<String> titleSpells, List<String> titleSimpSpells, List<String> options, List<String> anwers) {
        this.order = order;
        this.type = type;
        this.title = title;
        this.titleSpells = titleSpells;
        this.titleSimpSpells = titleSimpSpells;
        this.options = options;
        this.anwers = anwers;
    }

    public QuestionModel() {
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public List<String> getTitleSpells() {
        return titleSpells;
    }

    public void setTitleSpells(List<String> titleSpells) {
        this.titleSpells = titleSpells;
    }

    public List<String> getTitleSimpSpells() {
        return titleSimpSpells;
    }

    public void setTitleSimpSpells(List<String> titleSimpSpells) {
        this.titleSimpSpells = titleSimpSpells;
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
        return "QuestionModel:{order='" + this.order + "',type=" + this.type + ",title='" + this.title + "'}";
    }
}
