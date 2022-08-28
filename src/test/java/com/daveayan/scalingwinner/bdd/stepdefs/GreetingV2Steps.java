package com.daveayan.scalingwinner.bdd.stepdefs;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.daveayan.scalingwinner.greeting.v2.GreetingV2;

public class GreetingV2Steps {

    @Autowired
    private TestRestTemplate restTemplate;
    private int port = 8080;

    ResponseEntity<GreetingV2> latestResponse = null;

    private void printLastResponse() {
        System.out.println("\n\n\n\n\n");
        System.out.println(latestResponse);
        System.out.println("\n\n\n\n\n");
    }

    ResponseEntity<GreetingV2> get(Long id) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v2" + "/greeting/";
        ResponseEntity<GreetingV2> actualObject = this.restTemplate.getForEntity(
                fullPathWithoutParams + id,
                GreetingV2.class);

        return actualObject;
    }

    ResponseEntity<GreetingV2> post(GreetingV2 requestObject) {
        String basePath = "http://localhost:" + port + "/api";
        String fullPathWithoutParams = basePath + "/v2" + "/greeting/";
        ResponseEntity<GreetingV2> actualObject = this.restTemplate.postForEntity(
                fullPathWithoutParams,
                requestObject,
                GreetingV2.class);

        return actualObject;
    }

    @Given("A Greeting V2 API")
    public void initializeGreetingV1API() throws Throwable {
        System.out.println("1");
    }

    @When("A Greeting V2 is created with {string}")
    public void createGreetingV2(String greetingName) {
        // GreetingV2 gv1 = new GreetingV2(greetingName, "ABC", "XYZ");
        GreetingV2 gv1 = new GreetingV2(greetingName);
        latestResponse = post(gv1);
    }

    @When("I get the greeting V2 with ID {string}")
    public void getGreetingV2(String greetingId) {
        latestResponse = get(Long.parseLong(greetingId));
    }

    @Then("V2 API response code will be {string}")
    public void function3(String responseCode) {
        assertEquals(Integer.parseInt(responseCode), latestResponse.getStatusCode().value());
    }

    @And("V2 API response greeting id will be {string}")
    public void function4(String responseId) {
        assertEquals(Long.parseLong(responseId), latestResponse.getBody().getId());
    }

    @And("V2 API response content will be {string}")
    public void function5(String responseContent) {
        assertEquals(responseContent, latestResponse.getBody().getMessage());
    }

    @And("V2 API response from will be {string}")
    public void function6(String responseFrom) {
        printLastResponse();
        // assertEquals(responseFrom, latestResponse.getBody().getFrom());
    }

    @And("V2 API response to will be {string}")
    public void function7(String responseTo) {
        // assertEquals(responseTo, latestResponse.getBody().getTo());
    }
}
