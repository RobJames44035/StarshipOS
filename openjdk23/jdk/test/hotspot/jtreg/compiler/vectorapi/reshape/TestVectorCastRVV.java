/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.vectorapi.reshape;

import compiler.vectorapi.reshape.tests.TestVectorCast;
import compiler.vectorapi.reshape.utils.TestCastMethods;
import compiler.vectorapi.reshape.utils.VectorReshapeHelper;

/*
 * @test
 * @bug 8321021 8321023 8321024
 * @key randomness
 * @modules jdk.incubator.vector
 * @modules java.base/jdk.internal.misc
 * @summary Test that vector cast intrinsics work as intended on riscv (rvv).
 * @requires os.arch == "riscv64" & vm.cpu.features ~= ".*rvv.*"
 * @library /test/lib /
 * @run main/timeout=300 compiler.vectorapi.reshape.TestVectorCastRVV
 */
public class TestVectorCastRVV {
    public static void main(String[] args) {
        VectorReshapeHelper.runMainHelper(
                TestVectorCast.class,
                TestCastMethods.RVV_CAST_TESTS.stream(),
                "-XX:+UseRVV");
    }
}

