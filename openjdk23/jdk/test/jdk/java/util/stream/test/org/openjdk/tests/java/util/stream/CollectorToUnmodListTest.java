/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/*
 * @test
 * @bug 8254090
 * @summary Test for Collectors.toUnmodifiableList().
 */
public class CollectorToUnmodListTest {
    @SuppressWarnings("unchecked")
    @Test
    public void testFinisher() {
        String[] array = { "x", "y", "z" };
        List<String> in = new ArrayList<>() {
            public Object[] toArray() {
                return array;
            }
        };
        var finisher = (Function<List<String>, List<String>>)Collectors.<String>toUnmodifiableList().finisher();
        assertThrows(IllegalArgumentException.class, () -> finisher.apply(in));
  }
}
