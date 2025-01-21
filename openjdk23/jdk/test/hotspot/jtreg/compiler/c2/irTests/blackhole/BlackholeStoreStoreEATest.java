/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8284848
 * @requires vm.compiler2.enabled
 * @summary Blackhole arguments are globally escaping, thus preventing advanced EA optimizations
 * @library /test/lib /
 * @run driver compiler.c2.irTests.blackhole.BlackholeStoreStoreEATest
 */

package compiler.c2.irTests.blackhole;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;

public class BlackholeStoreStoreEATest {

    public static void main(String[] args) {
        TestFramework.runWithFlags(
            "-XX:+UseTLAB",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:CompileCommand=blackhole,compiler.c2.irTests.blackhole.BlackholeStoreStoreEATest::blackhole"
        );
    }

    /*
     * Negative test is not possible: the StoreStore barrier is still in, even if we just do dontinline.
     * Positive test: check that blackhole keeps the StoreStore barrier in.
     */

    @Test
    @IR(counts = {IRNode.MEMBAR_STORESTORE, "1"})
    static void testBlackholed() {
        Object o = new Object();
        blackhole(o);
    }

    static void blackhole(Object o) {}

    @Run(test = "testBlackholed")
    static void runBlackholed() {
        testBlackholed();
    }

}
