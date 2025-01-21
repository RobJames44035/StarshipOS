/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/ThreadGC.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 * VM Testbase readme:
 * This tests attempts to stress the garbage collector my making
 * synchronous calls to the garbage collector after producing garbage.
 * The garbage collector is invoked in a separate thread.
 * The test runs for one minute (see nsk.share.runner.ThreadsRunner and
 * nsk.share.runner.RunParams. It passes if no exceptions are generated.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.gctests.ThreadGC.ThreadGC -gp random(arrays) -ms low
 */

package gc.gctests.ThreadGC;

import nsk.share.gc.*;
import nsk.share.gc.gp.*;
import java.util.*;

public class ThreadGC extends ThreadedGCTest implements GarbageProducerAware, MemoryStrategyAware {
        private GarbageProducer garbageProducer;
        private MemoryStrategy memoryStrategy;
        private Reclaimer reclaimer;
        private int count;
        private long size;

        private class Devourer implements Runnable {
                private Object[] arr = null;
                private int index;

                public void run() {
                        if (arr == null || index >= count) {
                                arr = null;
                                arr = new Object[count];
                                index = 0;
                                synchronized (reclaimer) {
                                        reclaimer.notify();
                                }
                        }
                        arr[index] = garbageProducer.create(size);
                        ++index;
                }
        }

        private class Reclaimer implements Runnable {
                private long waitTime = 1000;

                public void run() {
                        try {
                                synchronized (this) {
                                        this.wait(waitTime);
                                }
                        } catch (InterruptedException e) {
                        }
                        System.gc();
                }
        }

        protected Runnable createRunnable(int i) {
                if (i == 0)
                        return new Devourer();
                else if (i == 1) {
                        reclaimer = new Reclaimer();
                        return reclaimer;
                } else
                        return null;
        }

        public void run() {
                size = GarbageUtils.getArraySize(runParams.getTestMemory(), memoryStrategy);
                count = GarbageUtils.getArrayCount(runParams.getTestMemory(), memoryStrategy);
                runParams.setIterations(count);
                super.run();
        }

        public static void main(String[] args) {
                GC.runTest(new ThreadGC(), args);
        }

        public void setGarbageProducer(GarbageProducer garbageProducer) {
                this.garbageProducer = garbageProducer;
        }

        public void setMemoryStrategy(MemoryStrategy memoryStrategy) {
                this.memoryStrategy = memoryStrategy;
        }

}
