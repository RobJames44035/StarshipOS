/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package org.openjdk.tests.java.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test(groups = "lib")
public class FillableStringTest {
    public Stream<String> generate() {
        return Arrays.asList("one", "two", "three", "four", "five", "six").stream()
                .filter(s->s.length() > 3)
                .map(String::toUpperCase);
    }

    public void testStringBuilder() {
        String s = generate().collect(Collectors.joining());
        assertEquals(s, "THREEFOURFIVE");
    }

    public void testStringBuffer() {
        String s = generate().collect(Collectors.joining());
        assertEquals(s, "THREEFOURFIVE");
    }

    public void testStringJoiner() {
        String s = generate().collect(Collectors.joining("-"));
        assertEquals(s, "THREE-FOUR-FIVE");
    }
}
