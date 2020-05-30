package com.ertai87.viafouraassignment.service;

import org.springframework.stereotype.Service;

/* Takes 2 strings and determines if they are anagrams of each other.
   - Is not case-sensitive; 'Dog' and 'God' are anagrams of each other
   - Accepts only A-Za-z as valid characters, no numerics or special characters
 */

@Service
public class AnagramDetectionService {
    public boolean detectAnagram(String s1, String s2) {
        // This for loop could be done easily with a regex, but that would be much harder to read
        for (char c : (s1 + s2).toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                throw new RuntimeException("Invalid input, contains ASCII value " + (int)c);
            }
        }

        if (s1.length() != s2.length()) return false;

        //By above, we know the strings are alphabetical only so toLowerCase will not do anything strange
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] s1hist = new int[26];
        int[] s2hist = new int[26];
        for (int i = 0; i < 26; i++) {
            s1hist[i] = 0;
            s2hist[i] = 0;
        }

        //by above, we have already determined the 2 strings are of equal length
        for (int i = 0; i < s1.length(); i++) {
            s1hist[s1.charAt(i) - 'a']++;
            s2hist[s2.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++){
            if (s1hist[i] != s2hist[i]) return false;
        }
        return true;
    }
}
