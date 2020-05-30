package com.ertai87.viafouraassignment.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/* Takes a string and returns all "anagrams" (permutations) of the characters in that string.
   - Output is always lowercase, even if input includes uppercase characters
   - Throws error if input string contains a non-alphabetic (A-Za-z) character
 */

@Service
public class AnagramGenerationService {
    public List<String> generateAnagrams(String s) {
        // This for loop could be done easily with a regex, but that would be much harder to read
        for (char c : s.toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                throw new RuntimeException("Invalid input, contains ASCII value " + (int)c);
            }
        }

        // Casting to LinkedList here to make the remove/re-add operation fast in the helper method
        return generateAnagramsHelper(new LinkedList<>(s.toLowerCase().chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList())));
    }

    private List<String> generateAnagramsHelper(List<Character> string) {
        //Using LinkedList here to make list add fast because random access is not used
        List<String> perms = new LinkedList<>();
        if (string.size() == 1) {
            perms.add("" + string.get(0));
            return perms;
        }
        for (int i = 0; i < string.size(); i++) {
            char removed = string.remove(i);
            generateAnagramsHelper(string).forEach(substring -> {
                perms.add(removed + substring);
            });
            string.add(i, removed);
        }
        return perms;
    }
}
