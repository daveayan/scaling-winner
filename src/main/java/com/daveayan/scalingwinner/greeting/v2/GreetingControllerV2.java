package com.daveayan.scalingwinner.greeting.v2;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v2/greeting")
public class GreetingControllerV2 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingControllerV2.class);
    @Autowired GreetingServiceV2 serviceV2;

    @GetMapping("/{id}")
    ResponseEntity<GreetingV2> getGreetingV2(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        LOG.trace("IN getGreetingV1");
        try {
            GreetingV2 greetingFromStore =  serviceV2.getFromStore(id);
            LOG.trace("OUT getGreetingV1");
            return new ResponseEntity<GreetingV2>(greetingFromStore, HttpStatus.OK);
        } catch (GreetingNotFoundException e) {
            LOG.trace("ERROR getGreetingV1");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("")
    ResponseEntity<GreetingV2> newGreetingV2(@RequestHeader HttpHeaders headers, @RequestBody GreetingV2 newGreeting) {
        LOG.trace("IN newGreetingV2");
        try {
            GreetingV2 createdGreeting = serviceV2.addNewToStore(newGreeting);
            LOG.trace("OUT newGreetingV2");
            return new ResponseEntity<GreetingV2>(createdGreeting, HttpStatus.CREATED);
        } catch (DuplicateGreetingException e) {
            LOG.trace("ERROR newGreetingV2");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DuplicateException");
        }
    }
}
