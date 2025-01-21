/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Stress test Thread.getStackTrace on virtual threads that are blocking or
 *     blocked on monitorenter
 * @requires vm.debug != true
 * @modules jdk.management
 * @library /test/lib
 * @run main/othervm/timeout=300 GetStackTraceALotWhenBlocking 100000
 */

/*
 * @test
 * @requires vm.debug == true & vm.continuations
 * @modules jdk.management
 * @library /test/lib
 * @run main/othervm/timeout=300 GetStackTraceALotWhenBlocking 50000
 */

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import jdk.test.lib.Platform;
import jdk.test.lib.thread.VThreadRunner;   // ensureParallelism requires jdk.management

public class GetStackTraceALotWhenBlocking {

    public static void main(String[] args) throws Exception {
        // need at least two carriers
        VThreadRunner.ensureParallelism(2);

        int iterations;
        int value = Integer.parseInt(args[0]);
        if (Platform.isOSX() && Platform.isX64()) {
            // reduced iterations on macosx-x64
            iterations = Math.max(value / 4, 1);
        } else {
            iterations = value;
        }

        var done = new AtomicBoolean();
        var lock = new Object();

        Runnable task = () -> {
            long count = 0L;
            while (!done.get()) {
                synchronized (lock) {
                    pause();
                }
                count++;
            }
            System.out.format("%s %s => %d loops%n", Instant.now(), Thread.currentThread(), count);
        };

        var thread1 = Thread.ofVirtual().start(task);
        var thread2 = Thread.ofVirtual().start(task);
        long lastTime = System.nanoTime();
        try {
            for (int i = 1; i <= iterations; i++) {
                thread1.getStackTrace();
                pause();
                thread2.getStackTrace();
                pause();

                long currentTime = System.nanoTime();
                if (i == iterations || ((currentTime - lastTime) > 1_000_000_000L)) {
                    System.out.format("%s => %d of %d%n", Instant.now(), i, iterations);
                    lastTime = currentTime;
                }

                if (Thread.currentThread().isInterrupted()) {
                    // fail quickly if interrupted by jtreg
                    throw new RuntimeException("interrupted");
                }
            }
        } finally {
            done.set(true);
            thread1.join();
            thread2.join();
        }
    }

    private static void pause() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            Thread.onSpinWait();
        } else {
            Thread.yield();
        }
    }
}
