package com.netstore.model.repository;

import com.netstore.model.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Master on 2017-09-21.
 */
public interface CredentialsRepository extends JpaRepository<CredentialsEntity,Integer>{
    CredentialsEntity findByToken(String token);
    CredentialsEntity findByUserIdUser(int id);
    CredentialsEntity findByTokenAndPin(String token , String pin);

}
