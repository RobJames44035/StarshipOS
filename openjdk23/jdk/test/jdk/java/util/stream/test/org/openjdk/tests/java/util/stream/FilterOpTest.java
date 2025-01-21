/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.Collection;
import java.util.stream.*;

import org.testng.annotations.Test;

import java.util.Arrays;

import static java.util.stream.LambdaTestHelpers.*;

/**
 * FilterOpTest
 *
 * @author Brian Goetz
 */
@Test
public class FilterOpTest extends OpTestCase {
    public void testFilter() {
        assertCountSum(countTo(0).stream().filter(pTrue), 0, 0);
        assertCountSum(countTo(10).stream().filter(pFalse), 0, 0);
        assertCountSum(countTo(10).stream().filter(pEven), 5, 30);
        assertCountSum(countTo(10).stream().filter(pOdd), 5, 25);
        assertCountSum(countTo(10).stream().filter(pTrue), 10, 55);
        assertCountSum(countTo(10).stream().filter(pEven).filter(pOdd), 0, 0);

        exerciseOps(countTo(1000), s -> s.filter(pTrue), countTo(1000));
        exerciseOps(countTo(1000), s -> s.filter(pFalse), countTo(0));
        exerciseOps(countTo(1000), s -> s.filter(e -> e > 100), range(101, 1000));
        exerciseOps(countTo(1000), s -> s.filter(e -> e < 100), countTo(99));
        exerciseOps(countTo(1000), s -> s.filter(e -> e == 100), Arrays.asList(100));
    }

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOps(String name, TestData.OfRef<Integer> data) {
        Collection<Integer> result = exerciseOps(data, s -> s.filter(pTrue));
        assertEquals(result.size(), data.size());

        result = exerciseOps(data, s -> s.filter(pFalse));
        assertEquals(result.size(), 0);

        exerciseOps(data, s -> s.filter(pEven));
        exerciseOps(data, s -> s.filter(pOdd));

        result = exerciseOps(data, s -> s.filter(pOdd.and(pEven)));
        assertEquals(result.size(), 0);

        result = exerciseOps(data, s -> s.filter(pOdd.or(pEven)));
        assertEquals(result.size(), data.size());
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfInt data) {
        Collection<Integer> result = exerciseOps(data, s -> s.filter(i -> true));
        assertEquals(result.size(), data.size());

        result = exerciseOps(data, s -> s.filter(i -> false));
        assertEquals(result.size(), 0);

        exerciseOps(data, s -> s.filter(i -> 0 == i % 2));
        exerciseOps(data, s -> s.filter(i -> 1 == i % 2));
    }

    @Test(dataProvider = "LongStreamTestData", dataProviderClass = LongStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfLong data) {
        Collection<Long> result = exerciseOps(data, s -> s.filter(i -> true));
        assertEquals(result.size(), data.size());

        result = exerciseOps(data, s -> s.filter(i -> false));
        assertEquals(result.size(), 0);

        exerciseOps(data, s -> s.filter(i -> 0 == i % 2));
        exerciseOps(data, s -> s.filter(i -> 1 == i % 2));
    }

    @Test(dataProvider = "DoubleStreamTestData", dataProviderClass = DoubleStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfDouble data) {
        Collection<Double> result = exerciseOps(data, s -> s.filter(i -> true));
        assertEquals(result.size(), data.size());

        result = exerciseOps(data, s -> s.filter(i -> false));
        assertEquals(result.size(), 0);

        exerciseOps(data, s -> s.filter(i -> 0 == ((long) i) % 2));
        exerciseOps(data, s -> s.filter(i -> 1 == ((long) i) % 2));
    }
}
