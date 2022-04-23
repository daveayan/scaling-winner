package com.daveayan.scalingwinner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import com.daveayan.scalingwinner.greeting.v1.GreetingV1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingsV1CacheHTTPTest {
    @LocalServerPort
    private int port;

    @Autowired
    CacheManager cacheManager;

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

    void showCache(String label) {
        Collection<String> cacheNames = cacheManager.getCacheNames();

        Object[] arrCacheNames = (Object []) cacheNames.toArray();
        System.out.println("----- START ----- " + label);
        for(int i = 0; i < arrCacheNames.length; i++) {
            System.out.println(" -- " + arrCacheNames[i]);
            Cache thisCache = cacheManager.getCache(arrCacheNames[i].toString());
            System.out.println(" -- -- -- " + thisCache.getNativeCache());
        }
        System.out.println("----- END ----- " + label);
    }

    @Test
    public void cacheEmpty_retrieve() throws Exception {
        
        post(new GreetingV1(101L, "Hello 101"));
        post(new GreetingV1(102L, "Hello 102"));
        post(new GreetingV1(103L, "Hello 103"));

        showCache("cacheEmpty_retrieve::Before");

        get(101L);
        showCache("cacheEmpty_retrieve::After 101");

        get(102L);
        showCache("cacheEmpty_retrieve::After 102");

        get(103L);
        showCache("cacheEmpty_retrieve::After 103");
        
        // assertThat(actualObject.getStatusCode() == HttpStatus.NOT_FOUND).isTrue();
        // assertThat(actualObject.getBody().getId() == 0).isTrue();
    }
}
