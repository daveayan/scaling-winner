package com.daveayan.scalingwinner.greeting.v1;

import java.util.HashMap;
import java.util.Map;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingStoreV1 {
    private static Map<Long, GreetingV1> data = new HashMap<Long, GreetingV1>();
    private static Long idCounter = 1L;

    GreetingV1 getFromStore(Long id) throws GreetingNotFoundException {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            throw new GreetingNotFoundException(id);
        }
    }
    
    GreetingV1 addNewToStore(GreetingV1 newGreeting) throws DuplicateGreetingException {
        if(newGreeting.getId() != 0) {
            if(data.containsKey(newGreeting.getId())) {
                throw new DuplicateGreetingException(newGreeting.getId());
            }
            data.put(newGreeting.getId(), newGreeting);
        } else {
            newGreeting.setId(idCounter);
            data.put(idCounter, newGreeting);
            idCounter ++;
        }
        
        return newGreeting;
    }
}
