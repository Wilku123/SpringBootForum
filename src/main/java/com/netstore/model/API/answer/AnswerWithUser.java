package com.netstore.model.API.answer;

import com.netstore.model.view.AnswerRestViewEntity;
import com.netstore.model.view.UserRestViewEntity;

/**
 * Created by Master on 2017-10-15.
 */
public class AnswerWithUser {
    private AnswerRestViewEntity answerRestViewEntity;
    private UserRestViewEntity userRestViewEntity;

    public AnswerRestViewEntity getAnswerRestViewEntity() {
        return answerRestViewEntity;
    }

    public void setAnswerRestViewEntity(AnswerRestViewEntity answerRestViewEntity) {
        this.answerRestViewEntity = answerRestViewEntity;
    }

    public UserRestViewEntity getUserRestViewEntity() {
        return userRestViewEntity;
    }

    public void setUserRestViewEntity(UserRestViewEntity userRestViewEntity) {
        this.userRestViewEntity = userRestViewEntity;
    }
}
