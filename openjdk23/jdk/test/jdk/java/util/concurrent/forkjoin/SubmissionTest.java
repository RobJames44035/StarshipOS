/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import jdk.test.lib.Utils;

/*
 * @test
 * @bug 8078490
 * @summary Test submission and execution of task without joining
 * @library /test/lib
 */
public class SubmissionTest {
    static final long LONG_DELAY_MS = Utils.adjustTimeout(10_000);

    static long millisElapsedSince(long startTime) {
        return (System.nanoTime() - startTime) / (1000L * 1000L);
    }

    public static void main(String[] args) throws Throwable {
        final ForkJoinPool e = new ForkJoinPool(1);
        final AtomicBoolean b = new AtomicBoolean();
        final Runnable setFalse = () -> b.set(false);
        for (int i = 0; i < 30_000; i++) {
            b.set(true);
            e.execute(setFalse);
            long startTime = System.nanoTime();
            while (b.get()) {
                if (millisElapsedSince(startTime) >= LONG_DELAY_MS) {
                    throw new RuntimeException("Submitted task failed to execute");
                }
                Thread.yield();
            }
        }
    }
}
