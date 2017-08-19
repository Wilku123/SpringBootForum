package com.netstore.model.repository;

import com.netstore.model.SubscribedCircleEntity;
import com.netstore.model.SubscribedTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Master on 2017-07-24.
 */
@Repository
@javax.transaction.Transactional
public interface SubscribedTopicRepository extends JpaRepository<SubscribedTopicEntity,Integer> {



    SubscribedTopicEntity findByUserIdUserAndTopicIdTopic(Integer user, Integer circle);

}
