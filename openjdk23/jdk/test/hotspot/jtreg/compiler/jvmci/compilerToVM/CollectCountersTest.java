/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library / /test/lib/
 * @library ../common/patches
 * @modules java.base/jdk.internal.misc
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *                   -XX:+EnableJVMCI
 *                   -XX:JVMCICounterSize=0
 *                   -Dcompiler.jvmci.compilerToVM.CollectCountersTest.expected=0
 *                   compiler.jvmci.compilerToVM.CollectCountersTest
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *                   -XX:+EnableJVMCI
 *                   -XX:JVMCICounterSize=11
 *                   -Dcompiler.jvmci.compilerToVM.CollectCountersTest.expected=11
 *                   compiler.jvmci.compilerToVM.CollectCountersTest
 */

package compiler.jvmci.compilerToVM;

import jdk.test.lib.Asserts;
import jdk.vm.ci.hotspot.CompilerToVMHelper;

public class CollectCountersTest {
    private static final int EXPECTED = Integer.getInteger(
            "compiler.jvmci.compilerToVM.CollectCountersTest.expected");
    public static void main(String args[]) {
        new CollectCountersTest().runTest();
    }

    private void runTest() {
        long[] counters = CompilerToVMHelper.collectCounters();
        Asserts.assertNotNull(counters, "Expected not-null counters array");
        int ctvmData = counters.length;
        Asserts.assertEQ(EXPECTED, ctvmData, "Unexpected counters amount");
    }
}
