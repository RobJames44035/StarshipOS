/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/memory/UniThread/Circular4.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.memory.UniThread.Circular4.Circular4 -iterations 5
 */

package gc.memory.UniThread.Circular4;

import nsk.share.gc.*;
import gc.memory.UniThread.Circular3.Circular3;

/**
 * Test GC collection of circular linked lists.
 *
 * This test simply creates a series of circulary
 * linked memory objects which should be able to be
 * GC'd.
 *
 * In this test the size of one object is medium, the number
 * of objects in one list is medium and the number
 * of lists is large. Also, the order in which references
 * are cleared is randomized.
 */

public class Circular4 {
        public static void main(String args[]) {
                int circularitySize = 100;
                int objectSize = 10000;
                GC.runTest(new Circular3(objectSize, circularitySize), args);
        }
}
