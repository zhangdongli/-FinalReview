package com.zdl.finalreview.service.biz;

import com.zdl.finalreview.common.exception.FRException;
import com.zdl.finalreview.service.model.QuestionModel;

import java.io.File;
import java.util.List;

/**
 * Created by zhangdongli on 16/11/28.
 * 题型服务
 */
public interface QuestionService {

    /**
     * 查找题型
     *
     * @param keyword 题型关键字
     * @return 题型集合
     */
    List<QuestionModel> findQuestions(String keyword) throws FRException;

    /**
     * 导入题型
     *
     * @param questionsFile 要导入的题型文件
     */
    void importQuestions(File questionsFile) throws FRException;

}
