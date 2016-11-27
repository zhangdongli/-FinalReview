package com.zdl.finalreview.common.test;

import com.zdl.finalreview.common.strings.PinYin;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhangdongli on 16/11/25.
 */
public class PinYinTest {

    @Test
    public void getStringPinYinsTest(){
        PinYin pinYin = new PinYin();
        String pingYingStr = "长滩岛";
        String[]pinyins = pinYin.getStringPinYins(pingYingStr);
        Assert.assertArrayEquals("转化出来的拼音不对",new String[]{"zhangtandao","changtandao"},pinyins);
    }
}
