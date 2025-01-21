/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/AllocateWithoutOomTest.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 *   Small stress test that should be able to run for a specified
 *   time without hitting an OOM.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      -XX:-UseGCOverheadLimit
 *      gc.gctests.AllocateWithoutOomTest.AllocateWithoutOomTest
 */

package gc.gctests.AllocateWithoutOomTest;

import java.util.ArrayList;
import java.util.Random;
import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.test.Stresser;

/**
 * Small stress test that should be able to run for a specified
 * time without hitting an OOM.
 */
public class AllocateWithoutOomTest extends GCTestBase {

    /**
     * Small stress test that allocates objects in a certain interval
     * and runs for a specified time. It should not throw any OOM during
     * the execution.
     *
     * @return success if the test runs for the specified time without
     *         and exceptions being thrown.
     */
    @Override
    public void run() {
        int minSize;
        int maxSize;



        minSize = 2048;
        maxSize = 32768;


        ArrayList placeholder = new ArrayList();
        long multiplier = maxSize - minSize;
        Random rndGenerator = new Random(runParams.getSeed());

        long memoryUpperLimit = runParams.getTestMemory();
        long memoryLowerLimit = runParams.getTestMemory() / 3;
        long memoryAllocatedLowerLimit = memoryUpperLimit
                - memoryLowerLimit;


        long totalAllocatedMemory = 0;
        long totalAllocatedObjects = 0;
        int allocationSize = -1;
        long roundCounter = 1;

        try {
            Stresser stresser = new Stresser(runParams.getStressOptions());
            stresser.start(0);
            while (stresser.continueExecution()) {
                while (totalAllocatedMemory < memoryUpperLimit) {
                    allocationSize = ((int) (rndGenerator.nextDouble()
                            * multiplier)) + minSize;
                    byte[] tmp = new byte[allocationSize];
                    totalAllocatedMemory += allocationSize;
                    totalAllocatedObjects++;
                    placeholder.add(tmp);
                    tmp = null;
                }

                // NOTE: Start on index 1 to make sure we don't remove to many
                // consecutive objects in the beginning
                int indexToRemove = 1;

                while (totalAllocatedMemory > memoryAllocatedLowerLimit) {
                    // NOTE: Terminate if we only have zero objects left
                    if (placeholder.size() == 0) {
                        throw new TestFailure("No more objects to free, "
                                + "so we can't continue");
                    }

                    if (indexToRemove >= placeholder.size()) {
                        indexToRemove = (placeholder.size() == 1) ? 0 : 1;
                    }

                    byte[] tmp = (byte[]) placeholder.remove(indexToRemove);

                    totalAllocatedMemory -= tmp.length;
                    totalAllocatedObjects--;

                    tmp = null;
                    // NOTE: Since we removed one object, we only need to
                    // increment index by 1 to move two steps. We want to
                    // remove every other object to create fragmentation
                    indexToRemove++;
                }

                roundCounter++;
            }
            placeholder = null;
            log.info("Passed. Completed " + roundCounter
                    + " rounds during the test");
        } catch (OutOfMemoryError oome) {
            placeholder = null;
            throw new TestFailure("OOM thrown when allocating an object of size "
                    + allocationSize, oome);
        } finally {
            placeholder = null;
        }
    }

    public static void main(String[] args) {
        GC.runTest(new AllocateWithoutOomTest(), args);
    }
}
