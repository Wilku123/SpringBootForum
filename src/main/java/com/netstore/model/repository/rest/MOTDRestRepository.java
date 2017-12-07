package com.netstore.model.repository.rest;

import com.netstore.model.API.mobile.motd.MOTD;
import com.netstore.model.entity.MotdEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MOTDRestRepository extends JpaRepository<MotdEntity,Integer> {
    List<MotdEntity> findTop5ByOrderByPublishDateDesc();
}
