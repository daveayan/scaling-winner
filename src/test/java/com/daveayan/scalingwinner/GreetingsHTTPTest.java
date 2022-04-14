package com.daveayan.scalingwinner;

import static org.assertj.core.api.Assertions.assertThat;

import com.daveayan.scalingwinner.greeting.Greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingsHTTPTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<Greeting> get(Long id) {
        ResponseEntity<Greeting> actualObject = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/api/greeting/" + id, 
            Greeting.class
        );

        return actualObject;
    }

    ResponseEntity<Greeting> post(Greeting requestObject) {
        ResponseEntity<Greeting> actualObject = this.restTemplate.postForEntity(
            "http://localhost:" + port + "/api/greeting", 
            requestObject, 
            Greeting.class
        );

        return actualObject;
    }

    @Test
    public void gettingObjectThatDoesNotExistCauses404() throws Exception {
        ResponseEntity<Greeting> actualObject = get(201L);
        
        assertThat(actualObject.getStatusCode() == HttpStatus.NOT_FOUND).isTrue();
        assertThat(actualObject.getBody().getId() == 0).isTrue();
    }

    @Test
    public void createNewGreetingWithId() throws Exception {
        Greeting requestObject = new Greeting(101L, "Hello 101");
        ResponseEntity<Greeting> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 101L).isTrue();
    }

    @Test
    public void createNewGreetingWithoutId() throws Exception {
        Greeting requestObject = new Greeting("Hello 1L");
        ResponseEntity<Greeting> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 1L).isTrue();
    }

    @Test
    public void gettingObjectThatDoesExistCauses200AndProperResponse() throws Exception {
        ResponseEntity<Greeting> actualObject = get(101L);

        assertThat(actualObject.getStatusCode() == HttpStatus.OK).isTrue();
        assertThat(actualObject.getBody().getContent().equals("Hello 101")).isTrue();
    }
}
