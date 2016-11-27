package com.zdl.finalreview.dao.test;

import com.zdl.finalreview.dao.enums.QuestionType;
import com.zdl.finalreview.dao.models.Question;
import com.zdl.finalreview.dao.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static com.sun.tools.internal.xjc.reader.Ring.add;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * Created by zhangdongli on 16-11-27.
 */
public class QuestionRepositoryTest {

    private QuestionRepository questionRepository;

    @Before
    public void befory() {
        ApplicationContext act = new ClassPathXmlApplicationContext("finalreview-dao-content.xml");
        questionRepository = act.getBean(QuestionRepository.class);
    }

    @Test
    public void saveQuestionTest() {

        Question question = new Question(
                1,
                QuestionType.TRUEORFALSE.getCode(),
                "中国人都是女人",
                new ArrayList<String>() {{
                    add("zhongguorendoushinvren");
                }},
                new ArrayList<String>() {{
                    add("zgrdsnr");
                }},
                null,
                new ArrayList<String>() {{
                    add("假");
                }}
        );

        Question saveQuestion = questionRepository.save(question);

        Assert.assertNotNull(saveQuestion);
        Assert.assertEquals(saveQuestion.getTitle(),question.getTitle());
        System.out.println(saveQuestion);
    }
}
