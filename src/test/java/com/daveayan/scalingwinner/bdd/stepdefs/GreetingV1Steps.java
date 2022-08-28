package com.daveayan.scalingwinner.bdd.stepdefs;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.daveayan.scalingwinner.greeting.v1.GreetingV1;

public class GreetingV1Steps {

    @Autowired
    private TestRestTemplate restTemplate;
    private int port = 8080;

    ResponseEntity<GreetingV1> latestResponse = null;

    ResponseEntity<GreetingV1> get(Long id) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v1" + "/greeting/";
        ResponseEntity<GreetingV1> actualObject = this.restTemplate.getForEntity(
                fullPathWithoutParams + id,
                GreetingV1.class);

        return actualObject;
    }

    ResponseEntity<GreetingV1> post(GreetingV1 requestObject) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v1" + "/greeting/";
        ResponseEntity<GreetingV1> actualObject = this.restTemplate.postForEntity(
                fullPathWithoutParams,
                requestObject,
                GreetingV1.class);

        return actualObject;
    }

    @Given("A Greeting V1 API")
    public void initializeGreetingV1API() throws Throwable {
        System.out.println("1");
    }

    @When("A Greeting V1 is created with {string}")
    public void createGreetingV1(String greetingName) {
        GreetingV1 gv1 = new GreetingV1(greetingName);
        latestResponse = post(gv1);
    }

    @When("I get the greeting V1 with ID {string}")
    public void getGreetingV1(String greetingId) {
        latestResponse = get(Long.parseLong(greetingId));
    }

    @Then("V1 API response code will be {string}")
    public void function3(String responseCode) {
        assertEquals(Integer.parseInt(responseCode), latestResponse.getStatusCode().value());
    }

    @And("V1 API response greeting id will be {string}")
    public void function4(String responseId) {
        assertEquals(Long.parseLong(responseId), latestResponse.getBody().getId());
    }

    @And("V1 API response content will be {string}")
    public void function5(String responseContent) {
        assertEquals(responseContent, latestResponse.getBody().getContent());
    }
}
