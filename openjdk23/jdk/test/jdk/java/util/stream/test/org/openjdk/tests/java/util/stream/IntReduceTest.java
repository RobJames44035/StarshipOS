/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.stream.IntStream;
import java.util.stream.IntStreamTestDataProvider;
import java.util.stream.OpTestCase;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.TestData;

import static java.util.stream.LambdaTestHelpers.*;

public class IntReduceTest extends OpTestCase {
    public void testReduce() {
        int[] a = IntStream.range(1, 11).toArray();

        assertEquals(55, Arrays.stream(a).reduce(irPlus).getAsInt());
        assertEquals(55, Arrays.stream(a).reduce(0, irPlus));
        assertEquals(10, Arrays.stream(a).reduce(irMax).getAsInt());
        assertEquals(1, Arrays.stream(a).reduce(irMin).getAsInt());

        assertEquals(0, IntStream.empty().reduce(0, irPlus));
        assertFalse(IntStream.empty().reduce(irPlus).isPresent());

        assertEquals(110, Arrays.stream(a).map(irDoubler).reduce(irPlus).getAsInt());
        assertEquals(20, Arrays.stream(a).map(irDoubler).reduce(irMax).getAsInt());
        assertEquals(2, Arrays.stream(a).map(irDoubler).reduce(irMin).getAsInt());
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfInt data) {
        assertEquals(0, (int) exerciseTerminalOps(data, s -> s.filter(ipFalse), s -> s.reduce(0, irPlus)));

        OptionalInt seedless = exerciseTerminalOps(data, s -> s.reduce(irPlus));
        int folded = exerciseTerminalOps(data, s -> s.reduce(0, irPlus));
        assertEquals(folded, seedless.orElse(0));

        seedless = exerciseTerminalOps(data, s -> s.reduce(irMin));
        folded = exerciseTerminalOps(data, s -> s.reduce(Integer.MAX_VALUE, irMin));
        assertEquals(folded, seedless.orElse(Integer.MAX_VALUE));

        seedless = exerciseTerminalOps(data, s -> s.reduce(irMax));
        folded = exerciseTerminalOps(data, s -> s.reduce(Integer.MIN_VALUE, irMax));
        assertEquals(folded, seedless.orElse(Integer.MIN_VALUE));

        seedless = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(irPlus));
        folded = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(0, irPlus));
        assertEquals(folded, seedless.orElse(0));

        seedless = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(irMin));
        folded = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(Integer.MAX_VALUE, irMin));
        assertEquals(folded, seedless.orElse(Integer.MAX_VALUE));

        seedless = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(irMax));
        folded = exerciseTerminalOps(data, s -> s.map(irDoubler), s -> s.reduce(Integer.MIN_VALUE, irMax));
        assertEquals(folded, seedless.orElse(Integer.MIN_VALUE));
    }
}
