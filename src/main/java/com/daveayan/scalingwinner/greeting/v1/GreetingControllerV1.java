package com.daveayan.scalingwinner.greeting.v1;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/greeting")
public class GreetingControllerV1 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingControllerV1.class);
    
    @Autowired GreetingServiceV1 serviceV1;

    @GetMapping("/{id}")
    ResponseEntity<GreetingV1> getGreetingV1(@PathVariable Long id) {
        LOG.trace("IN getGreetingV1");
        try {
            GreetingV1 greetingFromStore =  serviceV1.getFromStore(id);
            LOG.trace("OUT getGreetingV1");
            return new ResponseEntity<GreetingV1>(greetingFromStore, HttpStatus.OK);
        } catch (GreetingNotFoundException e) {
            LOG.trace("ERROR getGreetingV1");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("")
    ResponseEntity<GreetingV1> newGreetingV1(@RequestBody GreetingV1 newGreeting) {
        LOG.trace("IN newGreetingV1");
        try {
            GreetingV1 createdGreeting = serviceV1.addNewToStore(newGreeting);
            LOG.trace("OUT newGreetingV1");
            return new ResponseEntity<GreetingV1>(createdGreeting, HttpStatus.CREATED);
        } catch (DuplicateGreetingException e) {
            LOG.trace("ERROR newGreetingV1");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DuplicateException");
        }
    }
}
