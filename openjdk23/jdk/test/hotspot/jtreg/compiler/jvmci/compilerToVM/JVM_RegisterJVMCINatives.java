/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136421
 * @requires vm.jvmci
 * @library /test/lib /
 * @ignore 8249621
 * @modules java.base/jdk.internal.misc:open
 * @modules jdk.internal.vm.ci/jdk.vm.ci.hotspot:open
 *          jdk.internal.vm.ci/jdk.vm.ci.runtime
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *      -Dcompiler.jvmci.compilerToVM.JVM_RegisterJVMCINatives.positive=true
 *      -XX:+EnableJVMCI
 *      compiler.jvmci.compilerToVM.JVM_RegisterJVMCINatives
 * @run main/othervm -XX:+UnlockExperimentalVMOptions
 *      -Dcompiler.jvmci.compilerToVM.JVM_RegisterJVMCINatives.positive=false
 *      -XX:-EnableJVMCI -XX:-UseJVMCICompiler
 *      compiler.jvmci.compilerToVM.JVM_RegisterJVMCINatives
 */

package compiler.jvmci.compilerToVM;

import jdk.test.lib.Asserts;
import jdk.vm.ci.runtime.JVMCI;

import java.lang.reflect.Method;

public class JVM_RegisterJVMCINatives {
    private static final boolean IS_POSITIVE = Boolean.getBoolean(
            "compiler.jvmci.compilerToVM.JVM_RegisterJVMCINatives.positive");

    private final Method registerNatives;

    public static void main(String[] args) {
        new JVM_RegisterJVMCINatives().runTest();
    }

    private void runTest() {
        Object result;
        try {
            result = invoke();
        } catch (InternalError e) {
            if (IS_POSITIVE) {
                throw new AssertionError("unexpected exception", e);
            }
            return;
        }
        if (!IS_POSITIVE) {
            throw new AssertionError("didn't get expected exception");
        }
        Asserts.assertNull(result,
                "registerNatives()V returned non-null");
        Asserts.assertEQ(result, invoke(),
                "registerNatives returns different results");

    }
    private Object invoke() {
        Object result;
        try {
            result = registerNatives.invoke(JVMCI.class);
        } catch (ReflectiveOperationException e) {
            throw new Error("can't invoke registerNatives", e);
        }
        return result;
    }

    private JVM_RegisterJVMCINatives() {
        Method method;
        try {
            method = Class.forName("jdk.vm.ci.hotspot.CompilerToVM",
                    /* initialize = */ false,
                    this.getClass().getClassLoader())
                    .getDeclaredMethod("registerNatives");
            method.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new Error("can't find CompilerToVM::registerNatives", e);
        }
        registerNatives = method;
    }
}
