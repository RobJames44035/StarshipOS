/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Stress test Thread.yield
 * @requires vm.debug != true
 * @run main YieldALot 500000
 */

/*
 * @test
 * @requires vm.debug == true
 * @run main YieldALot 200000
 */

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class YieldALot {

    public static void main(String[] args) throws Exception {
        int iterations;
        if (args.length > 0) {
            iterations = Integer.parseInt(args[0]);
        } else {
            iterations = 1_000_000;
        }

        AtomicInteger count = new AtomicInteger();
        Thread thread = Thread.ofVirtual().start(() -> {
            while (count.incrementAndGet() < iterations) {
                Thread.yield();
            }
        });

        boolean terminated;
        do {
            terminated = thread.join(Duration.ofSeconds(1));
            System.out.println(Instant.now() + " => " + count.get() + " of " + iterations);
        } while (!terminated);

        int countValue = count.get();
        if (countValue != iterations) {
            throw new RuntimeException("Thread terminated, count=" + countValue);
        }
    }
}
