package com.netstore.model.repository;

import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Master on 2017-05-21.
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity,Integer>, JpaSpecificationExecutor<AnswerEntity> {
    List<AnswerEntity> findAllByTopicByTopicIdTopic(TopicEntity topicEntity);
    List<AnswerEntity> findAllByTopicIdTopic(Integer id);
}
