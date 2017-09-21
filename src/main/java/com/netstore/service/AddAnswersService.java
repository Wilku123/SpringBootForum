package com.netstore.service;

import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-05-22.
 */
@Service
@Repository
public class AddAnswersService {
    @Autowired
    AnswerRepository answerRepository;

    @Transactional
    public AnswerEntity saveAndFlush(AnswerEntity answerEntity){
        answerEntity = answerRepository.saveAndFlush(answerEntity);
        return answerEntity;
    }
}
