/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.vm.ci.code.test;

import org.junit.Assert;
import org.junit.Test;

import jdk.vm.ci.code.VirtualObject;
import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.JavaValue;
import jdk.vm.ci.meta.ResolvedJavaType;

public class VirtualObjectFormattingTest extends VirtualObjectTestBase {

    @Test
    public void testFormat() {
        testBase();
    }

    @Override
    protected void test(ResolvedJavaType klass, JavaValue[] kinds, JavaKind[] values, boolean malformed) {
        // Verify that VirtualObject.toString will produce output without throwing exceptions or
        // asserting.
        VirtualObject virtual = VirtualObject.get(klass, 0);
        virtual.setValues(kinds, values);
        Assert.assertTrue(!virtual.toString().equals(""));
    }
}
