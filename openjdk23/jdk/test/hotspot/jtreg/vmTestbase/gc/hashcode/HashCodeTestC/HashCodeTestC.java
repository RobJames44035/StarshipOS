/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/hashcode/HashCodeTestC.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Test that verifies external hash codes. This class tests the scenario
 * with  FullGC (compaction).
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.hashcode.HashCodeTestC.HashCodeTestC
 */

package gc.hashcode.HashCodeTestC;

import gc.hashcode.HCHelper;
import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.test.Stresser;
import nsk.share.gc.gp.GarbageUtils;

/**
 * Test that verifies external hash codes. This class tests the scenario
 * with compaction.
 */
public class HashCodeTestC extends GCTestBase {


    /**
     * Test external hash codes when a compaction have been performed.
     *
     * @return Success if all hash codes matches original hash codes
     */
    @Override
    public void run(){
        Stresser stresser = new Stresser(runParams.getStressOptions());
        stresser.start(0);
        HCHelper hch = new HCHelper(512, 3584, runParams.getSeed(),
                0.7, 10240);

        hch.setupLists();
        hch.clearList(HCHelper.EVAC_LIST_1);
        GarbageUtils.eatMemory(stresser);
        if (!stresser.continueExecution()) {
            return;// we didn't trigger GC, nothing
        }
        boolean testResult = hch.verifyHashCodes();
        hch.cleanupLists();

        if(!testResult) {
            throw new TestFailure("Some hash codes didn't match");
        }
    }

    public static void main(String[] args) {
        GC.runTest(new HashCodeTestC(), args);
    }

}
