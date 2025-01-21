/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @requires vm.jvmci
 * @requires vm.simpleArch == "x64" | vm.simpleArch == "aarch64" | vm.simpleArch == "riscv64"
 * @library /test/lib /
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.code.site
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          jdk.internal.vm.ci/jdk.vm.ci.aarch64
 *          jdk.internal.vm.ci/jdk.vm.ci.amd64
 *          jdk.internal.vm.ci/jdk.vm.ci.riscv64
 * @compile CodeInstallationTest.java DebugInfoTest.java TestAssembler.java TestHotSpotVMConfig.java amd64/AMD64TestAssembler.java aarch64/AArch64TestAssembler.java riscv64/RISCV64TestAssembler.java
 * @run junit/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler jdk.vm.ci.code.test.SimpleCodeInstallationTest
 */

package jdk.vm.ci.code.test;
import jdk.test.lib.Asserts;

import jdk.vm.ci.code.Register;
import jdk.vm.ci.hotspot.HotSpotNmethod;
import org.junit.Test;

/**
 * Test simple code installation.
 */
public class SimpleCodeInstallationTest extends CodeInstallationTest {

    public static int add(int a, int b) {
        return a + b;
    }

    private static void compileAdd(TestAssembler asm) {
        Register arg0 = asm.emitIntArg0();
        Register arg1 = asm.emitIntArg1();
        Register ret = asm.emitIntAdd(arg0, arg1);
        asm.emitIntRet(ret);
    }

    @Test
    public void test() {
        HotSpotNmethod nmethod = test(SimpleCodeInstallationTest::compileAdd, getMethod("add", int.class, int.class), 5, 7);

        // Test code invalidation
        Asserts.assertTrue(nmethod.isValid(), "code is not valid, i = " + nmethod);
        Asserts.assertTrue(nmethod.isAlive(), "code is not alive, i = " + nmethod);
        Asserts.assertNotEquals(nmethod.getStart(), 0L);

        // Make nmethod non-entrant but still alive
        nmethod.invalidate(false);
        Asserts.assertFalse(nmethod.isValid(), "code is valid, i = " + nmethod);
        Asserts.assertTrue(nmethod.isAlive(), "code is not alive, i = " + nmethod);
        Asserts.assertEquals(nmethod.getStart(), 0L);

        // Deoptimize the nmethod and cut the link to it from the HotSpotNmethod
        nmethod.invalidate(true);
        Asserts.assertFalse(nmethod.isValid(), "code is valid, i = " + nmethod);
        Asserts.assertFalse(nmethod.isAlive(), "code is alive, i = " + nmethod);
        Asserts.assertEquals(nmethod.getStart(), 0L);
    }
}
