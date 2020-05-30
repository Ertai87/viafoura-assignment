package com.ertai87.viafouraassignment.controller;

import com.ertai87.viafouraassignment.service.AnagramDetectionService;
import com.ertai87.viafouraassignment.service.AnagramGenerationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/anagrams")
@AllArgsConstructor
public class AnagramsController {
    private AnagramDetectionService detectionService;
    private AnagramGenerationService generationService;

    @GetMapping("/{s1}/{s2}")
    public AnagramDetectionResponse detectAnagram(@PathVariable String s1, @PathVariable String s2) {
        try {
            return new AnagramDetectionResponse(detectionService.detectAnagram(s1, s2));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{s1}")
    public AnagramGenerationResponse generateAnagrams(@PathVariable String s1) {
        try {
            return new AnagramGenerationResponse(generationService.generateAnagrams(s1));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // NoArgsConstructors required for integration test code

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnagramDetectionResponse {
        private boolean areAnagrams;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnagramGenerationResponse {
        private List<String> anagrams;
    }
}
