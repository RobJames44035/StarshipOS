/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.stream.*;

import org.testng.annotations.Test;

public class PrimitiveAverageOpTest extends OpTestCase {

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfInt data) {
        exerciseTerminalOps(data, s -> s.average());
    }

    @Test(dataProvider = "LongStreamTestData", dataProviderClass = LongStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfLong data) {
        exerciseTerminalOps(data, s -> s.average());
    }

    // @@@ For Double depending on the input data the average algorithm may produce slightly
    //     different results for the sequential and parallel evaluation.results are within
    //     While the following works at the moment, it could change when double data, not cast from long
    //     values is introduced, or if the average/sum algorithm is modified.
    @Test(dataProvider = "DoubleStreamTestData", dataProviderClass = DoubleStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfDouble data) {
        exerciseTerminalOps(data, s -> s.average());
    }

}
