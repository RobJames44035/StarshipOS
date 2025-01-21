/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.util.concurrent.CyclicBarrier;

import static java.lang.System.currentTimeMillis;

public class MetaspaceTestOneArenaManyThreads extends MetaspaceTestWithThreads {

    // Several threads allocate from a single arena.
    // This mimicks several threads loading classes via the same class loader.

    public MetaspaceTestOneArenaManyThreads(MetaspaceTestContext context, long testAllocationCeiling, int numThreads, int seconds) {
        super(context, testAllocationCeiling, numThreads, seconds);
    }

    public void runTest() throws Exception {

        long t_start = currentTimeMillis();
        long t_stop = t_start + (seconds * 1000);

        // We create a single arena, and n threads which will allocate from that single arena.

        MetaspaceTestArena arena = context.createArena(RandomHelper.fiftyfifty(), testAllocationCeiling);
        CyclicBarrier gate = new CyclicBarrier(numThreads + 1);

        for (int i = 0; i < numThreads; i ++) {
            RandomAllocator allocator = new RandomAllocator(arena);
            RandomAllocatorThread thread = new RandomAllocatorThread(gate, allocator, i);
            threads[i] = thread;
            thread.start();
        }

        gate.await();

        while (System.currentTimeMillis() < t_stop) {
            Thread.sleep(200);
        }

        stopAllThreads();

        context.updateTotals();
        System.out.println("  ## Finished: " + context);

        context.checkStatistics();

        context.destroyArena(arena);

        context.purge();

        context.destroy();

        System.out.println("This took " + (System.currentTimeMillis() - t_start) + "ms");

    }

}

