/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/FinalizeTest04.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.gctests.FinalizeTest04.FinalizeTest04
 */

package gc.gctests.FinalizeTest04;

import nsk.share.test.*;
import nsk.share.gc.*;

/**
 * Test that synchronization between GC and finalizer thread
 * (if any) is correct.
 *
 * This test creates objects that do GC-related work in finalizer,
 * e.g. call System.gc(), System.runFinalization(), Algorithms.eatMemory().
 *
 * @see nsk.share.gc.Algorithms#eatMemory()
 * @see java.lang.System#gc()
 * @see java.lang.System#runFinalization()
 */
public class FinalizeTest04 extends GCTestBase {

    private int objectSize = 100;
    private int objectCount = 100;
    private ExecutionController stresser;

    private class FinMemoryObject2 extends FinMemoryObject {

        public FinMemoryObject2(int size) {
            super(size);
        }

        protected void finalize() {
            super.finalize();
            System.gc();
            Algorithms.eatMemory(stresser);
            System.runFinalization();
            System.gc();
            Algorithms.eatMemory(stresser);
            System.gc();
        }
    }

    public void run() {
        stresser = new Stresser(runParams.getStressOptions());
        stresser.start(runParams.getIterations());
        for (int i = 0; i < objectCount; ++i) {
            new FinMemoryObject2(objectSize);
        }
        Algorithms.eatMemory(stresser);
    }

    public static void main(String[] args) {
        GC.runTest(new FinalizeTest04(), args);
    }
}
