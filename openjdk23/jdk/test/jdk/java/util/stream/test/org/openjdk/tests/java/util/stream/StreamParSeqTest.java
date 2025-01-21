/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class StreamParSeqTest {

    public void testParSeq() {
        Stream<Integer> s = Arrays.asList(1, 2, 3, 4).stream().parallel();
        assertTrue(s.isParallel());

        s = s.sequential();
        assertFalse(s.isParallel());

        s = s.sequential();
        assertFalse(s.isParallel());

        s = s.parallel();
        assertTrue(s.isParallel());

        s = s.parallel();
        assertTrue(s.isParallel());
    }
}
