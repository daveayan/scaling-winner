package com.daveayan.scalingwinner.greeting.v1;

import java.util.HashMap;
import java.util.Map;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GreetingStoreV1 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingStoreV1.class);
    private static Map<Long, GreetingV1> data = new HashMap<Long, GreetingV1>();
    private static Long idCounter = 1L;

    public GreetingStoreV1() {
        LOG.trace("IN GreetingStoreV1 constructor");
        LOG.trace("OUT GreetingStoreV1 constructor");
    }

    GreetingV1 getFromStore(Long id) throws GreetingNotFoundException {
        LOG.trace("IN getFromStore " + id);

        if (data.containsKey(id)) {
            GreetingV1 greetingToReturn = data.get(id);

            LOG.trace("OUT getFromStore " + greetingToReturn);
            return data.get(id);
        } else {
            LOG.trace("ERROR getFromStore");
            throw new GreetingNotFoundException(id);
        }
    }
    
    GreetingV1 addNewToStore(GreetingV1 newGreeting) throws DuplicateGreetingException {
        LOG.trace("IN addNewToStore " + newGreeting);

        if(newGreeting.getId() != 0) {
            if(data.containsKey(newGreeting.getId())) {
                LOG.trace("ERROR addNewToStore " + newGreeting);
                throw new DuplicateGreetingException(newGreeting.getId());
            }
            data.put(newGreeting.getId(), newGreeting);
        } else {
            newGreeting.setId(idCounter);
            data.put(idCounter, newGreeting);
            idCounter ++;
        }
        
        LOG.trace("OUT addNewToStore " + newGreeting);
        return newGreeting;
    }
}
