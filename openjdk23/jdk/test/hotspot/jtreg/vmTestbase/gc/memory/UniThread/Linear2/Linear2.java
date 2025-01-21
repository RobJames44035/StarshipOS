/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key stress
 *
 * @summary converted from VM Testbase gc/memory/UniThread/Linear2.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.memory.UniThread.Linear2.Linear2 -iterations 5
 */

package gc.memory.UniThread.Linear2;

import nsk.share.gc.*;
import gc.memory.UniThread.Linear1.Linear1;

/**
 * Test GC collection of linked lists.
 *
 * This test simply creates a series of singly
 * linked memory objects which should be able to be
 * GC'd.
 *
 * In this test the size of one object is medium, the number
 * of objects in one list is medium and the number
 * of lists is large.
 */

public class Linear2 {
        public static void main(String args[]) {
                int circularitySize = 100;
                int objectSize = 1000;
                GC.runTest(new Linear1(objectSize, circularitySize), args);
        }
}
