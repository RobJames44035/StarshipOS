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
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+DebugNonSafepoints
 *                   -Dcompiler.jvmci.compilerToVM.ShouldDebugNonSafepointsTest.expected=true
 *                   compiler.jvmci.compilerToVM.ShouldDebugNonSafepointsTest
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:-DebugNonSafepoints
 *                   -Dcompiler.jvmci.compilerToVM.ShouldDebugNonSafepointsTest.expected=false
 *                   compiler.jvmci.compilerToVM.ShouldDebugNonSafepointsTest
 */

package compiler.jvmci.compilerToVM;

import jdk.test.lib.Asserts;
import jdk.vm.ci.hotspot.CompilerToVMHelper;

public class ShouldDebugNonSafepointsTest {
    private static final boolean EXPECTED = Boolean.getBoolean("compiler"
            + ".jvmci.compilerToVM.ShouldDebugNonSafepointsTest.expected");

    public static void main(String args[]) {
        new ShouldDebugNonSafepointsTest().runTest();
    }

    private void runTest() {
        Asserts.assertEQ(CompilerToVMHelper.shouldDebugNonSafepoints(),
                EXPECTED, "Unexpected shouldDebugnonSafepoints value");
    }
}
