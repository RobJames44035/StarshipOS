/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/CallGC/CallGC02.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.gctests.CallGC.CallGC02.CallGC02 -t 100 -gp random(arrays)
 */

package gc.gctests.CallGC.CallGC02;

import nsk.share.gc.*;
import nsk.share.gc.gp.*;
import nsk.share.runner.*;

/**
 * This test starts a number of threads that do System.gc() and
 * System.runFinalization() and checks that there are no crashes.
 *
 * There is also a thread that produces garbage.
 */
public class CallGC02 extends ThreadedGCTest implements GarbageProducerAware {
        private GarbageProducer garbageProducer;
        private final int objectSize = 100;

        private class GarbageProduction implements Runnable {
                public void run() {
                        garbageProducer.create(objectSize);
                }
        }

        protected Runnable createRunnable(int i) {
                if (i == 0)
                        return new GarbageProduction();
                else if (i % 2 == 0)
                        return new GCRunner();
                else
                        return new FinRunner();
        }

        public final void setGarbageProducer(GarbageProducer garbageProducer) {
                this.garbageProducer = garbageProducer;
        }

        public static void main(String[] args) {
                GC.runTest(new CallGC02(), args);
        }
}
