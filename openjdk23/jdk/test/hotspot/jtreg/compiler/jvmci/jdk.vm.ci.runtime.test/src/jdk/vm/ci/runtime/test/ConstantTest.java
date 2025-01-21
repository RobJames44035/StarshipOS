/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @requires vm.jvmci
 * @library ../../../../../
 * @modules jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *          java.base/jdk.internal.misc
 * @run junit/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler jdk.vm.ci.runtime.test.ConstantTest
 */
package jdk.vm.ci.runtime.test;

import jdk.vm.ci.meta.JavaConstant;
import org.junit.Assert;
import org.junit.Test;

public class ConstantTest extends FieldUniverse {

    @Test
    public void testNegativeZero() {
        Assert.assertTrue("Constant for 0.0f must be different from -0.0f", JavaConstant.FLOAT_0 != JavaConstant.forFloat(-0.0F));
        Assert.assertTrue("Constant for 0.0d must be different from -0.0d", JavaConstant.DOUBLE_0 != JavaConstant.forDouble(-0.0d));
    }

    @Test
    public void testNullIsNull() {
        Assert.assertTrue(JavaConstant.NULL_POINTER.isNull());
    }
}
