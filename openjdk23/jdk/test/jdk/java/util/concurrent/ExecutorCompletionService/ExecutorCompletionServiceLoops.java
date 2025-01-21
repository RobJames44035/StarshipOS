/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

/*
 * @test
 * @bug 4965960
 * @summary  Exercise ExecutorCompletionService
 * @library /test/lib
 */

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jdk.test.lib.Utils;

public class ExecutorCompletionServiceLoops {
    static final long LONG_DELAY_MS = Utils.adjustTimeout(10_000);
    static final int POOLSIZE = 10;
    static final ExecutorService pool =
        Executors.newFixedThreadPool(POOLSIZE);
    static final ExecutorCompletionService<Integer> ecs =
        new ExecutorCompletionService<>(pool);
    static boolean print = false;

    public static void main(String[] args) throws Exception {
        int max = 8;
        int base = 2000;

        if (args.length > 0)
            max = Integer.parseInt(args[0]);

        System.out.println("Warmup...");
        oneTest(base);
        print = true;

        for (int i = 1; i <= max; i += (i+1) >>> 1) {
            System.out.print("n: " + i * base);
            oneTest(i * base);
        }
        pool.shutdown();
        if (! pool.awaitTermination(LONG_DELAY_MS, MILLISECONDS))
            throw new Error();
   }

    static class Task implements Callable<Integer> {
        public Integer call() {
            int s = 0;
            int l = System.identityHashCode(this);
            for (int i = 0; i < 5; ++i) {
                l = LoopHelpers.compute2(l);
                s += LoopHelpers.compute1(l);
            }
            return new Integer(s);
        }
    }

    static class Producer implements Runnable {
        final ExecutorCompletionService cs;
        final int iters;
        Producer(ExecutorCompletionService ecs, int i) {
            cs = ecs;
            iters = i;
        }
        public void run() {
            for (int i = 0; i < iters; ++i)
                ecs.submit(new Task());
        }
    }

    static void oneTest(int iters) throws Exception {
        long startTime = System.nanoTime();
        new Thread(new Producer(ecs, iters)).start();

        int r = 0;
        for (int i = 0; i < iters; ++i)
            r += ecs.take().get().intValue();

        long elapsed = System.nanoTime() - startTime;
        long tpi = elapsed/ iters;

        if (print)
            System.out.println("\t: " + LoopHelpers.rightJustify(tpi) + " ns per task");

        if (r == 0) // avoid overoptimization
            System.out.println("useless result: " + r);
    }

}
