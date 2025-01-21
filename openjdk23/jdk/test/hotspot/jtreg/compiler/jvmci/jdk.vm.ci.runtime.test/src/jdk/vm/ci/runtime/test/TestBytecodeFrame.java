/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @requires vm.jvmci
 * @library ../../../../../
 * @modules jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          java.base/jdk.internal.misc
 * @run junit/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler jdk.vm.ci.runtime.test.TestBytecodeFrame
 */

package jdk.vm.ci.runtime.test;

import jdk.vm.ci.code.BytecodeFrame;
import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.JavaValue;
import jdk.vm.ci.meta.JavaConstant;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import org.junit.Test;

import java.util.Map;
import java.util.Iterator;

import org.junit.Assert;

public class TestBytecodeFrame extends MethodUniverse {

    private static void assertEquals(BytecodeFrame f1, BytecodeFrame f2) {
        Assert.assertEquals(f1, f2);
        Assert.assertEquals(f1.hashCode(), f2.hashCode());
    }

    private static void assertNotEquals(BytecodeFrame f1, BytecodeFrame f2) {
        Assert.assertNotEquals(f1, f2);
        Assert.assertNotEquals(f1.hashCode(), f2.hashCode());
    }

    /**
     * Tests the {@link BytecodeFrame#equals} and {@link BytecodeFrame#hashCode}.
     */
    @Test
    public void equalsAndHashcodeTest() {
        Iterator<ResolvedJavaMethod> iter = methods.values().iterator();
        ResolvedJavaMethod m1 = iter.next();
        ResolvedJavaMethod m2 = iter.next();
        ResolvedJavaMethod m3 = iter.next();

        JavaValue[] values = {
            JavaConstant.INT_0,
            JavaConstant.INT_1,
            JavaConstant.INT_2,
            JavaConstant.NULL_POINTER,
        };
        JavaKind[] slotKinds = {
            JavaKind.Int,
            JavaKind.Int,
            JavaKind.Int,
            JavaKind.Object,
        };
        JavaValue[] values2 = {
            JavaConstant.INT_1,
            JavaConstant.INT_2,
            JavaConstant.NULL_POINTER,
            JavaConstant.INT_0,
        };
        JavaKind[] slotKinds2 = {
            JavaKind.Int,
            JavaKind.Int,
            JavaKind.Object,
            JavaKind.Int,
        };

        // The BytecodeFrame objects below will not all pass BytecodeFrame.verifyInvariants
        // but that's fine for simply testing equals and hashCode.
        BytecodeFrame caller = new BytecodeFrame(null, m3, 0, false, true,  values,  slotKinds, 1, 1, 0);
        BytecodeFrame f1 =  new BytecodeFrame(caller, m1, 0, false,  true,  values,  slotKinds,  1, 1, 0);
                                                                                                           // Differing field
        assertNotEquals(f1, new BytecodeFrame(caller, m2, 0, false, true,  values,  slotKinds,  1, 1, 0)); // method
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 1, false, true,  values,  slotKinds,  1, 1, 0)); // bci
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, true,  true,  values,  slotKinds,  1, 1, 0)); // rethrowException
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, false, values,  slotKinds,  1, 1, 0)); // duringCall
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, true,  values2, slotKinds,  1, 1, 0)); // values
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, true,  values,  slotKinds2, 1, 1, 0)); // slotKinds
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, false, values,  slotKinds,  2, 1, 0)); // numLocals
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, false, values,  slotKinds,  1, 2, 0)); // numStack
        assertNotEquals(f1, new BytecodeFrame(caller, m1, 0, false, false, values,  slotKinds,  1, 1, 1)); // numLocks
        assertEquals(f1, f1);

        BytecodeFrame f2 = new BytecodeFrame(caller, m1, 0, false,  true,  values,  slotKinds, 1, 1, 0);
        assertEquals(f1, f2);
    }
}
