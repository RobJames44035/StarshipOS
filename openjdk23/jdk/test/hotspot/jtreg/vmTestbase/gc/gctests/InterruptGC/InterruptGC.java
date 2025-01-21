/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/InterruptGC.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 * VM Testbase readme:
 * In this test, threads perform garbage collection while constantly
 * interrupting each other. Another thread generates the garbage.
 * The test runs for approximately one minute (see nsk.share.runner.ThreadsRunner
 * and nsk.share.runner.RunParams). The test passes if no exceptions are generated.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.gctests.InterruptGC.InterruptGC -gp random(arrays) -ms low
 */

package gc.gctests.InterruptGC;

import nsk.share.gc.*;
import nsk.share.test.*;
import nsk.share.gc.gp.*;

import java.util.*;

/**
 * The test starts one thread which generates garbage and several other
 * thread which continuously do System.gc() and interrupt each other.
 */
public class InterruptGC extends ThreadedGCTest implements GarbageProducerAware, MemoryStrategyAware {
        private GarbageProducer garbageProducer;
        private MemoryStrategy memoryStrategy;
        private List<Interrupter> interrupters = new ArrayList<Interrupter>();
        private int count;
        private long size;

        private class GarbageCreator implements Runnable {
                public void run() {
                        Object[] arr = new Object[count];
                        for (int i = 0; i < count && getExecutionController().continueExecution(); ++i)
                                arr[i] = garbageProducer.create(size);
                }
        }

        private class Interrupter implements Runnable {
                private Thread thread;

                public void run() {
                        if (thread == null)
                                thread = Thread.currentThread();
                        Interrupter interrupter = interrupters.get(LocalRandom.nextInt(interrupters.size()));
                        Thread thread = interrupter.getThread();
                        if (thread != null)
                                thread.interrupt();
                        System.gc();
                }

                public Thread getThread() {
                        return thread;
                }
        }

        protected Runnable createRunnable(int i) {
                switch (i) {
                case 0:
                        return new GarbageCreator();
                default:
                        Interrupter interrupter = new Interrupter();
                        interrupters.add(interrupter);
                        return interrupter;
                }
        }

        public void run() {
                size = GarbageUtils.getArraySize(runParams.getTestMemory(), memoryStrategy);
                count = GarbageUtils.getArrayCount(runParams.getTestMemory(), memoryStrategy);
                super.run();
        }

        public void setGarbageProducer(GarbageProducer garbageProducer) {
                this.garbageProducer = garbageProducer;
        }

        public void setMemoryStrategy(MemoryStrategy memoryStrategy) {
                this.memoryStrategy = memoryStrategy;
        }

        public static void main(String[] args) {
                GC.runTest(new InterruptGC(), args);
        }
}
