/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.Collection;
import java.util.stream.*;

import org.testng.annotations.Test;

import static java.util.stream.LambdaTestHelpers.assertCountSum;
import static java.util.stream.LambdaTestHelpers.assertUnique;

/**
 * UniqOpTest
 */
@Test
public class IntUniqOpTest extends OpTestCase {

    public void testUniqOp() {
        assertCountSum(IntStream.generate(() -> 0).limit(10).distinct().boxed(), 1, 0);
        assertCountSum(IntStream.generate(() -> 1).limit(10).distinct().boxed(), 1, 1);
        assertCountSum(IntStream.range(0, 0).distinct().boxed(), 0, 0);
        assertCountSum(IntStream.range(1, 11).distinct().boxed(), 10, 55);
        assertCountSum(IntStream.range(1, 11).distinct().boxed(), 10, 55);
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOp(String name, TestData.OfInt data) {
        Collection<Integer> result = exerciseOps(data, s -> s.distinct().boxed());

        assertUnique(result);
        if (data.size() > 0)
            assertTrue(result.size() > 0);
        else
            assertTrue(result.size() == 0);
        assertTrue(result.size() <= data.size());
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOpSorted(String name, TestData.OfInt data) {
        Collection<Integer> result = withData(data).
                stream(s -> s.sorted().distinct().boxed()).
                exercise();

        assertUnique(result);
        if (data.size() > 0)
            assertTrue(result.size() > 0);
        else
            assertTrue(result.size() == 0);
        assertTrue(result.size() <= data.size());
    }
}
