package com.daveayan.scalingwinner.greeting;

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
public class GreetingController {
    @Autowired GreetingStore store;

    @GetMapping("/greeting/{id}")
    ResponseEntity<Greeting> getGreeting(
        @PathVariable Long id
    ) {
        try {
            Greeting greetingFromStore =  store.getFromStore(id);
            return new ResponseEntity<Greeting>(greetingFromStore, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("/greeting")
    ResponseEntity<Greeting> newGreeting(@RequestBody Greeting newGreeting) {
        try {
            Greeting createdGreeting = store.addNewToStore(newGreeting);
            return new ResponseEntity<Greeting>(createdGreeting, HttpStatus.CREATED);
        } catch (DuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DuplicateException");
        }
    }
}
