/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @summary Tests counting of streams
 * @bug 8031187 8067969 8075307 8265029
 */

package org.openjdk.tests.java.util.stream;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.DoubleStreamTestDataProvider;
import java.util.stream.IntStream;
import java.util.stream.IntStreamTestDataProvider;
import java.util.stream.LongStream;
import java.util.stream.LongStreamTestDataProvider;
import java.util.stream.OpTestCase;
import java.util.stream.Stream;
import java.util.stream.StreamTestDataProvider;
import java.util.stream.TestData;

import org.testng.annotations.Test;

public class CountTest extends OpTestCase {

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOps(String name, TestData.OfRef<Integer> data) {
        long expectedCount = data.size();

        withData(data).
                terminal(Stream::count).
                expectedResult(expectedCount).
                exercise();

        withData(data).
                terminal(s -> s.skip(1), Stream::count).
                expectedResult(Math.max(0, expectedCount - 1)).
                exercise();

        // Test with an unknown sized stream
        withData(data).
                terminal(s -> s.filter(e -> true), Stream::count).
                expectedResult(expectedCount).
                exercise();

        // Test counting collector
        withData(data).
                terminal(s -> s, s -> s.collect(Collectors.counting())).
                expectedResult(expectedCount).
                exercise();

        // Test with stateful distinct op that is a barrier or lazy
        // depending if source is not already distinct and encounter order is
        // preserved or not
        expectedCount = data.into(new HashSet<>()).size();
        withData(data).
                terminal(Stream::distinct, Stream::count).
                expectedResult(expectedCount).
                exercise();
        withData(data).
                terminal(s -> s.unordered().distinct(), Stream::count).
                expectedResult(expectedCount).
                exercise();
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfInt data) {
        long expectedCount = data.size();

        withData(data).
                terminal(IntStream::count).
                expectedResult(expectedCount).
                exercise();

        withData(data).
            terminal(s -> s.skip(1), IntStream::count).
            expectedResult(Math.max(0, expectedCount - 1)).
            exercise();

        withData(data).
                terminal(s -> s.filter(e -> true), IntStream::count).
                expectedResult(expectedCount).
                exercise();

        expectedCount = data.into(new HashSet<>()).size();
        withData(data).
                terminal(IntStream::distinct, IntStream::count).
                expectedResult(expectedCount).
                exercise();
        withData(data).
                terminal(s -> s.unordered().distinct(), IntStream::count).
                expectedResult(expectedCount).
                exercise();
    }

    @Test(dataProvider = "LongStreamTestData", dataProviderClass = LongStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfLong data) {
        long expectedCount = data.size();

        withData(data).
                terminal(LongStream::count).
                expectedResult(expectedCount).
                exercise();

        withData(data).
            terminal(s -> s.skip(1), LongStream::count).
            expectedResult(Math.max(0, expectedCount - 1)).
            exercise();

        withData(data).
                terminal(s -> s.filter(e -> true), LongStream::count).
                expectedResult(expectedCount).
                exercise();

        expectedCount = data.into(new HashSet<>()).size();
        withData(data).
                terminal(LongStream::distinct, LongStream::count).
                expectedResult(expectedCount).
                exercise();
        withData(data).
                terminal(s -> s.unordered().distinct(), LongStream::count).
                expectedResult(expectedCount).
                exercise();
    }

    @Test(dataProvider = "DoubleStreamTestData", dataProviderClass = DoubleStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfDouble data) {
        long expectedCount = data.size();

        withData(data).
                terminal(DoubleStream::count).
                expectedResult(expectedCount).
                exercise();

        withData(data).
            terminal(s -> s.skip(1), DoubleStream::count).
            expectedResult(Math.max(0, expectedCount - 1)).
            exercise();

        withData(data).
                terminal(s -> s.filter(e -> true), DoubleStream::count).
                expectedResult(expectedCount).
                exercise();

        expectedCount = data.into(new HashSet<>()).size();
        withData(data).
                terminal(DoubleStream::distinct, DoubleStream::count).
                expectedResult(expectedCount).
                exercise();
        withData(data).
                terminal(s -> s.unordered().distinct(), DoubleStream::count).
                expectedResult(expectedCount).
                exercise();
    }

    @Test
    public void testNoEvaluationForSizedStream() {
        checkStreamDoesNotConsumeElements(() -> Stream.of(1, 2, 3, 4), 4);
        checkStreamDoesNotConsumeElements(() -> Stream.of(1, 2, 3, 4).skip(1).limit(2).skip(1), 1);
        checkIntStreamDoesNotConsumeElements(() -> IntStream.of(1, 2, 3, 4), 4);
        checkIntStreamDoesNotConsumeElements(() -> IntStream.of(1, 2, 3, 4).skip(1).limit(2).skip(1), 1);
        checkLongStreamDoesNotConsumeElements(() -> LongStream.of(1, 2, 3, 4), 4);
        checkLongStreamDoesNotConsumeElements(() -> LongStream.of(1, 2, 3, 4).skip(1).limit(2).skip(1), 1);
        checkDoubleStreamDoesNotConsumeElements(() -> DoubleStream.of(1, 2, 3, 4), 4);
        checkDoubleStreamDoesNotConsumeElements(() -> DoubleStream.of(1, 2, 3, 4).skip(1).limit(2).skip(1), 1);
    }

    private void checkStreamDoesNotConsumeElements(Supplier<Stream<?>> supplier, long expectedCount) {
        AtomicInteger ai = new AtomicInteger();
        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).count(), expectedCount);
        assertEquals(ai.get(), 0);

        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).parallel().count(), expectedCount);
        assertEquals(ai.get(), 0);
    }

    private void checkIntStreamDoesNotConsumeElements(Supplier<IntStream> supplier, long expectedCount) {
        AtomicInteger ai = new AtomicInteger();
        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).count(), expectedCount);
        assertEquals(ai.get(), 0);

        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).parallel().count(), expectedCount);
        assertEquals(ai.get(), 0);
    }

    private void checkLongStreamDoesNotConsumeElements(Supplier<LongStream> supplier, long expectedCount) {
        AtomicInteger ai = new AtomicInteger();
        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).count(), expectedCount);
        assertEquals(ai.get(), 0);

        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).parallel().count(), expectedCount);
        assertEquals(ai.get(), 0);
    }

    private void checkDoubleStreamDoesNotConsumeElements(Supplier<DoubleStream> supplier, long expectedCount) {
        AtomicInteger ai = new AtomicInteger();
        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).count(), expectedCount);
        assertEquals(ai.get(), 0);

        assertEquals(supplier.get().peek(e -> ai.getAndIncrement()).parallel().count(), expectedCount);
        assertEquals(ai.get(), 0);
    }
}
