package com.netstore.model.repository.rest;

import com.netstore.model.view.CircleRestViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Master on 2017-07-21.
 */
@Repository
@javax.transaction.Transactional
public interface CircleRestViewRepository extends JpaRepository<CircleRestViewEntity,Integer> {
    CircleRestViewEntity findByIdCircle(Integer id);
    CircleRestViewEntity findByUserIdUser(Integer id);
    List<CircleRestViewEntity> findAllByPublishDateIsLessThanEqualOrderByPublishDateDesc(Timestamp date);
    List<CircleRestViewEntity> findAllByPublishDateIsLessThanOrderByPublishDateDesc(Timestamp date);
    List<CircleRestViewEntity> findAllByNameContaining(String name);
    List<CircleRestViewEntity> findAllByDescriptionContaining(String name);
    List<CircleRestViewEntity> findAllByNameContainingOrDescriptionContaining(String name,String description);
}

