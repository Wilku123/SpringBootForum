package com.netstore.model.repository.rest;

import com.netstore.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EventRestRepository extends JpaRepository<EventEntity,Integer> {
    List<EventEntity> findTop30ByOrderByPublishDateDesc();

}
