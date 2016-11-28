package com.zdl.finalreview.common.strings;

/**
 * Created by zhangdongli on 16/11/28.
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 是否是中文
     *
     * @param c 字符
     * @return 是/否
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 是否是数字
     *
     * @param c 字符
     * @return 是/否
     */
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 是否是英文
     *
     * @param c 字符
     * @return 是/否
     */
    public static boolean isEnglish(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str        字符串
     * @param isMatchAll 是否要求这个字符串全是中文
     * @return 是/否
     */
    public static boolean isChinese(String str, boolean isMatchAll) {
        if (str == null) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (StringUtil.isChinese(c)) {
                if (!isMatchAll) {
                    return true;
                }
            } else {
                if (isMatchAll) {
                    return false;
                }
            }
        }

        return isMatchAll;
    }

    /**
     * 获取数字、英文和中文
     *
     * @param str 要获取的字符串
     * @return 数字、英文和中文字符串
     */
    public static String getNumberOrEnglishOrChinese(String str) {
        str = str.trim();
        char[] strCharArray = str.toCharArray();
        char[] newStrCharArray = new char[str.length()];

        int index = 0;
        for (char newChar : strCharArray) {
            if (StringUtil.isNumber(newChar) || StringUtil.isEnglish(newChar) || StringUtil.isChinese(newChar)) {
                newStrCharArray[index] = newChar;
                index++;
            }
        }

        return new String(newStrCharArray, 0, index);
    }
}
