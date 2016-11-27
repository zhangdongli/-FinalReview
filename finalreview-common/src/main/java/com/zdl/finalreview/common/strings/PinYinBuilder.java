package com.zdl.finalreview.common.strings;

/**
 * Created by zhangdongli on 16/11/25.
 * 拼音构造器
 */
public class PinYinBuilder {
    /**
     * 拼音组合
     */
    private String[] tokens;


    /**
     * 获取拼音组合
     *
     * @return 拼音组合
     */
    public String[] getTokens() {
        return this.tokens;
    }

    /**
     * 追加拼音组合
     *
     * @param tokens 拼音组合
     * @return 成功/失败
     */
    public boolean appendTokens(String[] tokens) {
        if (null == tokens || tokens.length <= 0) {
            return false;
        }

        if (null == this.tokens || this.tokens.length == 0) {
            this.tokens = tokens;
            return true;
        }

        String[] tmpTokens = new String[this.tokens.length * tokens.length];
        for (int i = 0; i < this.tokens.length; i++) {
            for (int j = 0; j < tokens.length; j++) {
                tmpTokens[i * tokens.length + j] = this.tokens[i] + tokens[j];
            }
        }
        this.tokens = tmpTokens;
        return true;
    }
}
