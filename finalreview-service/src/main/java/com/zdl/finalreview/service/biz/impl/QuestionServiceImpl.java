package com.zdl.finalreview.service.biz.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zdl.finalreview.common.enums.ReturnCode;
import com.zdl.finalreview.common.exception.FRException;
import com.zdl.finalreview.common.strings.LogBuilder;
import com.zdl.finalreview.common.strings.PinYin;
import com.zdl.finalreview.common.strings.StringUtil;
import com.zdl.finalreview.dao.enums.QuestionType;
import com.zdl.finalreview.dao.model.Question;
import com.zdl.finalreview.dao.repository.QuestionRepository;
import com.zdl.finalreview.service.biz.QuestionService;
import com.zdl.finalreview.service.model.QuestionModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;

/**
 * Created by zhangdongli on 16/11/28.
 * 题型服务实现
 */
@Component
public class QuestionServiceImpl implements QuestionService {

    private final static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionModel> findQuestions(String keyword) throws FRException {

        LogBuilder logBuilder = LogBuilder.getBuilder();
        logBuilder.append("查找题型[参数：").append("keyword='").append(keyword).append("']");

        List<QuestionModel> ret = Lists.newArrayList();
        if (null == keyword || keyword.trim().length() <= 0) {
            logBuilder.append("[失败：keyword不可以为空");
            logger.error(logBuilder.toString());
            throw new FRException(ReturnCode.PARAM_ERROR, "keyword不可以为空");
        }

        //创建查询参数
        BoolQueryBuilder queryBuilder = boolQuery();
        if (StringUtil.isChinese(keyword, false)) {
            queryBuilder.must(matchQuery("title", keyword));
        } else {
            keyword = keyword.toLowerCase().replaceAll(" ", "");
            BoolQueryBuilder shouldBuilder = boolQuery();
            shouldBuilder.should(prefixQuery("titleSpells", keyword));
            shouldBuilder.should(prefixQuery("titleSimpSpells", keyword));
            queryBuilder.must(shouldBuilder);
        }

        //从es中查询
        Page<Question> quesPage = questionRepository.search(queryBuilder, new PageRequest(0, 1000));
        List<Question> questions = quesPage.getContent();
        return this.convert(questions);
    }

    @Override
    public void importQuestions(File questionsFile) throws FRException {
        LogBuilder logBuilder = LogBuilder.getBuilder();
        logBuilder.append("导入题型[参数：questionsFile='").append(questionsFile).append("']");

        //文件校验
        if (!questionsFile.exists()) {
            logBuilder.append("[失败：文件不存在]");
            logger.error(logBuilder.toString());
            throw new FRException(ReturnCode.PARAM_ERROR, "文件不存在");
        }

        if (!questionsFile.canRead()) {
            logBuilder.append("[失败：文件不可读]");
            logger.error(logBuilder.toString());
            throw new FRException(ReturnCode.PARAM_ERROR, "文件不可读");
        }

        if (!questionsFile.getName().endsWith(".xls")) {
            logBuilder.append("[失败：文件格式不是.xls]");
            logger.error(logBuilder.toString());
            throw new FRException(ReturnCode.PARAM_ERROR, "文件格式不是.xls");
        }

        //解析题型
        List<QuestionModel> questionModels = null;
        try {
            questionModels = this.getQuestionsFromExcel(questionsFile);
        } catch (FRException ex) {
            logBuilder.append("[失败：解析excel报错-").append(ex.getErrorMsg()).append("]");
            logger.error(logBuilder.toString());
            throw ex;
        }

        //导入到es
        if (null != questionModels && questionModels.size() > 0) {
            try {

                List<Question> questions = this.reverseConvert(questionModels);
                questionRepository.deleteAll();
                List<Question> saveQuestions = (List<Question>) questionRepository.save(questions);
                logBuilder.append("[成功：保存" + saveQuestions.size() + "道]");
                logger.info(logBuilder.toString());

            } catch (Exception ex) {
                logBuilder.append("[失败：导入到es报错-").append(ex.getMessage()).append("]");
                logger.error(logBuilder.toString());
                throw new FRException(ReturnCode.RUNTIME_ERROR, ex.getMessage());
            }
        }
    }

