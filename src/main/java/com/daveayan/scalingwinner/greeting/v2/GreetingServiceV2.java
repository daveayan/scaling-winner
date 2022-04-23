package com.daveayan.scalingwinner.greeting.v2;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceV2 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingServiceV2.class);
    @Autowired GreetingStoreV2 storeV2;

    @Cacheable(cacheNames = "greetings-v2", key = "#result.id")
    public GreetingV2 getFromStore(Long id) throws GreetingNotFoundException {
        LOG.trace("IN getFromStore " + id);

        GreetingV2 greeting = storeV2.getFromStore(id);
        
        LOG.trace("OUT getFromStore " + greeting);
        return greeting;
    }
    
    public GreetingV2 addNewToStore(GreetingV2 newGreeting) throws DuplicateGreetingException {
        LOG.trace("IN addNewToStore " + newGreeting);
        
        GreetingV2 newGreetingCreated = storeV2.addNewToStore(newGreeting);
        
        LOG.trace("OUT addNewToStore " + newGreetingCreated);
        return newGreetingCreated;
    }
}
