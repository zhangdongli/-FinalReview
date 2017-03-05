package com.zdl.finalreview.service.test;

import com.zdl.finalreview.common.exception.FRException;
import com.zdl.finalreview.service.biz.QuestionService;
import com.zdl.finalreview.service.model.QuestionModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;

/**
 * Created by zhangdongli on 16/11/28.
 * 题型服务测试类
 */
public class QuestionServiceTest {

    private QuestionService questionService;

    @Before
    public void init() {
//        ApplicationContext act = new ClassPathXmlApplicationContext("finalreview-service-content.xml");
//        questionService = act.getBean(QuestionService.class);
    }

    @Test
    public void importQuestionsTest() {
//        File file = new File("/Users/zhangdongli/Desktop/数字图形设计复习题2.xls");
//        try {
//            questionService.importQuestions(file);
//        } catch (FRException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void findQuestionsTest() {
//        String keyword = "rgyf512";
//
//        try {
//            List<QuestionModel> questionModels = questionService.findQuestions(keyword);
//            Assert.assertNotNull(questionModels);
//
//            for (QuestionModel q : questionModels) {
//                System.out.println(q.getTitle());
//            }
//
//        } catch (FRException e) {
//            e.printStackTrace();
//        }
    }
}
