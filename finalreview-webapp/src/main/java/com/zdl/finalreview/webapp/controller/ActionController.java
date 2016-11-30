package com.zdl.finalreview.webapp.controller;

import com.google.common.collect.Lists;
import com.zdl.finalreview.common.enums.ReturnCode;
import com.zdl.finalreview.common.exception.FRException;
import com.zdl.finalreview.service.biz.QuestionService;
import com.zdl.finalreview.service.model.QuestionModel;
import com.zdl.finalreview.webapp.model.QuestionVo;
import com.zdl.finalreview.webapp.model.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;


/**
 * Created by zhangdongli on 16/11/28.
 * 后台动作
 */
@RestController
@RequestMapping("action")
public class ActionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "question.html")
    public ResponseVo findQuestions(String keyword) {
        try {
            List<QuestionModel> questions = questionService.findQuestions(keyword);
            return new ResponseVo(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getText(), this.convert(questions));
        } catch (FRException e) {
            return new ResponseVo(e.getReturnCode().getCode(), e.getErrorMsg());
        } catch (Exception e) {
            return new ResponseVo(ReturnCode.RUNTIME_ERROR.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "importData.html")
    public ResponseVo importData() {
        try {
            File questionFile = new File("/tmp/数字图形设计复习题.xls");
            questionService.importQuestions(questionFile);
            return new ResponseVo(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getText());
        } catch (FRException e) {
            return new ResponseVo(e.getReturnCode().getCode(), e.getErrorMsg());
        } catch (Exception e) {
            return new ResponseVo(ReturnCode.RUNTIME_ERROR.getCode(), e.getMessage());
        }
    }

    private QuestionVo convert(QuestionModel question) {
        if (null == question) {
            return null;
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        return questionVo;
    }

    private List<QuestionVo> convert(List<QuestionModel> questions) {
        List<QuestionVo> ret = null;
        if (null != questions && questions.size() > 0) {
            ret = Lists.newArrayList();
            QuestionVo questionVo;
            for (QuestionModel question : questions) {
                questionVo = this.convert(question);
                if (null != questionVo) {
                    ret.add(questionVo);
                }
            }
        }
        return ret;
    }
}
