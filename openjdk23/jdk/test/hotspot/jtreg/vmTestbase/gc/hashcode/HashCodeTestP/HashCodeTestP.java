/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/hashcode/HashCodeTestP.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Test that verifies external hash codes.  This class tests the scenario
 * with promotion.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.hashcode.HashCodeTestP.HashCodeTestP
 */

package gc.hashcode.HashCodeTestP;

import gc.hashcode.HCHelper;
import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.gc.gp.GarbageUtils;
import nsk.share.test.Stresser;

/**
 * Test that verifies external hash codes. This class tests the scenario
 * with promotion.
 */
public class HashCodeTestP extends GCTestBase {

    /**
     * Test external hash codes when a promotion have been performed.
     *
     * @return Success if all hash codes matches original hash codes
     */
    @Override
    public final void run() {
        HCHelper hch = new HCHelper(512, 2000, runParams.getSeed(),
                0.7, 10240);

        hch.setupLists();
        Stresser stresser = new Stresser(runParams.getStressOptions());
        stresser.start(0);
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
        GC.runTest(new HashCodeTestP(), args);
    }
}
