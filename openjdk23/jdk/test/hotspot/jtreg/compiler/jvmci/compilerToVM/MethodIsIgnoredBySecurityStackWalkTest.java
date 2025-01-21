/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library /test/lib /
 * @library ../common/patches
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @modules java.base/jdk.internal.reflect
 *          jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:-UseJVMCICompiler
 *                   compiler.jvmci.compilerToVM.MethodIsIgnoredBySecurityStackWalkTest
 */

package compiler.jvmci.compilerToVM;

import compiler.jvmci.common.CTVMUtilities;
import jdk.test.lib.Asserts;
import jdk.vm.ci.hotspot.CompilerToVMHelper;
import jdk.vm.ci.hotspot.HotSpotResolvedJavaMethod;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodIsIgnoredBySecurityStackWalkTest {

    public static void main(String[] args) {
        Map<Executable, Boolean> testCases = createTestCases();
        testCases.forEach(
                MethodIsIgnoredBySecurityStackWalkTest::runSanityTest);
    }

    private static void runSanityTest(Executable aMethod, Boolean expected) {
        HotSpotResolvedJavaMethod method
                = CTVMUtilities.getResolvedMethod(aMethod);
        boolean isIgnored = CompilerToVMHelper
                .methodIsIgnoredBySecurityStackWalk(method);
        String msg = String.format("%s is%s ignored but must%s", aMethod,
                isIgnored ? "" : " not",
                expected ? "" : " not");
        Asserts.assertEQ(isIgnored, expected, msg);
    }

    private static Map<Executable, Boolean> createTestCases() {
        Map<Executable, Boolean> testCases = new HashMap<>();

        try {
            Class<?> aClass = Method.class;
            testCases.put(aClass.getMethod("invoke", Object.class,
                    Object[].class), true);

            aClass = Class.forName("jdk.internal.reflect.DirectMethodHandleAccessor$NativeAccessor");
            testCases.put(aClass.getMethod("invoke", Object.class,
                    Object[].class), true);
            testCases.put(aClass.getDeclaredMethod("invoke0", Method.class,
                    Object.class, Object[].class), true);

            aClass = MethodIsIgnoredBySecurityStackWalkTest.class;
            for (Executable method : aClass.getMethods()) {
                testCases.put(method, false);
            }
            for (Executable method : aClass.getDeclaredMethods()) {
                testCases.put(method, false);
            }
            for (Executable method : aClass.getConstructors()) {
                testCases.put(method, false);
            }
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new Error("TEST BUG " + e.getMessage(), e);
        }
        return testCases;
    }
}
