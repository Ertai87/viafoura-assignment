package com.ertai87.viafouraassignment.integration;

import com.ertai87.viafouraassignment.controller.AnagramsController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnagramsDetectionIntegrationTest {
    private String port;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private Environment env;

    @Before
    public void setUp() {
        port = env.getProperty("local.server.port");
    }

    @Test
    public void whenValidRequestToDetectionServiceThenReturnValue() {
        ResponseEntity<AnagramsController.AnagramDetectionResponse> response = restTemplate.getForEntity(
                URI.create("http://localhost:" + port + "/anagrams/dog/god"
                ), AnagramsController.AnagramDetectionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isAreAnagrams());
    }

    @Test
    public void whenInvalidRequestToDetectionServiceThenReturn400() {
        try {
            restTemplate.getForEntity(
                    URI.create("http://localhost:" + port + "/anagrams/0dog1/god"
                    ), AnagramsController.AnagramDetectionResponse.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    public void whenValidRequestToGenerationServiceThenReturnValue() {
        ResponseEntity<AnagramsController.AnagramGenerationResponse> response = restTemplate.getForEntity(
                URI.create("http://localhost:" + port + "/anagrams/dog"
                ), AnagramsController.AnagramGenerationResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody().getAnagrams().size());
        assertThat(response.getBody().getAnagrams(), containsInAnyOrder("dog", "dgo", "ogd", "odg", "god", "gdo"));
    }

    @Test
    public void whenInvalidRequestToGenerationServiceThenReturn400() {
        try {
            restTemplate.getForEntity(
                    URI.create("http://localhost:" + port + "/anagrams/0dog1"
                    ), AnagramsController.AnagramGenerationResponse.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
