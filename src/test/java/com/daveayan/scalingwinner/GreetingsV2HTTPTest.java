package com.daveayan.scalingwinner;

import static org.assertj.core.api.Assertions.assertThat;

import com.daveayan.scalingwinner.greeting.v2.GreetingV2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingsV2HTTPTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<GreetingV2> get(Long id) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v2" + "/greeting/";
        ResponseEntity<GreetingV2> actualObject = this.restTemplate.getForEntity(
            fullPathWithoutParams + id, 
            GreetingV2.class
        );

        return actualObject;
    }

    ResponseEntity<GreetingV2> post(GreetingV2 requestObject) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v2" + "/greeting/";
        ResponseEntity<GreetingV2> actualObject = this.restTemplate.postForEntity(
            fullPathWithoutParams, 
            requestObject, 
            GreetingV2.class
        );

        return actualObject;
    }

    @Test
    public void gettingObjectThatDoesNotExistCauses404() throws Exception {
        ResponseEntity<GreetingV2> actualObject = get(201L);
        
        assertThat(actualObject.getStatusCode() == HttpStatus.NOT_FOUND).isTrue();
        assertThat(actualObject.getBody().getId() == 0).isTrue();
    }

    @Test
    public void createNewGreetingWithId() throws Exception {
        GreetingV2 requestObject = new GreetingV2(101L, "Hello 101", "ABC", "XYZ");
        ResponseEntity<GreetingV2> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 101L).isTrue();
        assertThat(createRecordResponse.getBody().getMessage().equals("Hello 101")).isTrue();
        assertThat(createRecordResponse.getBody().getFrom().equals("ABC")).isTrue();
        assertThat(createRecordResponse.getBody().getTo().equals("XYZ")).isTrue();
    }

    @Test
    public void createNewGreetingWithoutId() throws Exception {
        GreetingV2 requestObject = new GreetingV2("Hello 1L");
        ResponseEntity<GreetingV2> createRecordResponse = post(requestObject);
        
        assertThat(createRecordResponse.getStatusCode() == HttpStatus.CREATED).isTrue();
        assertThat(createRecordResponse.getBody().getId() == 1L).isTrue();
    }

    @Test
    public void gettingObjectThatDoesExistCauses200AndProperResponse() throws Exception {
        ResponseEntity<GreetingV2> actualObject = get(101L);

        assertThat(actualObject.getStatusCode() == HttpStatus.OK).isTrue();
        assertThat(actualObject.getBody().getMessage().equals("Hello 101")).isTrue();
        assertThat(actualObject.getBody().getMessage().equals("Hello 101")).isTrue();
        assertThat(actualObject.getBody().getFrom().equals("ABC")).isTrue();
        assertThat(actualObject.getBody().getTo().equals("XYZ")).isTrue();
    }
}
