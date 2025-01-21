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
 * @summary Test that vector cast intrinsics work as intended on avx512dq.
 * @requires vm.cpu.features ~= ".*avx512dq.*"
 * @library /test/lib /
 * @run main/timeout=300 compiler.vectorapi.reshape.TestVectorCastAVX512DQ
 */
public class TestVectorCastAVX512DQ {
    public static void main(String[] args) {
        VectorReshapeHelper.runMainHelper(
                TestVectorCast.class,
                TestCastMethods.AVX512DQ_CAST_TESTS.stream(),
                "-XX:UseAVX=3");
    }
}

