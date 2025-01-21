/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/StringIntern.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.gctests.StringIntern.StringIntern
 */

package gc.gctests.StringIntern;

import nsk.share.test.*;
import nsk.share.gc.*;

/**
 * Test that strings returned by String.intern() can be collected.
 *
 * Create strings consisting of random characters, call String.intern().
 * Check that intern() contract.
 */
public class StringIntern extends ThreadedGCTest {
        private int maxLength = 1000;
        private int checkCount = 100;

        private class StringGenerator implements Runnable {
                private StringBuffer sb = new StringBuffer();

                private void generateRandomBuffer() {
                        int length = LocalRandom.nextInt(maxLength);
                        for (int i = 0; i < length; ++i)
                                sb.append((char) LocalRandom.nextInt(Integer.MAX_VALUE));
                }

                private String getString() {
                        return sb.toString();
                }

                public void run() {
                        generateRandomBuffer();
                        for (int i = 0; i < checkCount; ++i) {
                                String s1 = getString();
                                String s2 = getString();
                                if (s1.intern() != s2.intern()) {
                                        log.error("Test failed on: " + s1);
                                        setFailed(true);
                                }
                        }
                }
        }

        protected Runnable createRunnable(int i) {
                return new StringGenerator();
        }

        public static void main(String[] args) {
                GC.runTest(new StringIntern(), args);
        }
}
