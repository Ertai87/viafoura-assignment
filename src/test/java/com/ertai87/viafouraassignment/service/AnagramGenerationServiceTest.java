package com.ertai87.viafouraassignment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnagramGenerationServiceTest {
    private AnagramGenerationService tested = new AnagramGenerationService();

    @Test
    public void whenLowerCaseInputThenGenerateLowerCasePermutations() {
        List<String> perms = tested.generateAnagrams("dog");
        assertEquals(6, perms.size());
        assertThat(perms, containsInAnyOrder("dog", "dgo", "ogd", "odg", "god", "gdo"));
    }

    @Test
    public void whenUpperCaseInputThenGenerateLowerCasePermutations() {
        List<String> perms = tested.generateAnagrams("DOG");
        assertEquals(6, perms.size());
        assertThat(perms, containsInAnyOrder("dog", "dgo", "ogd", "odg", "god", "gdo"));
    }

    @Test
    public void whenMixedCaseInputThenGenerateLowerCasePermutations() {
        List<String> perms = tested.generateAnagrams("Dog");
        assertEquals(6, perms.size());
        assertThat(perms, containsInAnyOrder("dog", "dgo", "ogd", "odg", "god", "gdo"));
    }

    @Test
    public void whenEmptyStringThenNoPermutations() {
        List<String> perms = tested.generateAnagrams("");
        assertThat(perms, emptyCollectionOf(String.class));
    }

    @Test(expected = RuntimeException.class)
    public void whenInvalidStringThenThrowRuntimeException() {
        tested.generateAnagrams("{dog}");
    }
}
