package com.netstore.model.service;

import com.netstore.model.entity.EventEntity;
import com.netstore.model.repository.rest.EventRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
public class AddEventService {

    @Autowired
    private EventRestRepository eventRestRepository;

    @Transactional
    public EventEntity saveAndFlush(EventEntity eventEntity)
    {
        eventEntity = eventRestRepository.saveAndFlush(eventEntity);
        return eventEntity;
    }
}
