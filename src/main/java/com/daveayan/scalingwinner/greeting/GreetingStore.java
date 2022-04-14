package com.daveayan.scalingwinner.greeting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingStore {
    private static Map<Long, Greeting> data = new HashMap<Long, Greeting>();
    private static Long idCounter = 1L;

    Greeting getFromStore(Long id) throws NotFoundException {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            throw new NotFoundException(id);
        }
    }
    
    Greeting addNewToStore(Greeting newGreeting) throws DuplicateException {
        if(newGreeting.getId() != 0) {
            if(data.containsKey(newGreeting.getId())) {
                throw new DuplicateException(newGreeting.getId());
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

class DuplicateException extends Exception {
    String message;
    DuplicateException(Long id) { 
        super();
        this.message = "The record already exists - " + id;
    }
}

class NotFoundException extends Exception {
    String message;
    NotFoundException(Long id) { 
        super();
        this.message = "Record not found - " + id;
    }
}
