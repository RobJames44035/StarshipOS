/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/hashcode/HashCodeTestPC.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Test that verifies external hash codes.  This class tests the scenario
 * with promotion & compaction.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.hashcode.HashCodeTestPC.HashCodeTestPC
 */

package gc.hashcode.HashCodeTestPC;

import gc.hashcode.HCHelper;
import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.gc.gp.GarbageUtils;
import nsk.share.test.Stresser;


/**
 * Test that verifies external hash codes. This class tests the scenario
 * with promotion followed by compaction.
 */
public class HashCodeTestPC extends GCTestBase{


    /**
     * Test external hash codes when a promotion followed by a compaction
     * have been performed.
     *
     * @return Success if all hash codes matches original hash codes
     */
    @Override
    public void run() {
        HCHelper hch = new HCHelper(512, 2000, runParams.getSeed(),
                0.7, 10240);

        hch.setupLists();
        Stresser stresser = new Stresser(runParams.getStressOptions());
        stresser.start(0);
        GarbageUtils.eatMemory(stresser);
        if (!stresser.continueExecution()) {
            return;// we didn't trigger GC, nothing
        }
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
        GC.runTest(new HashCodeTestPC(), args);
    }
}
