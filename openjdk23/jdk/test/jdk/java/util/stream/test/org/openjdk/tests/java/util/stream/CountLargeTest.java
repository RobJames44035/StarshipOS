/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @summary Tests counting of streams containing Integer.MAX_VALUE + 1 elements
 * @bug 8031187 8067969
 */

package org.openjdk.tests.java.util.stream;

import java.util.stream.LongStream;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class CountLargeTest {

    static final long EXPECTED_LARGE_COUNT = 1L + Integer.MAX_VALUE;

    public void testRefLarge() {
        // Test known sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToObj(e -> null).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
        // Test unknown sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToObj(e -> null).filter(e -> true).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
    }

    public void testIntLarge() {
        // Test known sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToInt(e -> 0).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
        // Test unknown sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToInt(e -> 0).filter(e -> true).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
    }

    public void testLongLarge() {
        // Test known sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
        // Test unknown sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .filter(e -> true).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
    }

    public void testDoubleLarge() {
        // Test known sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToDouble(e -> 0.0).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
        // Test unknown sized stream
        {
            long count = LongStream.range(0, EXPECTED_LARGE_COUNT)
                    .mapToDouble(e -> 0.0).filter(e -> true).count();
            assertEquals(count, EXPECTED_LARGE_COUNT);
        }
    }
}
