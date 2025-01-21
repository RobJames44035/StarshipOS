/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.arraycopy.stress;

import java.util.Arrays;
import java.util.Random;
import jdk.test.lib.Utils;

public class StressObjectArrayCopy extends AbstractStressArrayCopy {

    private static final Object[] orig = new Object[MAX_SIZE];
    private static final Object[] test = new Object[MAX_SIZE];

    protected void testWith(int size, int l, int r, int len) {
        // Seed the test from the original
        System.arraycopy(orig, 0, test, 0, size);

        // Check the seed is correct
        {
            int m = Arrays.mismatch(test, 0, size,
                                    orig, 0, size);
            if (m != -1) {
                throwSeedError(size, m);
            }
        }

        // Perform the tested copy
        System.arraycopy(test, l, test, r, len);

        // Check the copy has proper contents
        {
            int m = Arrays.mismatch(test, r, r+len,
                                    orig, l, l+len);
            if (m != -1) {
                throwContentsError(l, r, len, r+m);
            }
        }

        // Check anything else was not affected: head and tail
        {
            int m = Arrays.mismatch(test, 0, r,
                                    orig, 0, r);
            if (m != -1) {
                throwHeadError(l, r, len, m);
            }
        }
        {
            int m = Arrays.mismatch(test, r + len, size,
                                    orig, r + len, size);
            if (m != -1) {
                throwTailError(l, r, len, m);
            }
        }
    }

    public static void main(String... args) {
        Random rand = Utils.getRandomInstance();
        for (int c = 0; c < orig.length; c++) {
            orig[c] = new Object();
        }
        new StressObjectArrayCopy().run(rand);
    }

}
