/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @requires vm.jvmci
 * @requires vm.simpleArch == "x64" | vm.simpleArch == "aarch64" | vm.simpleArch == "riscv64"
 * @library /
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.code.site
 *          jdk.internal.vm.ci/jdk.vm.ci.common
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          jdk.internal.vm.ci/jdk.vm.ci.aarch64
 *          jdk.internal.vm.ci/jdk.vm.ci.amd64
 *          jdk.internal.vm.ci/jdk.vm.ci.riscv64
 * @compile CodeInstallationTest.java DebugInfoTest.java TestAssembler.java TestHotSpotVMConfig.java amd64/AMD64TestAssembler.java aarch64/AArch64TestAssembler.java riscv64/RISCV64TestAssembler.java
 * @run junit/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler jdk.vm.ci.code.test.MaxOopMapStackOffsetTest
 */

package jdk.vm.ci.code.test;

import jdk.vm.ci.code.Location;
import jdk.vm.ci.code.Register;
import jdk.vm.ci.common.JVMCIError;
import jdk.vm.ci.meta.JavaConstant;
import jdk.vm.ci.meta.JavaKind;
import org.junit.Test;

public class MaxOopMapStackOffsetTest extends DebugInfoTest {

    public static int pass() {
        return 42;
    }

    public static int fail() {
        return 42;
    }

    private void test(String name, int offset) {
        Location location = Location.stack(offset);
               DebugInfoCompiler compiler = (asm, values) -> {
            asm.growFrame(offset);
            Register v = asm.emitLoadInt(0);
            asm.emitIntToStack(v);
            values[0] = JavaConstant.forInt(42);
            return null;
        };
        test(compiler, getMethod(name), 2, new Location[]{location}, new Location[1], new int[]{4}, JavaKind.Int);
    }

    private int maxOffset() {
        return config.maxOopMapStackOffset;
    }

    private int wordSize() {
        return config.heapWordSize;
    }

    @Test(expected = JVMCIError.class)
    public void failTooLargeOffset() {
        // This should throw a JVMCIError during installation because the offset is too large.
        test("fail", maxOffset() + wordSize());
    }

    @Test
    public void passWithLargeOffset() {
        test("pass", maxOffset());
    }
}
