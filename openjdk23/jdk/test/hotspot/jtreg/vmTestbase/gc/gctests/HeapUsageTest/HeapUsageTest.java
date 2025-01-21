/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress
 *
 * @summary converted from VM Testbase gc/gctests/HeapUsageTest.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Originally it was Micro benchmark that tests the heap usage.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.gctests.HeapUsageTest.HeapUsageTest
 */

package gc.gctests.HeapUsageTest;

import java.util.ArrayList;
import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.test.Stresser;

/**
 * Micro benchmark that tests the heap usage.
 */
public class HeapUsageTest extends GCTestBase {

    /**
     *  Helper class to store allocation size and iterations for the
     *  HeapUsageTest heap usage test program
     */
    private class TestValue {

        private int allocationSize;
        private int allocationIterations;

        TestValue(int allocSize, int allocIters) {
            allocationSize = allocSize;
            allocationIterations = allocIters;
        }

        final int getSize() {
            return allocationSize;
        }

        final int getIterations() {
            return allocationIterations;
        }
    }

    /**
     * Simple micro benchmark for testing heap usage. Returns a percentage
     * that tells how much of the total heap size the test was able to
     * allocate.
     *
     * @return success if test could run until OOME was thrown, and was
     * able to determine the heap usage in percent without any other
     * exceptions being thrown.
     */
    public void run() {

        try {
            int[] testParams =
                    new int[]{512, 5, 2048, 3, 3145728, 2};


            TestValue[] values = new TestValue[testParams.length / 2];
            for (int i = 0; i < testParams.length / 2; i++) {
                values[i] = new TestValue(testParams[i * 2],
                        testParams[i * 2 + 1]);
            }

            // NOTE: The call to Runtime might not look like it does anything
            // here, but it codegens the class, so it will not OOM later on
            // due to low-mem sitation for codegen.
            Runtime r = Runtime.getRuntime();
            // NOTE: Codegen freeMemory() and maxMemory() so this
            // doesn't cause OOM in a OOM situation
            ArrayList holdObjects = new ArrayList();
            long currentAllocatedSize = 0;
            Stresser stresser = new Stresser(runParams.getStressOptions());
            stresser.start(0);
            try {
                long loopCount;
                int nrOfLoops = 0;

                for (int i = 0; i < values.length; i++) {
                    if (values[i].getIterations() > nrOfLoops) {
                        nrOfLoops = values[i].getIterations();
                    }
                }

                for (loopCount = 0;; loopCount++) {
                    for (int i = 0; i < nrOfLoops; i++) {
                        for (int k = 0; k < values.length; k++) {
                            if (i < values[k].getIterations()) {
                                if (!stresser.continueExecution()) {
                                    // no time to eat all heap
                                    return;
                                }
                                byte[] tmp = new byte[values[k].getSize()];
                                holdObjects.add(tmp);
                                currentAllocatedSize += (long) values[k].getSize();
                            }
                        }
                    }
                }
            } catch (OutOfMemoryError oome) {
                long oomMaxMemory = r.maxMemory();

                holdObjects = null;

                double myPercentUsed =
                        (((double) (currentAllocatedSize))
                        / oomMaxMemory) * 100;

                log.info("Heap usage percentage ( "
                        + myPercentUsed + " %) " + myPercentUsed);
            } finally {
                // NOTE: In case OOM wasn't hit, release references
                // and cleanup by calling System.gc();
                holdObjects = null;
            }
        } catch (OutOfMemoryError oome2) {
            throw new TestFailure("OutOfMemoryError thrown even though it shouldn't. "
                    + "Please investigate.", oome2);
        }
    }

    public static void main(String[] args) {
        GC.runTest(new HeapUsageTest(), args);
    }
}
