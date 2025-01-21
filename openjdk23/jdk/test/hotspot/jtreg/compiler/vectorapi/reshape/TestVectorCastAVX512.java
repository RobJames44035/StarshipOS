/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.vectorapi.reshape;

import compiler.vectorapi.reshape.tests.TestVectorCast;
import compiler.vectorapi.reshape.utils.TestCastMethods;
import compiler.vectorapi.reshape.utils.VectorReshapeHelper;

/*
 * @test
 * @bug 8259610
 * @key randomness
 * @modules jdk.incubator.vector
 * @modules java.base/jdk.internal.misc
 * @summary Test that vector cast intrinsics work as intended on avx512.
 * @requires vm.cpu.features ~= ".*avx512.*"
 * @library /test/lib /
 * @run main/timeout=300 compiler.vectorapi.reshape.TestVectorCastAVX512
 */
public class TestVectorCastAVX512 {
    public static void main(String[] args) {
        VectorReshapeHelper.runMainHelper(
                TestVectorCast.class,
                TestCastMethods.AVX512_CAST_TESTS.stream(),
                "-XX:UseAVX=3");
    }
}

