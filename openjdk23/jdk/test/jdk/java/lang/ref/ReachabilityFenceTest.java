/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8133348
 * @summary Tests if reachabilityFence is working
 *
 * @requires vm.opt.DeoptimizeALot != true
 *
 * @run main/othervm -Xint                           -Dpremature=false ReachabilityFenceTest
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=1 -Dpremature=true  ReachabilityFenceTest
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=2 -Dpremature=true  ReachabilityFenceTest
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=3 -Dpremature=true  ReachabilityFenceTest
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=4 -Dpremature=true  ReachabilityFenceTest
 */

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReachabilityFenceTest {

    /*
     * Implementation notes:
     *
     * This test has positive and negative parts.
     *
     * Negative test is "nonFenced", and it tests that absent of reachabilityFence, the object can
     * be prematurely finalized -- this validates the test itself. Not every VM mode is expected to
     * prematurely finalize the objects, and -Dpremature option communicates that to test. If a VM mode
     * passes the negative test, then our understanding of what could happen is correct, and we can
     * go forward.
     *
     * Positive test is "fenced", and it checks that given the reachabilityFence at the end of the block,
     * the object cannot be finalized. There is no sense running a positive test when premature finalization
     * is not expected. It is a job for negative test to verify that invariant.
     *
     * The test methods should be appropriately compiled, therefore we do several iterations and run with -Xbatch.
     */

    // Enough to OSR and compile
    static final int LOOP_ITERS = Integer.getInteger("LOOP_ITERS", 50000);

    // Enough after which to start triggering GC and finalization
    static final int WARMUP_LOOP_ITERS = LOOP_ITERS - Integer.getInteger("GC_ITERS", 100);

    // Enough to switch from an OSR'ed method to compiled method
    static final int MAIN_ITERS = 3;

    static final boolean PREMATURE_FINALIZATION = Boolean.getBoolean("premature");

    public static void main(String... args) {
        // Negative test
        boolean finalized = false;
        for (int c = 0; !finalized && c < MAIN_ITERS; c++) {
            finalized |= nonFenced();
        }

        if (PREMATURE_FINALIZATION && !finalized) {
            throw new IllegalStateException("The object had never been finalized before timeout reached.");
        }

        if (!PREMATURE_FINALIZATION && finalized) {
            throw new IllegalStateException("The object had been finalized without a fence, even though we don't expect it.");
        }

        if (!PREMATURE_FINALIZATION)
            return;

        // Positive test
        finalized = false;
        for (int c = 0; !finalized && c < MAIN_ITERS; c++) {
            finalized |= fenced();
        }

        if (finalized) {
            throw new IllegalStateException("The object had been prematurely finalized.");
        }
    }

    public static boolean nonFenced() {
        AtomicBoolean finalized = new AtomicBoolean();
        MyFinalizeable o = new MyFinalizeable(finalized);

        for (int i = 0; i < LOOP_ITERS; i++) {
            if (finalized.get()) break;
            if (i > WARMUP_LOOP_ITERS) {
                System.gc();
                System.runFinalization();
            }
        }

        return finalized.get();
    }

    public static boolean fenced() {
        AtomicBoolean finalized = new AtomicBoolean();
        MyFinalizeable o = new MyFinalizeable(finalized);

        for (int i = 0; i < LOOP_ITERS; i++) {
            if (finalized.get()) break;
            if (i > WARMUP_LOOP_ITERS) {
                System.gc();
                System.runFinalization();
            }
        }

        try {
            return finalized.get();
        } finally {
            Reference.reachabilityFence(o);
        }
    }

    private static class MyFinalizeable {
        private final AtomicBoolean finalized;

        public MyFinalizeable(AtomicBoolean b) {
            this.finalized = b;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            finalized.set(true);
        }
    }
}
