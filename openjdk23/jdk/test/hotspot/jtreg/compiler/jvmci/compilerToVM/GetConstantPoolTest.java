/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library /test/lib /
 * @library ../common/patches
 * @library /testlibrary/asm
 * @ignore 8249621
 * @modules java.base/jdk.internal.misc
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:-UseJVMCICompiler
 *                   compiler.jvmci.compilerToVM.GetConstantPoolTest
 */
package compiler.jvmci.compilerToVM;

import jdk.test.lib.Utils;
import compiler.jvmci.common.CTVMUtilities;
import compiler.jvmci.common.testcases.TestCase;
import jdk.vm.ci.hotspot.CompilerToVMHelper;
import jdk.vm.ci.hotspot.HotSpotResolvedJavaMethod;
import jdk.vm.ci.hotspot.HotSpotResolvedObjectType;
import jdk.vm.ci.meta.ConstantPool;

import java.lang.reflect.Field;
import java.lang.reflect.Executable;

/**
 * Tests for jdk.vm.ci.hotspot.CompilerToVM::getConstantPool method
 */
public class GetConstantPoolTest {

    public static void testMethod(Executable executable) {
        test(CTVMUtilities.getResolvedMethod(executable));
    }

    public static void testClass(Class cls) {
        HotSpotResolvedObjectType type = CompilerToVMHelper
                .lookupTypeHelper(Utils.toJVMTypeSignature(cls),
                        GetConstantPoolTest.class, /* resolve = */ true);
        test(type);
    }

    private static void test(Object object) {
        ConstantPool cp = CompilerToVMHelper.getConstantPool(object);
        System.out.println(object + " -> " + cp);
    }

    public static void main(String[] args) {
        TestCase.getAllClasses().forEach(GetConstantPoolTest::testClass);
        TestCase.getAllExecutables().forEach(GetConstantPoolTest::testMethod);
        testNull();
        testObject();
    }

    private static void testNull() {
        try {
            Object cp = CompilerToVMHelper.getConstantPool(null);
            throw new AssertionError("Test OBJECT."
                + " Expected IllegalArgumentException has not been caught");
        } catch (NullPointerException npe) {
            // expected
        }
    }
    private static void testObject() {
        try {
            Object cp = CompilerToVMHelper.getConstantPool(new Object());
            throw new AssertionError("Test OBJECT."
                + " Expected IllegalArgumentException has not been caught");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
