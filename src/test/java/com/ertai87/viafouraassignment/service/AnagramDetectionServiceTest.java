package com.ertai87.viafouraassignment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnagramDetectionServiceTest {
    AnagramDetectionService tested = new AnagramDetectionService();

    @Test
    public void whenLowerCaseAnagramThenTrue() {
        boolean val = tested.detectAnagram("god", "dog");
        assertTrue(val);
    }

    @Test
    public void whenUpperCaseAnagramThenTrue() {
        boolean val = tested.detectAnagram("GOD", "DOG");
        assertTrue(val);
    }

    @Test
    public void whenMixedCaseAnagramThenTrue() {
        boolean val = tested.detectAnagram("God", "Dog");
        assertTrue(val);
    }

    @Test
    public void whenS1IsShorterThenFalse() {
        boolean val = tested.detectAnagram("go", "dog");
        assertFalse(val);
    }

    @Test
    public void whenS2IsShorterThenFalse() {
        boolean val = tested.detectAnagram("god", "do");
        assertFalse(val);
    }

    @Test
    public void whenEmptyStringThenTrue() {
        boolean val = tested.detectAnagram("", "");
        assertTrue(val);
    }

    @Test(expected = RuntimeException.class)
    public void whenInvalidStringsThenError() {
        tested.detectAnagram("{god}", "{dog}");
    }
}
