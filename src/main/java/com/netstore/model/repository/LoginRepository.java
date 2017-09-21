package com.netstore.model.repository;

        import com.netstore.model.entity.UserEntity;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;


        import java.util.List;


/**
 * Created by Master on 2017-04-26.
 */
@Repository
@javax.transaction.Transactional
public interface LoginRepository extends JpaRepository<UserEntity,String> {

    @Override
    List<UserEntity> findAll();
}
