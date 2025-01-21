/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * @test
 * @summary Tests the API and contract of Gatherers.fold
 * @run junit GatherersFoldTest
 */

public class GatherersFoldTest {

    record Config(int streamSize, boolean parallel) {
        Stream<Integer> stream() {
            var stream = Stream.iterate(1, i -> i + 1).limit(streamSize);
            stream = parallel ? stream.parallel() : stream.sequential();
            return stream;
        }
    }

    static final Stream<Integer> sizes(){
        return Stream.of(0,1,10,33,99,9999);
    }

    static final Stream<Config> sequentialAndParallel(int size) {
        return Stream.of(false, true)
                .map(parallel ->
                        new Config(size, parallel));
    }

    static final Stream<Config> configurations() {
        return sizes().flatMap(i -> sequentialAndParallel(i));
    }

    @Test
    public void throwsNPEWhenStateSupplierIsNull() {
        assertThrows(NullPointerException.class, () -> Gatherers.<String, String>fold(null, (state, next) -> state));
    }

    @Test
    public void throwsNPEWhenFolderFunctionIsNull() {
        assertThrows(NullPointerException.class, () -> Gatherers.<String, String>fold(() -> "", null));
    }

    @ParameterizedTest
    @MethodSource("configurations")
    public void behavesAsExpected(Config config) {
        final var expectedResult = List.of(
            config.stream()
                    .sequential()
                    .reduce(0L, (acc, next) -> acc + next,(l, r) -> {
                        throw new IllegalStateException();
                    })
        );

        final var result = config.stream()
                .gather(Gatherers.fold(() -> 0L, (acc, next) -> acc + next))
                .toList();

        assertEquals(expectedResult, result);
    }
}
