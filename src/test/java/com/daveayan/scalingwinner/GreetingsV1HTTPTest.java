package com.daveayan.scalingwinner;

import static org.assertj.core.api.Assertions.assertThat;

import com.daveayan.scalingwinner.greeting.v1.GreetingV1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingsV1HTTPTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<GreetingV1> get(Long id) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v1" + "/greeting/";
        ResponseEntity<GreetingV1> actualObject = this.restTemplate.getForEntity(
            fullPathWithoutParams + id, 
            GreetingV1.class
        );

        return actualObject;
    }

    ResponseEntity<GreetingV1> post(GreetingV1 requestObject) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v1" + "/greeting/";
        ResponseEntity<GreetingV1> actualObject = this.restTemplate.postForEntity(
            fullPathWithoutParams, 
            requestObject, 
            GreetingV1.class
        );

        return actualObject;
    }

    @Test
    public void gettingObjectThatDoesNotExistCauses404() throws Exception {
        ResponseEntity<GreetingV1> actualObject = get(201L);
        
        assertThat(actualObject.getStatusCode() == HttpStatus.NOT_FOUND).isTrue();
        assertThat(actualObject.getBody().getId() == 0).isTrue();
    }

    @Test
    public void createNewGreetingWithId() throws Exception {
        GreetingV1 requestObject = new GreetingV1(101L, "Hello 101");
        ResponseEntity<GreetingV1> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 101L).isTrue();
        assertThat(createRecordResponse.getBody().getContent().equals("Hello 101")).isTrue();
    }

    @Test
    public void createNewGreetingWithoutId() throws Exception {
        GreetingV1 requestObject = new GreetingV1("Hello 1L");
        ResponseEntity<GreetingV1> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 1L).isTrue();
    }

    @Test
    public void gettingObjectThatDoesExistCauses200AndProperResponse() throws Exception {
        ResponseEntity<GreetingV1> actualObject = get(101L);

        assertThat(actualObject.getStatusCode() == HttpStatus.OK).isTrue();
        assertThat(actualObject.getBody().getContent().equals("Hello 101")).isTrue();
        assertThat(actualObject.getBody().getContent().equals("Hello 101")).isTrue();
    }
}
