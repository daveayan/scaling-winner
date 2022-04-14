package com.daveayan.scalingwinner.greeting.v1;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GreetingControllerV1 {
    @Autowired GreetingStoreV1 storeV1;

    @GetMapping("/v1/greeting/{id}")
    ResponseEntity<GreetingV1> getGreetingV1(
        @PathVariable Long id
    ) {
        try {
            GreetingV1 greetingFromStore =  storeV1.getFromStore(id);
            return new ResponseEntity<GreetingV1>(greetingFromStore, HttpStatus.OK);
        } catch (GreetingNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("/v1/greeting")
    ResponseEntity<GreetingV1> newGreetingV1(@RequestBody GreetingV1 newGreeting) {
        try {
            GreetingV1 createdGreeting = storeV1.addNewToStore(newGreeting);
            return new ResponseEntity<GreetingV1>(createdGreeting, HttpStatus.CREATED);
        } catch (DuplicateGreetingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DuplicateException");
        }
    }
}
