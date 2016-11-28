package com.zdl.finalreview.common.strings;

import com.zdl.finalreview.common.enums.PinYinType;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdongli on 16/11/25.
 * 拼音处理器
 */
public class PinYin {

    /**
     * 拼音格式
     */
    private HanyuPinyinOutputFormat format = null;


    public PinYin() {
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
    }

    /**
     * 转换中文字符串成拼音字符串
     * 注意，因为多音字的存在，会返回多个拼音字符串
     *
     * @param str 汉字字符串
     * @return 拼音字符串集合
     */
    public String[] getStringPinYins(String str) {
        return this.getPinYins(str, PinYinType.FULL_PINYIN);
    }

    /**
     * 转换中文字符串成拼音简写字符串
     * 注意，因为多音字的存在，会返回多个拼音简写字符串
     *
     * @param str 汉字字符串
     * @return 拼音简写字符串集合
     */
    public String[] getStringSimpPinYins(String str) {
        return this.getPinYins(str, PinYinType.SIMP_PINYIN);
    }

    /**
     * 转换中文字符串成拼音字符串
     *
     * @param str        汉字字符串
     * @param pinYinType 拼音类型
     * @return 拼音字符串集合
     */
    private String[] getPinYins(String str, PinYinType pinYinType) {
        if (null == str || str.trim().length() == 0) {
            return new String[0];
        }

        str = str.trim();

        String lastTmpPinYin = null;
        List<String> pinyins = null;
        String[] tempPinyins = null;
        PinYinBuilder builder = new PinYinBuilder(pinYinType);
        for (int i = 0; i < str.length(); ++i) {
            lastTmpPinYin = "";
            pinyins = new ArrayList<>();
            tempPinyins = getCharacterPinYin(str.charAt(i));
            if (tempPinyins == null) {
                //如果str.charAt(i)非汉字，则保持原样
                pinyins.add(new String(new char[]{str.charAt(i)}));
            } else {
                for (String tempPinyin : tempPinyins) {
                    if (!lastTmpPinYin.equals(tempPinyin)) {
                        pinyins.add(tempPinyin);
                        lastTmpPinYin = tempPinyin;
                    }
                }
            }
            builder.appendTokens(pinyins.toArray(new String[]{}));
        }

        return builder.getTokens();
    }

    /**
     * 转换单个字符
     *
     * @param c 单个汉字
     * @return 拼音数组
     */
    private String[] getCharacterPinYin(char c) {
        String[] pinyin = null;

        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
        }

        //如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null) return null;

        return new String[]{pinyin[0]};
    }
}
