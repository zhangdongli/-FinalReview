package com.zdl.finalreview.common.strings;

import com.zdl.finalreview.common.enums.PinYinType;

/**
 * Created by zhangdongli on 16/11/25.
 * 拼音构造器
 */
public class PinYinBuilder {

    /**
     * 拼音类型
     */
    private PinYinType pinYinType;

    /**
     * 拼音组合
     */
    private String[] tokens;

    public String[] getTokens() {
        return this.tokens;
    }

    public PinYinBuilder() {
        this.pinYinType = PinYinType.FULL_PINYIN;
    }

    public PinYinBuilder(PinYinType pinYinType) {
        this.pinYinType = pinYinType;
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

        tokens = this.getFinalTokens(tokens);
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

    private String[] getFinalTokens(String[] tokens) {
        if (this.pinYinType == PinYinType.FULL_PINYIN) {
            return tokens;
        } else if (this.pinYinType == PinYinType.SIMP_PINYIN) {
            String[] ret = new String[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                ret[i] = String.valueOf(tokens[i].charAt(0));
            }
            return ret;
        }
        return null;
    }
}
