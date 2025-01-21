/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @requires vm.jvmci
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 * @library /compiler/jvmci/common/patches
 * @build jdk.internal.vm.ci/jdk.vm.ci.hotspot.HotSpotResolvedJavaFieldHelper
 * @run testng/othervm
 *      -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler
 *      jdk.vm.ci.hotspot.test.TestHotSpotResolvedJavaField
 */

package jdk.vm.ci.hotspot.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import jdk.vm.ci.hotspot.HotSpotResolvedJavaFieldHelper;
import jdk.vm.ci.meta.ResolvedJavaField;

public class TestHotSpotResolvedJavaField {

    @Test
    public void testIndex() {
        int max = Character.MAX_VALUE;
        int[] valid = {0, 1, max - 1, max};
        for (int index : valid) {
            ResolvedJavaField field = HotSpotResolvedJavaFieldHelper.createField(null, null, 0, 0, 0, index);
            Assert.assertEquals(HotSpotResolvedJavaFieldHelper.getIndex(field), index);
        }
    }

    @Test
    public void testOffset() {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        int[] valid = {min, min + 1, -2, 0, 1, max - 1, max};
        for (int offset : valid) {
            ResolvedJavaField field = HotSpotResolvedJavaFieldHelper.createField(null, null, offset, 0, 0, 0);
            Assert.assertEquals(field.getOffset(), offset);
        }
    }
}