    /**
     * 从excel获取题目
     *
     * @param excelFile excel文件
     * @return 题目集合
     */
    private List<QuestionModel> getQuestionsFromExcel(File excelFile) throws FRException {
        List<QuestionModel> ret = Lists.newArrayList();
        Workbook book = null;
        Sheet sheet1, sheet2, sheet3;
        try {

            //要读取的excel文件
            book = Workbook.getWorkbook(excelFile);

            //获得工作表对象(excel中sheet的编号从0开始,0,1,2,3,....)
            sheet1 = book.getSheet("选择题");
            sheet2 = book.getSheet("判断题");
            sheet3 = book.getSheet("填空题");

            //选择题
            List<QuestionModel> multipleChoiceQuestons = this.getMultipleChoiceQuestons(sheet1);
            if (null != multipleChoiceQuestons && multipleChoiceQuestons.size() > 0) {
                ret.addAll(multipleChoiceQuestons);
                //System.out.println("解析选择题有"+multipleChoiceQuestons.size()+"道.");
            }


            //判断题
            List<QuestionModel> trueOrFalseQuestons = this.getTrueorfalseOrFillintheblankQuestions(sheet2, QuestionType.TRUEORFALSE);
            if (null != trueOrFalseQuestons && trueOrFalseQuestons.size() > 0) {
                ret.addAll(trueOrFalseQuestons);
                //System.out.println("解析判断题有"+trueOrFalseQuestons.size()+"道.");
            }

            //填空题
            List<QuestionModel> fillinTheblankQuestons = this.getTrueorfalseOrFillintheblankQuestions(sheet3, QuestionType.FILLINTHEBLANK);
            if (null != fillinTheblankQuestons && fillinTheblankQuestons.size() > 0) {
                ret.addAll(fillinTheblankQuestons);
                //System.out.println("解析填空题有"+fillinTheblankQuestons.size()+"道.");
            }
        } catch (FRException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FRException(ReturnCode.RUNTIME_ERROR, ex.getMessage());
        } finally {
            if (null != book) {
                book.close();
            }
        }
        return ret;
    }

    /**
     * 获取选择题
     *
     * @param questionSheet 选择题sheet
     * @return 选择题集合
     */
    private List<QuestionModel> getMultipleChoiceQuestons(Sheet questionSheet) throws FRException {

        List<QuestionModel> questionModels = Lists.newArrayList();
        PinYin pinYin = new PinYin();
        String finalString = null;
        List<String> spells = null;
        List<String> simpSpells = null;
        List<String> options = null;
        List<String> anwers = null;
        QuestionModel questionModel = null;

        int i = 1;
        Cell cell0, cell1, cell2, cell3, cell4, cell5, cell6;

        int rows = questionSheet.getRows();
        int columns = questionSheet.getColumns();

        if (rows > 1000) {
            throw new FRException(ReturnCode.RUNTIME_ERROR, "选提题一次上传数量不能超过1000条");
        }

        while (true) {
            if (i > rows - 1) {
                break;
            }

            //获取每一行的单元格
            cell0 = questionSheet.getCell(0, i);
            cell1 = questionSheet.getCell(1, i);
            cell2 = questionSheet.getCell(2, i);
            cell3 = questionSheet.getCell(3, i);
            cell4 = questionSheet.getCell(4, i);
            cell5 = questionSheet.getCell(5, i);
            cell6 = questionSheet.getCell(6, i);

            //如果读取的数据为空
            if (Strings.isNullOrEmpty(cell1.getContents())) {
                break;
            }

            //拼音
            finalString = StringUtil.getNumberOrEnglishOrChinese(cell1.getContents());
            if(finalString.length() > 20){
                //只要前20个字符就够了
                finalString = finalString.substring(0,19);
            }
            spells = new ArrayList<>(Arrays.asList(pinYin.getStringPinYins(finalString)));
            simpSpells = new ArrayList<>(Arrays.asList(pinYin.getStringSimpPinYins(finalString)));

            //选项
            options = Lists.newArrayList();
            options.add("A." + cell2.getContents());
            options.add("B." + cell3.getContents());
            options.add("C." + cell4.getContents());
            options.add("D." + cell5.getContents());

            //答案
            anwers = Lists.newArrayList();
            anwers.add(cell6.getContents());

            //题型
            questionModel = new QuestionModel(
                    QuestionType.MULTIPLECHOICE.getCode() + "_" + cell0.getContents(),
                    QuestionType.MULTIPLECHOICE.getCode(),
                    cell1.getContents(),
                    spells,
                    simpSpells,
                    options,
                    anwers
            );
            questionModels.add(questionModel);
            i++;
        }
        return questionModels;
    }

