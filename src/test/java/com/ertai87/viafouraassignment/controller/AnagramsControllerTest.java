package com.ertai87.viafouraassignment.controller;

import com.ertai87.viafouraassignment.service.AnagramDetectionService;
import com.ertai87.viafouraassignment.service.AnagramGenerationService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnagramsControllerTest {
    @Mock
    private AnagramDetectionService detectionService;
    @Mock
    private AnagramGenerationService generationService;

    private AnagramsController tested;

    @Before
    public void setUp() {
        tested = new AnagramsController(detectionService, generationService);
    }

    @Test
    public void whenDetectionServiceReturnsTrueThenReturnStructureWithTrue() {
        when(detectionService.detectAnagram("dog", "god")).thenReturn(true);
        AnagramsController.AnagramDetectionResponse response = tested.detectAnagram("dog", "god");
        assertTrue(response.isAreAnagrams());
    }

    @Test
    public void whenDetectionServiceReturnsFalseThenReturnStructureWithTrue() {
        when(detectionService.detectAnagram("dog", "god")).thenReturn(false);
        AnagramsController.AnagramDetectionResponse response = tested.detectAnagram("dog", "god");
        assertFalse(response.isAreAnagrams());
    }

    @Test
    public void whenDetectionServiceThrowsRuntimeExceptionThenThrowBadRequest() {
        when(detectionService.detectAnagram("dog", "god")).thenThrow(new RuntimeException());
        try {
            tested.detectAnagram("dog", "god");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void whenGenerationServiceReturnsListThenReturnStructureWithList() {
        List<String> mockResponse = Lists.list("dog, god");
        when(generationService.generateAnagrams("dog")).thenReturn(mockResponse);
        AnagramsController.AnagramGenerationResponse response = tested.generateAnagrams("dog");
        assertEquals(mockResponse, response.getAnagrams());
    }

    @Test
    public void whenGenerationServiceThrowsRuntimeExceptionThenThrowBadRequest() {
        when(generationService.generateAnagrams("dog")).thenThrow(new RuntimeException());
        try {
            tested.generateAnagrams("dog");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }
}
