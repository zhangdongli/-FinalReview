package com.zdl.finalreview.common.strings;

import java.util.Date;

/**
 * Created by zhangdongli on 16/11/25.
 * 日志构造器
 */
public class LogBuilder {
    private StringBuilder stringBuilder;
    private Date date;

    public static LogBuilder getBuilder() {
        return new LogBuilder();
    }

    private LogBuilder() {
        this.stringBuilder = new StringBuilder();
        this.date = new Date();
    }

    public LogBuilder append(String info) {
        this.stringBuilder.append(info);
        return this;
    }

    public LogBuilder append(Object info) {
        this.stringBuilder.append(info);
        return this;
    }

    public LogBuilder append(double info) {
        this.stringBuilder.append(info);
        return this;
    }

    public LogBuilder append(int info) {
        this.stringBuilder.append(info);
        return this;
    }

    public LogBuilder append(long info) {
        this.stringBuilder.append(info);
        return this;
    }

    public LogBuilder append(boolean info) {
        this.stringBuilder.append(info);
        return this;
    }

    @Override
    public String toString() {
        double diff = new Date().getTime() - this.date.getTime();
        this.stringBuilder.append("[用时：").append(diff / 1000).append("秒]");
        return this.stringBuilder.toString();
    }
}
