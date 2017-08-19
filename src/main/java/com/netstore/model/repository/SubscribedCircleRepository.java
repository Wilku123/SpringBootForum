package com.netstore.model.repository;

import com.netstore.model.SubscribedCircleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Master on 2017-07-24.
 */
@Repository
@javax.transaction.Transactional
public interface SubscribedCircleRepository extends JpaRepository<SubscribedCircleEntity,Integer> {

//    <SubscribedCircleEntity> boolean exists(SubscribedCircleEntity subscribedCircleEntity);

    SubscribedCircleEntity findByUserIdUserAndCircleIdCircle(Integer user, Integer circle);
    //boolean findByUserIdUserAndCircleIdCircle(Integer user, Integer circle);
}
