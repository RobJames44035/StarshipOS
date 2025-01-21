/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library /test/lib /
 * @library ../common/patches
 * @library ../common/patches
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:-UseJVMCICompiler
 *                   compiler.jvmci.compilerToVM.GetLineNumberTableTest
 */

package compiler.jvmci.compilerToVM;

import compiler.jvmci.common.CTVMUtilities;
import compiler.jvmci.common.testcases.TestCase;
import jdk.test.lib.Asserts;
import jdk.vm.ci.hotspot.CompilerToVMHelper;
import jdk.vm.ci.hotspot.HotSpotResolvedJavaMethod;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.Map;

public class GetLineNumberTableTest {
    public static void main(String[] args) {
        TestCase.getAllExecutables()
                .forEach(GetLineNumberTableTest::runSanityTest);
    }

    public static void runSanityTest(Executable aMethod) {
        HotSpotResolvedJavaMethod method = CTVMUtilities
                .getResolvedMethod(aMethod);
        long[] lineNumbers = CompilerToVMHelper.getLineNumberTable(method);
        long[] expectedLineNumbers = getExpectedLineNumbers(aMethod);

        Asserts.assertTrue(Arrays.equals(lineNumbers, expectedLineNumbers),
                String.format("%s : unequal table values : %n%s%n%s%n",
                        aMethod,
                        Arrays.toString(lineNumbers),
                        Arrays.toString(expectedLineNumbers)));
    }

    public static long[] getExpectedLineNumbers(Executable aMethod) {
        Map<Integer, Integer> bciToLine = CTVMUtilities
                .getBciToLineNumber(aMethod);
        long[] result = null;
        if (!bciToLine.isEmpty()) {
            result = new long[2 * bciToLine.size()];
            int i = 0;
            for (Integer key : bciToLine.keySet()) {
                result[i++] = key.longValue();
                result[i++] = bciToLine.get(key).longValue();
            }
        }
        // compilerToVM::getLineNumberTable returns null in case empty table
        return result;
    }

}
