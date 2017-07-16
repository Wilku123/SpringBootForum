package com.netstore.model.repository;

import com.netstore.model.CircleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Created by Master on 2017-04-26.
 */
@Repository
@javax.transaction.Transactional
public interface CircleRepository extends JpaRepository<CircleEntity,Integer>, JpaSpecificationExecutor<CircleRepository> {

    @Override
    List<CircleEntity> findAll();

    @Override
    CircleEntity findOne(Integer integer);

//    @Query(value = "SELECT CIRCLE.ID_Circle, COUNT(TOPIC.CIRCLE_ID_Circle) FROM CIRCLE,TOPIC WHERE TOPIC.CIRCLE_ID_Circle = CIRCLE.ID_Circle group by CIRCLE.ID_Circle",nativeQuery = true)
//    public int countHowManyTopicsInCircle();


//    @Query(value = "SELECT CIRCLE.Name FROM CIRCLE",nativeQuery = true)
//    List<String> getByName();
//    @Query(value = "SELECT CIRCLE.Publish_Date FROM CIRCLE",nativeQuery = true)
//    List<String> getByPublishDate();
//    @Query(value = "SELECT CIRCLE.Description FROM CIRCLE",nativeQuery = true)
//    List<String> getByDescription();



}