    /**
     * 获取判断题或填空题
     *
     * @param questionSheet 判断题或填空题sheet
     * @return 判断题或填空题集合
     */
    private List<QuestionModel> getTrueorfalseOrFillintheblankQuestions(Sheet questionSheet, QuestionType questionType) throws FRException {
        List<QuestionModel> questionModels = Lists.newArrayList();
        PinYin pinYin = new PinYin();
        String finalString = null;
        List<String> spells = null;
        List<String> simpSpells = null;
        List<String> anwers = null;
        QuestionModel questionModel = null;

        int i = 1;
        Cell cell0, cell1, cell2;
        int rows = questionSheet.getRows();
        int columns = questionSheet.getColumns();

        if (rows > 1000) {
            throw new FRException(ReturnCode.RUNTIME_ERROR, "判断题或填空题一次上传数量不能超过1000条");
        }

        while (true) {
            if (i > rows - 1) {
                break;
            }

            //获取每一行的单元格
            cell0 = questionSheet.getCell(0, i);
            cell1 = questionSheet.getCell(1, i);
            cell2 = questionSheet.getCell(2, i);

            //如果读取的数据为空
            if (Strings.isNullOrEmpty(cell1.getContents())) {
                break;
            }

            //拼音
            finalString = StringUtil.getNumberOrEnglishOrChinese(cell1.getContents());
            spells = new ArrayList<>(Arrays.asList(pinYin.getStringPinYins(finalString)));
            simpSpells = new ArrayList<>(Arrays.asList(pinYin.getStringSimpPinYins(finalString)));

            //答案
            anwers = Lists.newArrayList();
            anwers.add(cell2.getContents());

            //题型
            questionModel = new QuestionModel(
                    questionType.getCode() + "_" + cell0.getContents(),
                    questionType.getCode(),
                    cell1.getContents(),
                    spells,
                    simpSpells,
                    null,
                    anwers
            );
            questionModels.add(questionModel);
            i++;
        }
        return questionModels;
    }

    private Question reverseConvert(QuestionModel questionModel) {
        if (null == questionModel) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionModel, question);
        return question;
    }

    private List<Question> reverseConvert(List<QuestionModel> questionModels) {
        List<Question> ret = Lists.newArrayList();
        if (null != questionModels && questionModels.size() > 0) {
            Question question = null;
            for (QuestionModel questionModel : questionModels) {
                question = this.reverseConvert(questionModel);
                if (null != question) {
                    ret.add(question);
                }
            }
        }
        return ret;
    }

    private QuestionModel convert(Question question) {
        if (null == question) {
            return null;
        }
        QuestionModel questionModel = new QuestionModel();
        BeanUtils.copyProperties(question, questionModel);
        return questionModel;
    }

    private List<QuestionModel> convert(List<Question> questions) {
        List<QuestionModel> ret = Lists.newArrayList();
        if (null != questions && questions.size() > 0) {
            QuestionModel questionModel = null;
            for (Question question : questions) {
                questionModel = this.convert(question);
                if (null != questionModel) {
                    ret.add(questionModel);
                }
            }
        }
        return ret;
    }
}
