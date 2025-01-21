/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/MemoryEaterMT.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.gctests.MemoryEaterMT.MemoryEaterMT
 */

package gc.gctests.MemoryEaterMT;

import nsk.share.gc.*;

/**
 * This test simply does Algorithms.eatMemory() in a loop
 * in multiple threads.
 */
public class MemoryEaterMT extends ThreadedGCTest {

    private class Eater implements Runnable, OOMStress {
        public void run() {
            Algorithms.eatMemory(getExecutionController());
        }
    }

    protected Runnable createRunnable(int i) {
        return new Eater();
    }

    public static void main(String args[]) {
        GC.runTest(new MemoryEaterMT(), args);
    }
}
