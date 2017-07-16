package com.netstore.service;

        import com.netstore.model.CircleEntity;
        import com.netstore.model.repository.CircleRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Repository;
        import org.springframework.stereotype.Service;

        import javax.transaction.Transactional;

/**
 * Created by Master on 2017-04-28.
 */
@Service
@Repository
public class AddCircleService {
    @Autowired
    CircleRepository circleRepository;

    @Transactional
    public CircleEntity saveAndFlush(CircleEntity circleEntity){
        circleEntity = circleRepository.saveAndFlush(circleEntity);
        return circleEntity;
    }
}
