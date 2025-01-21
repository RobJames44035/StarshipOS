/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key stress
 *
 * @summary converted from VM Testbase gc/memory/UniThread/Circular1.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.memory.UniThread.Circular1.Circular1 -iterations 5
 */

package gc.memory.UniThread.Circular1;

import nsk.share.gc.*;
import nsk.share.test.Stresser;

/**
 * Test GC collection of circular linked lists.
 *
 * This test simply creates a series of circulary
 * linked memory objects which should be able to be
 * GC'd.
 *
 * In this test the size of one object is small, the number
 * of objects in one list is small and the number
 * of lists is large.
 */
public class Circular1 extends GCTestBase {
        private int arraySize;
        private int objectSize;
        private int circularitySize;
        private MemoryObject memoryArray[];

        public Circular1() {
                this(100, 5);
        }

        public Circular1(int objectSize, int circularitySize) {
                this.objectSize = objectSize;
                this.circularitySize = circularitySize;
        }

        public void run() {
                arraySize = Memory.getArrayLength(runParams.getTestMemory(), Memory.getListSize(circularitySize, objectSize));
                Stresser stresser = new Stresser(runParams.getStressOptions());
                log.debug("Array size: " + arraySize);
                memoryArray = new MemoryObject[arraySize];
                stresser.start(runParams.getIterations());
                try {
                        while (stresser.iteration()) {
                                log.debug(Runtime.getRuntime().freeMemory());
                                for (int i = 0; i < arraySize && stresser.continueExecution(); i ++)
                                        memoryArray[i] = Memory.makeCircularList(circularitySize, objectSize);
                        }
                } finally {
                        stresser.finish();
                }
                System.out.println("Test passed.");
        }

        public static void main(String args[]) {
                GC.runTest(new Circular1(), args);
        }
}
