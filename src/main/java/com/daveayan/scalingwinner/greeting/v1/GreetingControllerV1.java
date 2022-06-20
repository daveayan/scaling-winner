package com.daveayan.scalingwinner.greeting.v1;

import javax.annotation.PostConstruct;

import com.daveayan.scalingwinner.greeting.common.DuplicateGreetingException;
import com.daveayan.scalingwinner.greeting.common.GreetingNotFoundException;
import com.daveayan.scalingwinner.greeting.translator.GreetingTranslator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/greeting")
public class GreetingControllerV1 {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingControllerV1.class);
    
    WebClient webClient;
    @Autowired GreetingServiceV1 serviceV1;

    @Value("${server.port}")
    private int serverPort;

    private String URLToTranslate = null;

    public GreetingControllerV1() {
        LOG.trace("IN GreetingControllerV1 constructor");
        LOG.trace("OUT GreetingControllerV1 constructor");
    }

    @PostConstruct
    public void init() {
        LOG.trace("IN init " + serverPort);
        URLToTranslate = "http://localhost:" + serverPort + "/api";
        LOG.trace("OUT init " + URLToTranslate);
    }

    @GetMapping("/{id}")
    ResponseEntity<GreetingV1> getGreetingV1(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        LOG.trace("IN getGreetingV1");
        try {
            GreetingV1 greetingFromStore =  serviceV1.getFromStore(id);
            RestTemplate rest = new RestTemplate();

            ResponseEntity<GreetingTranslator> restResponseGreetingTranslator = rest.getForEntity(
                URLToTranslate + "/v1/translate?text=Hello&from=en&to=es", 
                GreetingTranslator.class
            );

            // webClient.get().

            String translatedText = restResponseGreetingTranslator.getBody().getTranslatedText();
            GreetingV1 newGreeting = new GreetingV1(
                greetingFromStore.getId(), 
                greetingFromStore.getContent() + " " + translatedText
            ) ;

            LOG.trace("OUT getGreetingV1");
            return new ResponseEntity<GreetingV1>(newGreeting, HttpStatus.OK);
        } catch (GreetingNotFoundException e) {
            LOG.trace("ERROR getGreetingV1");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
        }
    }

    @PostMapping("")
    ResponseEntity<GreetingV1> newGreetingV1(@RequestHeader HttpHeaders headers, @RequestBody GreetingV1 newGreeting) {
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
