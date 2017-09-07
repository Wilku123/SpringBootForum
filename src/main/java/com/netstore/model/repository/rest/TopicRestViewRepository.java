package com.netstore.model.repository.rest;

import com.netstore.model.CircleRestViewEntity;
import com.netstore.model.TopicRestViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Master on 2017-07-21.
 */
@Repository
@javax.transaction.Transactional
public interface TopicRestViewRepository extends JpaRepository<TopicRestViewEntity,Integer> {
    TopicRestViewEntity findByIdTopic(Integer id);
    List<TopicRestViewEntity> findAllByCircleIdCircleOrderByPublishDateDesc(Integer id);
    List<TopicRestViewEntity> findAllByCircleIdCircleAndPublishDateIsLessThanEqualOrderByPublishDateDesc(Integer id, Timestamp timestamp);
    List<TopicRestViewEntity> findAllByNameContaining(String name);

}


