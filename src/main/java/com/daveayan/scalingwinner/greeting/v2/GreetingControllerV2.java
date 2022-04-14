package com.daveayan.scalingwinner.greeting.v2;

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
public class GreetingControllerV2 {
    @Autowired GreetingStoreV2 storeV2;

    @GetMapping("/v2/greeting/{id}")
    ResponseEntity<GreetingV2> getGreetingV2(
        @PathVariable Long id
    ) {
        try {
            GreetingV2 greetingFromStore =  storeV2.getFromStore(id);
            return new ResponseEntity<GreetingV2>(greetingFromStore, HttpStatus.OK);
        } catch (GreetingNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("/v2/greeting")
    ResponseEntity<GreetingV2> newGreetingV2(@RequestBody GreetingV2 newGreeting) {
        try {
            GreetingV2 createdGreeting = storeV2.addNewToStore(newGreeting);
            return new ResponseEntity<GreetingV2>(createdGreeting, HttpStatus.CREATED);
        } catch (DuplicateGreetingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DuplicateException");
        }
    }
}
