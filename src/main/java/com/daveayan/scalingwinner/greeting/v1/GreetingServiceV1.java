package com.daveayan.scalingwinner.greeting.v1;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceV1 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingServiceV1.class);
    @Autowired GreetingStoreV1 storeV1;

    @Cacheable(cacheNames = "greetings-v1")
    public GreetingV1 getFromStore(Long id) throws GreetingNotFoundException {
        LOG.trace("IN getFromStore " + id);

        GreetingV1 greeting = storeV1.getFromStore(id);
        
        LOG.trace("OUT getFromStore " + greeting);
        return greeting;
    }
    
    @CachePut(cacheNames = "greetings-v1", key = "#result.id")
    public GreetingV1 addNewToStore(GreetingV1 newGreeting) throws DuplicateGreetingException {
        LOG.trace("IN addNewToStore " + newGreeting);
        
        GreetingV1 newGreetingCreated = storeV1.addNewToStore(newGreeting);
        
        LOG.trace("OUT addNewToStore " + newGreetingCreated);
        return newGreetingCreated;
    }
}
