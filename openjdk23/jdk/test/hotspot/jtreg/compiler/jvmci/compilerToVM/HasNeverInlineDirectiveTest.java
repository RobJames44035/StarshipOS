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
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:-UseJVMCICompiler
 *                   compiler.jvmci.compilerToVM.HasNeverInlineDirectiveTest
 */

package compiler.jvmci.compilerToVM;

import compiler.jvmci.common.CTVMUtilities;
import jdk.test.lib.Asserts;
import jdk.vm.ci.hotspot.CompilerToVMHelper;
import jdk.vm.ci.hotspot.HotSpotResolvedJavaMethod;
import jdk.test.whitebox.WhiteBox;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HasNeverInlineDirectiveTest {

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    public static void main(String[] args) {
        List<Executable> testCases = createTestCases();
        testCases.forEach(HasNeverInlineDirectiveTest::runSanityTest);
    }

    private static void runSanityTest(Executable aMethod) {
        HotSpotResolvedJavaMethod method = CTVMUtilities
                .getResolvedMethod(aMethod);
        boolean hasNeverInlineDirective = CompilerToVMHelper.hasNeverInlineDirective(method);
        boolean expected = WB.testSetDontInlineMethod(aMethod, true);
        Asserts.assertEQ(hasNeverInlineDirective, expected, "Unexpected initial " +
                "value of property 'hasNeverInlineDirective'");

        hasNeverInlineDirective = CompilerToVMHelper.hasNeverInlineDirective(method);
        Asserts.assertTrue(hasNeverInlineDirective, aMethod + "Unexpected value of " +
                "property 'hasNeverInlineDirective' after setting 'do not inline' to true");
        WB.testSetDontInlineMethod(aMethod, false);
        hasNeverInlineDirective = CompilerToVMHelper.hasNeverInlineDirective(method);
        Asserts.assertFalse(hasNeverInlineDirective, "Unexpected value of " +
                "property 'hasNeverInlineDirective' after setting 'do not inline' to false");
    }

    private static List<Executable> createTestCases() {
        List<Executable> testCases = new ArrayList<>();

        Class<?> aClass = DummyClass.class;
        testCases.addAll(Arrays.asList(aClass.getDeclaredMethods()));
        testCases.addAll(Arrays.asList(aClass.getDeclaredConstructors()));
        return testCases;
    }
}
