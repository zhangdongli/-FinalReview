package com.zdl.finalreview.dao.repository;

import com.zdl.finalreview.dao.models.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangdongli on 16/11/25.
 * 题目查询
 */
@Repository
public interface QuestionRepository extends ElasticsearchRepository<Question, Integer> {
}
