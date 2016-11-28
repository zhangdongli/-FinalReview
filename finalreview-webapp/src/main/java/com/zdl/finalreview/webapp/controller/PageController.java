package com.zdl.finalreview.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangdongli on 16-11-28.
 * 绘制页面
 */
@Controller
public class PageController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getHome(){
        return "home";
    }
}
