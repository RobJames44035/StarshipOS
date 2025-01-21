/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @summary Testing compiler.jvmci.CompilerToVM.resolveTypeInPool method
 * @library /test/lib /
 * @library ../common/patches
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.access
 *          java.base/jdk.internal.reflect
 *          jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.CompilerToVMHelper jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -XX:-UseJVMCICompiler
 *                   compiler.jvmci.compilerToVM.ResolveTypeInPoolTest
 */

package compiler.jvmci.compilerToVM;

import compiler.jvmci.compilerToVM.ConstantPoolTestCase.ConstantTypes;
import compiler.jvmci.compilerToVM.ConstantPoolTestCase.TestedCPEntry;
import compiler.jvmci.compilerToVM.ConstantPoolTestCase.Validator;
import compiler.jvmci.compilerToVM.ConstantPoolTestsHelper.DummyClasses;
import jdk.vm.ci.hotspot.CompilerToVMHelper;
import jdk.vm.ci.hotspot.HotSpotResolvedObjectType;
import jdk.vm.ci.meta.ConstantPool;

import java.util.HashMap;
import java.util.Map;

import static compiler.jvmci.compilerToVM.ConstantPoolTestCase.ConstantTypes.CONSTANT_CLASS;

/**
 * Test for {@code jdk.vm.ci.hotspot.CompilerToVM.resolveTypeInPool} method
 */
public class ResolveTypeInPoolTest {

    public static void main(String[] args) throws Exception {
        Map<ConstantTypes, Validator> typeTests = new HashMap<>();
        typeTests.put(CONSTANT_CLASS, ResolveTypeInPoolTest::validate);
        ConstantPoolTestCase testCase = new ConstantPoolTestCase(typeTests);
        testCase.test();
        // The next "Class.forName" and repeating "testCase.test()"
        // are here for the following reason.
        // The first test run is without dummy class initialization,
        // which means no constant pool cache exists.
        // The second run is with initialized class (with constant pool cache available).
        // Some CompilerToVM methods require different input
        // depending on whether CP cache exists or not.
        for (DummyClasses dummy : DummyClasses.values()) {
            Class.forName(dummy.klass.getName());
        }
        testCase.test();
    }

    public static void validate(ConstantPool constantPoolCTVM,
                                ConstantTypes cpType,
                                DummyClasses dummyClass,
                                int i) {
        TestedCPEntry entry = cpType.getTestedCPEntry(dummyClass, i);
        if (entry == null) {
            return;
        }
        HotSpotResolvedObjectType typeToVerify = CompilerToVMHelper.resolveTypeInPool(constantPoolCTVM, i);
        String classNameToRefer = entry.klass;
        String outputToVerify = typeToVerify.toString();
        if (!outputToVerify.contains(classNameToRefer)) {
            String msg = String.format("Wrong class accessed by constant"
                                               + " pool index %d: %s, but should be %s",
                                       i,
                                       outputToVerify,
                                       classNameToRefer);
            throw new AssertionError(msg);
        }
    }
}
