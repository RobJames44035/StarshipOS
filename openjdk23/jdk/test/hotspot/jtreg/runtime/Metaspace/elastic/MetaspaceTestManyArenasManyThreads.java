/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
import java.util.concurrent.CyclicBarrier;

import static java.lang.System.currentTimeMillis;

public class MetaspaceTestManyArenasManyThreads extends MetaspaceTestWithThreads {

    // Several threads allocate from a single arena.
    // This mimicks several threads loading classes via the same class loader.

    public MetaspaceTestManyArenasManyThreads(MetaspaceTestContext context, long testAllocationCeiling, int numThreads, int seconds) {
        super(context, testAllocationCeiling, numThreads, seconds);
    }

    public void runTest() throws Exception {

        long t_start = currentTimeMillis();
        long t_stop = t_start + (seconds * 1000);

        CyclicBarrier gate = new CyclicBarrier(numThreads + 1);

        final long ceilingPerThread = testAllocationCeiling / numThreads;

        for (int i = 0; i < numThreads; i ++) {
            // Create n test threads, each one with its own allocator/arena pair
            MetaspaceTestArena arena = context.createArena(RandomHelper.fiftyfifty(), ceilingPerThread);
            RandomAllocator allocator = new RandomAllocator(arena);
            RandomAllocatorThread thread = new RandomAllocatorThread(gate, allocator, i);
            threads[i] = thread;
            thread.start();
        }

        gate.await();

        // while test is running, skim the arenas and kill any arena which is saturated (has started getting an
        // untoward number of allocation failures)
        while (System.currentTimeMillis() < t_stop) {

            // Wait a bit
            Thread.sleep(200);

            for (RandomAllocatorThread t: threads) {
                if (t.allocator.arena.numAllocationFailures > 1000) {
                    t.interrupt();
                    t.join();
                    context.destroyArena(t.allocator.arena);

                    // Create a new arena, allocator, then a new thread (note: do not pass in a start gate this time
                    // since we do not need to wait) and fire it up.
                    MetaspaceTestArena arena = context.createArena(RandomHelper.fiftyfifty(), ceilingPerThread);
                    RandomAllocator allocator = new RandomAllocator(arena);
                    RandomAllocatorThread t2 = new RandomAllocatorThread(null, allocator, t.id);
                    threads[t.id] = t2;
                    t2.start();
                }
            }

        }

        // Stop all threads.
        stopAllThreads();

        // At this point a large number of Arenas will have died (see above), but we probably still have
        //  some live arenas left. The chunk freelist will be full of free chunks. Maybe a bit fragmented,
        //  with a healthy mixture of larger and smaller chunks, since we still have live arenas.
        // These chunks are all committed still, since we did nothing to reclaim the storage. We now purge
        //  the context manually to uncommit those chunks, in order to get a realistic number for
        //  committed words (see checkStatistics()).
        // Note: In real metaspace, this happens as part of the same GC which removes class loaders and
        //  frees their metaspace arenas. All within CLDG::purge(). But since this test isolates the metaspace
        //  context and does test it separately, GC and CLDG are not involved here. We need to purge manually.
        //
        // Purging uncommits all free chunks >= 64K
        context.purge();

        context.updateTotals();
        System.out.println("  ## Finished: " + context);

        context.checkStatistics();

        // Destroy all arenas; then purge the space.
        destroyArenasAndPurgeSpace();

        context.destroy();

        context.updateTotals();

        System.out.println("This took " + (System.currentTimeMillis() - t_start) + "ms");

    }

}

