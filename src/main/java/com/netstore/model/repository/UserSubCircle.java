package com.netstore.model.repository;

import com.netstore.model.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by Master on 2017-04-26.
 */
@Repository
@javax.transaction.Transactional
public interface UserSubCircle extends JpaRepository<TopicEntity,Integer>, JpaSpecificationExecutor<TopicEntity> {

    @Query(value = "SELECT CIRCLE.ID_Circle, COUNT(TOPIC.CIRCLE_ID_Circle) FROM CIRCLE,TOPIC WHERE TOPIC.CIRCLE_ID_Circle = CIRCLE.ID_Circle group by CIRCLE.ID_Circle",nativeQuery = true)
    Integer getCount();
}
