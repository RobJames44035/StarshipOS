/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/StringInternGC.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:+ExplicitGCInvokesConcurrent gc.gctests.StringInternGC.StringInternGC
 */

package gc.gctests.StringInternGC;

import nsk.share.test.*;
import nsk.share.gc.*;

/**
 * Test that strings returned by String.intern() can be collected.
 *
 * Create strings consisting of random characters, call String.intern().
 * String pool should not overflow.
 */
public class StringInternGC extends ThreadedGCTest {
        private int maxLength = 1000; // Maximum number of characters to add per operation.
        private int maxTotalLength = 128 * 1024; // Total maximum length of the string until a new StringBuffer will be allocated.
        private long lastTime = System.currentTimeMillis();

        private class StringGenerator implements Runnable {
                private StringBuffer sb = new StringBuffer();

                private String generateString() {
                        int length = LocalRandom.nextInt(maxLength);
                        if (sb.length() > maxTotalLength) {
                                sb = new StringBuffer();
                        }

                        for (int i = 0; i < length; ++i)
                                sb.append((char) LocalRandom.nextInt(Integer.MAX_VALUE));
                        return sb.toString();
                }


                public void run() {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastTime > 5000) { // Cause a full gc every 5s.
                                lastTime = currentTime;
                                System.gc();
                        }

                        generateString().intern();
                }
        }

        protected Runnable createRunnable(int i) {
                return new StringGenerator();
        }

        public static void main(String[] args) {
                GC.runTest(new StringInternGC(), args);
        }
}
