/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @run testng AsyncShutdownNowInvokeAny
 * @summary A variant of AsyncShutdownNow useful for race bug hunting
 */

// TODO: reorganize all of the AsyncShutdown tests

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class AsyncShutdownNowInvokeAny {

    // long running interruptible task
    private static final Callable<Void> SLEEP_FOR_A_DAY = () -> {
        Thread.sleep(86400_000);
        return null;
    };

    private ScheduledExecutorService scheduledExecutor;

    @BeforeClass
    public void setup() {
        scheduledExecutor = Executors.newScheduledThreadPool(1);
    }

    @AfterClass
    public void teardown() {
        scheduledExecutor.shutdown();
    }

    /**
     * Schedule the given executor service to be shutdown abruptly after the given
     * delay, in seconds.
     */
    private void scheduleShutdownNow(ExecutorService executor, int delayInSeconds) {
        scheduledExecutor.schedule(() -> {
            executor.shutdownNow();
            return null;
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Test shutdownNow with thread blocked in invokeAny.
     */
    @Test
    public void testInvokeAny() throws Exception {
        final int reps = 4;
        for (int rep = 1; rep < reps; rep++) {
            ExecutorService pool = new ForkJoinPool(1);
            scheduleShutdownNow(pool, 5);
            try {
                try {
                    // execute long running tasks
                    pool.invokeAny(List.of(SLEEP_FOR_A_DAY, SLEEP_FOR_A_DAY));
                    assertTrue(false);
                } catch (ExecutionException | RejectedExecutionException e) {
                    // expected
                }
            } finally {
                pool.shutdown();
            }
        }
    }
}
