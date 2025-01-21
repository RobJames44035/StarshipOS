/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import jdk.test.whitebox.WhiteBox;
import java.util.Objects;
import jdk.test.lib.Platform;

/**
 * @test
 * @bug 8153324
 * @summary Verify instrumented Throwable bytecode by compiling it with C1.
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @requires vm.compMode!="Xint"
 * @build  jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -Xbatch -XX:StartFlightRecording:dumponexit=true jdk.jfr.event.runtime.TestThrowableInstrumentation
 */
public class TestThrowableInstrumentation {
    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();
    private static int COMP_LEVEL_SIMPLE = 1;

    private static boolean isTieredCompilationEnabled() {
        return Boolean.valueOf(Objects.toString(WHITE_BOX.getVMFlag("TieredCompilation")));
    }

    public static void main(String[] args) {
        // Compile Throwable::<clinit> with C1 (if available)
        if (!WHITE_BOX.enqueueInitializerForCompilation(java.lang.Throwable.class, COMP_LEVEL_SIMPLE)) {
          if (!Platform.isServer() || isTieredCompilationEnabled() || Platform.isEmulatedClient()) {
            throw new RuntimeException("Unable to compile Throwable::<clinit> with C1");
          }
        }
    }
}
