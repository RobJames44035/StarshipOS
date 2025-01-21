/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.LambdaTestHelpers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


/**
 * ToListOpTest
 */
@Test
public class ToListOpTest extends OpTestCase {

    public void testToList() {
        assertCountSum(countTo(0).stream().toList(), 0, 0);
        assertCountSum(countTo(10).stream().toList(), 10, 55);
    }

    private void checkUnmodifiable(List<Integer> list) {
        try {
            list.add(Integer.MIN_VALUE);
            fail("List.add did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ignore) { }

        if (list.size() > 0) {
            try {
                list.set(0, Integer.MAX_VALUE);
                fail("List.set did not throw UnsupportedOperationException");
            } catch (UnsupportedOperationException ignore) { }
        }
    }

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOps(String name, TestData.OfRef<Integer> data) {
        List<Integer> objects = exerciseTerminalOps(data, s -> s.toList());
        checkUnmodifiable(objects);
        assertFalse(objects.contains(null));
    }

    @Test(dataProvider = "withNull:StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOpsWithNull(String name, TestData.OfRef<Integer> data) {
        List<Integer> objects = exerciseTerminalOps(data, s -> s.toList());
        checkUnmodifiable(objects);
        assertTrue(objects.contains(null));
    }

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testDefaultOps(String name, TestData.OfRef<Integer> data) {
        List<Integer> objects = exerciseTerminalOps(data, s -> DefaultMethodStreams.delegateTo(s).toList());
        checkUnmodifiable(objects);
        assertFalse(objects.contains(null));
    }

    @Test(dataProvider = "withNull:StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testDefaultOpsWithNull(String name, TestData.OfRef<Integer> data) {
        List<Integer> objects = exerciseTerminalOps(data, s -> DefaultMethodStreams.delegateTo(s).toList());
        checkUnmodifiable(objects);
        assertTrue(objects.contains(null));
    }

}
