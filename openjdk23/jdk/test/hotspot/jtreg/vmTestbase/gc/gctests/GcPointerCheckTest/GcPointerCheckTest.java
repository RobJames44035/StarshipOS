/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress
 *
 * @summary converted from VM Testbase gc/gctests/GcPointerCheckTest.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Test that no pointers are broken.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.gctests.GcPointerCheckTest.GcPointerCheckTest
 */

package gc.gctests.GcPointerCheckTest;

import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.ThreadedGCTest;
import nsk.share.test.ExecutionController;

/**
 * Test that no pointers are broken.
 */
public class GcPointerCheckTest extends ThreadedGCTest {

    @Override
    protected Runnable createRunnable(int i) {
        return new Test();
    }

    class Test implements Runnable {

        /**
         * Helper class for linking objects together.
         */
        private class PointerHelper {

            public PointerHelper next;
        }
        private PointerHelper first;
        ExecutionController stresser;

        @Override
        public void run() {
            if (stresser == null) {
                stresser = getExecutionController();
            }
            while (stresser.continueExecution()) {
                testGcPointers();
            }
        }

        /**
         * Create a lot of objects and link them together, then verify
         * that the pointers are pointing to the correct type of objects.
         *
         * @return success if all references points to the correct type
         * of object.
         */
        public void testGcPointers() {

            int innerIters = 1;
            int outerIters = 200;

            PointerHelper tmp1;
            PointerHelper tmp2;

            while (outerIters > 0) {
                int i = 0;
                tmp1 = new PointerHelper();
                this.first = tmp1;

                while (i != innerIters) {
                    i++;
                    tmp2 = new PointerHelper();
                    tmp1.next = tmp2;
                    tmp1 = tmp2;
                    tmp2 = new PointerHelper();
                }

                outerIters--;

                if (!checkRefs()) {
                    throw new TestFailure("Some references were bad");
                }
            }
        }

        private boolean checkRefs() {
            PointerHelper iter = this.first;

            for (int i = 0; iter != null; iter = iter.next) {
                i++;

                if (iter.getClass() != PointerHelper.class) {
                    //("GC causer bad ref on " + i);
                    return false;
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        GC.runTest(new GcPointerCheckTest(), args);
    }
}
