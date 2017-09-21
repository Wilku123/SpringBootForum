package com.netstore.model.repository;

import com.netstore.model.entity.CircleEntity;
import com.netstore.model.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Created by Master on 2017-04-26.
 */
@Repository
@javax.transaction.Transactional
public interface TopicRepository extends JpaRepository<TopicEntity,Integer>, JpaSpecificationExecutor<TopicEntity> {


    @Override
    List<TopicEntity> findAll();
    List<TopicEntity> findAllByCircleByCircleIdCircle(CircleEntity circleEntity);
    TopicEntity findByIdTopic(Integer i);
}
