/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @requires vm.jvmci & !vm.graal.enabled & vm.compMode == "Xmixed"
 * @library / /test/lib
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot
 *          jdk.internal.vm.ci/jdk.vm.ci.code
 *          jdk.internal.vm.ci/jdk.vm.ci.code.site
 *          jdk.internal.vm.ci/jdk.vm.ci.meta
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *     -Xbootclasspath/a:.
 *     -XX:+EnableJVMCI -XX:JVMCITraceLevel=1
 *     -Dtest.jvmci.forceRuntimeStubAllocFail=test_stub_that_fails_to_be_allocated
 *     jdk.vm.ci.code.test.RuntimeStubAllocFailTest
 */

package jdk.vm.ci.code.test;

import jdk.test.lib.Asserts;
import jdk.test.lib.Platform;
import jdk.vm.ci.code.BailoutException;
import jdk.vm.ci.code.site.DataPatch;
import jdk.vm.ci.code.site.Site;
import jdk.vm.ci.hotspot.HotSpotCodeCacheProvider;
import jdk.vm.ci.hotspot.HotSpotCompiledCode;
import jdk.vm.ci.hotspot.HotSpotCompiledCode.Comment;
import jdk.vm.ci.meta.Assumptions.Assumption;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import jdk.vm.ci.runtime.JVMCI;
import jdk.vm.ci.runtime.JVMCIRuntime;
import jdk.vm.ci.runtime.JVMCIBackend;

public class RuntimeStubAllocFailTest {

    public static void main(String args[]) {
        JVMCIBackend backend = JVMCI.getRuntime().getHostJVMCIBackend();
        HotSpotCodeCacheProvider codeCache = (HotSpotCodeCacheProvider) backend.getCodeCache();
        int dataSectionAlignment = 8; // CodeBuffer::SECT_CONSTS code section alignment
        String stubToFail = System.getProperty("test.jvmci.forceRuntimeStubAllocFail");
        if (Platform.isDebugBuild() && stubToFail != null) {
            HotSpotCompiledCode stub = new HotSpotCompiledCode(stubToFail,
                    /* targetCode */ new byte[0],
                    /* targetCodeSize */ 0,
                    /* sites */ new Site[0],
                    /* assumptions */ new Assumption[0],
                    /* methods */ new ResolvedJavaMethod[0],
                    /* comments */ new Comment[0],
                    /* dataSection */ new byte[0],
                    dataSectionAlignment,
                    /* dataSectionPatches */ new DataPatch[0],
                    /* isImmutablePIC */ false,
                    /* totalFrameSize */ 0,
                    /* deoptRescueSlot */ null);
            try {
                codeCache.installCode(null, stub, null, null, true);
                throw new AssertionError("Didn't get expected " + BailoutException.class.getName());
            } catch (BailoutException e) {
                Asserts.assertEQ(e.getMessage(), "Error installing " + stubToFail + ": code cache is full");
            }
        }
    }
}
