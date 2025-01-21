/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8303742
 * @summary CompletableFuture.orTimeout can leak memory if completed exceptionally
 * @modules java.base/java.util.concurrent:open
 * @run junit/othervm -Xmx128m CompletableFutureOrTimeoutExceptionallyTest
 */

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompletableFutureOrTimeoutExceptionallyTest {
    static final BlockingQueue<Runnable> delayerQueue;
    static {
        try {
            var delayerClass = Class.forName("java.util.concurrent.CompletableFuture$Delayer",
                                             true,
                                             CompletableFuture.class.getClassLoader());
            var delayerField = delayerClass.getDeclaredField("delayer");
            delayerField.setAccessible(true);
            delayerQueue = ((ScheduledThreadPoolExecutor)delayerField.get(null)).getQueue();
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    /**
     * Test that orTimeout task is cancelled if the CompletableFuture is completed Exceptionally
     */
    @Test
    void testOrTimeoutWithCompleteExceptionallyDoesNotLeak() throws InterruptedException {
        assertTrue(delayerQueue.peek() == null);
        var future = new CompletableFuture<>().orTimeout(12, TimeUnit.HOURS);
        assertTrue(delayerQueue.peek() != null);
        future.completeExceptionally(new RuntimeException("This is fine"));
        while (delayerQueue.peek() != null) {
            Thread.sleep(100);
        };
    }

    /**
     * Test that the completeOnTimeout task is cancelled if the CompletableFuture is completed Exceptionally
     */
    @Test
    void testCompleteOnTimeoutWithCompleteExceptionallyDoesNotLeak() throws InterruptedException {
        assertTrue(delayerQueue.peek() == null);
        var future = new CompletableFuture<>().completeOnTimeout(null, 12, TimeUnit.HOURS);
        assertTrue(delayerQueue.peek() != null);
        future.completeExceptionally(new RuntimeException("This is fine"));
        while (delayerQueue.peek() != null) {
            Thread.sleep(100);
        };
    }
}
