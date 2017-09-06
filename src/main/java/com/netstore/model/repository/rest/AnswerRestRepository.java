package com.netstore.model.repository.rest;

import com.netstore.model.AnswerEntity;
import com.netstore.model.AnswerRestViewEntity;
import com.netstore.model.CircleEntity;
import com.netstore.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Master on 2017-05-21.
 */
public interface AnswerRestRepository extends JpaRepository<AnswerRestViewEntity,Integer>, JpaSpecificationExecutor<AnswerEntity> {
    List<AnswerRestViewEntity> findAllByTopicIdTopic(Integer id);
    List<AnswerRestViewEntity> findAllByTopicIdTopicAndPublishDateIsLessThanEqualOrderByPublishDateDesc(Integer id, Timestamp timestamp);
}
