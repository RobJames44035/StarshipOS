/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8311867
 * @summary Stress test of StructuredTaskScope.shutdown with running and starting threads
 * @enablePreview
 * @run junit StressShutdown
 */

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

class StressShutdown {

    static final Callable<Void> SLEEP_FOR_A_DAY = () -> {
        Thread.sleep(Duration.ofDays(1));
        return null;
    };

    static Stream<Arguments> testCases() {
        Stream<ThreadFactory> factories = Stream.of(
                Thread.ofPlatform().factory(),
                Thread.ofVirtual().factory()
        );
        // 0..15 forks before shutdown, 0..15 forks after shutdown
        return factories.flatMap(f -> IntStream.range(0, 256)
                .mapToObj(x -> Arguments.of(f, x & 0x0F, (x & 0xF0) >> 4)));
    }

    /**
     * Test StructuredTaskScope.shutdown with running threads and concurrently with
     * threads that are starting. The shutdown should interrupt all threads so that
     * join wakes up.
     *
     * @param factory the ThreadFactory to use
     * @param beforeShutdown the number of subtasks to fork before shutdown
     * @param afterShutdown the number of subtasks to fork after shutdown
     */
    @ParameterizedTest
    @MethodSource("testCases")
    void testShutdown(ThreadFactory factory, int beforeShutdown, int afterShutdown)
        throws InterruptedException
    {
        try (var scope = new StructuredTaskScope<>(null, factory)) {
            // fork subtasks
            for (int i = 0; i < beforeShutdown; i++) {
                scope.fork(SLEEP_FOR_A_DAY);
            }

            // fork subtask to shutdown
            scope.fork(() -> {
                scope.shutdown();
                return null;
            });

            // fork after forking subtask to shutdown
            for (int i = 0; i < afterShutdown; i++) {
                scope.fork(SLEEP_FOR_A_DAY);
            }

            scope.join();
        }
    }
}
