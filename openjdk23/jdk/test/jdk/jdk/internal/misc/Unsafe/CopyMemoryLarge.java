/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import jdk.internal.misc.Unsafe;

/*
 * @test
 * @summary Test Unsafe.copyMemory
 * @modules java.base/jdk.internal.misc
 * @requires os.maxMemory > 8G
 */
public class CopyMemoryLarge extends CopyCommon {
    private CopyMemoryLarge() {
    }

    /**
     * Run positive tests
     *
     * @throws RuntimeException if an error is found
     */
    private void testPositive() {
        testLargeCopy(false);
    }

    /**
     * Run all tests
     *
     * @throws RuntimeException if an error is found
     */
    private void test() {
        testPositive();
    }

    public static void main(String[] args) {
        CopyMemoryLarge cs = new CopyMemoryLarge();
        cs.test();
    }
}
