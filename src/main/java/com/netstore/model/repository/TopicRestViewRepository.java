package com.netstore.model.repository;

import com.netstore.model.CircleRestViewEntity;
import com.netstore.model.TopicRestViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Master on 2017-07-21.
 */
@Repository
@javax.transaction.Transactional
public interface TopicRestViewRepository extends JpaRepository<TopicRestViewEntity,Integer> {
    TopicRestViewEntity findByIdTopic(Integer id);
    List<TopicRestViewEntity> findAllByCircleIdCircle(Integer id);

}


