/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/memory/Churn/Churn1.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.memory.Churn.Churn1.Churn1
 */

package gc.memory.Churn.Churn1;

import nsk.share.test.*;
import nsk.share.gc.*;

/**
 *  Test that GC works with memory that is churn over.
 *
 *  This test starts a number of threads that create objects,
 *  keep references to them in array and overwrite them. The test
 *  test checks that GC is able to collect these objects.
 *
 *  This test will adjust the size of allocated objects to run
 *  environment.
 *
 *  @see nsk.share.gc.RunParams#getTestMemory()
 */
public class Churn1 extends ThreadedGCTest {
        private int multiplier = 10;
        private int sizeOfArray;

        class ThreadObject implements Runnable {
                private AllMemoryObject objectArray[] = new AllMemoryObject[sizeOfArray];

                public ThreadObject() {
                        for (int i = 0; i < sizeOfArray; i ++)
                                objectArray[i] = new AllMemoryObject(multiplier * i);
                }

                public void run() {
                        int index = LocalRandom.nextInt(sizeOfArray);
                        objectArray[index] = new AllMemoryObject(multiplier * index);
                }
        }

        protected Runnable createRunnable(int i) {
                return new ThreadObject();
        }
        public void run() {
                sizeOfArray = (int) Math.min(Math.sqrt(runParams.getTestMemory() * 2 / runParams.getNumberOfThreads() / multiplier), Integer.MAX_VALUE);
                super.run();
        }

        public static void main(String args[]) {
                GC.runTest(new Churn1(), args);
        }
}
