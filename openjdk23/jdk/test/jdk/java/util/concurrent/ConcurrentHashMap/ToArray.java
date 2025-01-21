/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4486658 8010293
 * @summary thread safety of toArray methods of collection views
 * @author Martin Buchholz
 */

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ToArray {

    public static void main(String[] args) throws Throwable {
        final int runsPerTest = Integer.getInteger("jsr166.runsPerTest", 1);
        final int reps = 10 * runsPerTest;
        for (int i = reps; i--> 0; )
            executeTest();
    }

    static void executeTest() throws Throwable {
        try (var executor = Executors.newCachedThreadPool()) {
            final ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
            final ThreadLocalRandom rnd = ThreadLocalRandom.current();
            final int nCPU = Runtime.getRuntime().availableProcessors();
            final int minWorkers = 2;
            final int maxWorkers = Math.max(minWorkers, Math.min(32, nCPU));
            final int nWorkers = rnd.nextInt(minWorkers, maxWorkers + 1);
            final int sizePerWorker = 1024;
            final int maxSize = nWorkers * sizePerWorker;

            // The foreman busy-checks that the size of the arrays obtained
            // from the keys and values views grows monotonically until it
            // reaches the maximum size.

            // NOTE: these size constraints are not specific to toArray and are
            // applicable to any form of traversal of the collection views
            CompletableFuture<?> foreman = CompletableFuture.runAsync(new Runnable() {
                private int prevSize = 0;

                private boolean checkProgress(Object[] a) {
                    int size = a.length;
                    if (size < prevSize || size > maxSize)
                        throw new AssertionError(
                                String.format("prevSize=%d size=%d maxSize=%d",
                                        prevSize, size, maxSize));
                    prevSize = size;
                    return size == maxSize;
                }

                public void run() {
                    Integer[] empty = new Integer[0];
                    for (; ; )
                        if (checkProgress(m.values().toArray())
                                & checkProgress(m.keySet().toArray())
                                & checkProgress(m.values().toArray(empty))
                                & checkProgress(m.keySet().toArray(empty)))
                            return;
                }
            }, executor);

            // Each worker puts globally unique keys into the map
            List<CompletableFuture<?>> workers =
                    IntStream.range(0, nWorkers)
                            .mapToObj(w -> (Runnable) () -> {
                                for (int i = 0, o = w * sizePerWorker; i < sizePerWorker; i++)
                                    m.put(o + i, i);
                            })
                            .map(r -> CompletableFuture.runAsync(r, executor))
                            .collect(Collectors.toList());

            // Wait for workers and foreman to complete
            workers.forEach(CompletableFuture<?>::join);
            foreman.join();
        }
    }
}
