package com.daveayan.scalingwinner.greeting.v2;

import java.util.HashMap;
import java.util.Map;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingStoreV2 {
    private static Map<Long, GreetingV2> data = new HashMap<Long, GreetingV2>();
    private static Long idCounter = 1L;

    GreetingV2 getFromStore(Long id) throws GreetingNotFoundException {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            throw new GreetingNotFoundException(id);
        }
    }
    
    GreetingV2 addNewToStore(GreetingV2 newGreeting) throws DuplicateGreetingException {
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
