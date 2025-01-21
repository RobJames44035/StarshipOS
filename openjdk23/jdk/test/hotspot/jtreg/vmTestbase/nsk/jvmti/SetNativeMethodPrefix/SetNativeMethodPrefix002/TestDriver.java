/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/SetNativeMethodPrefix/SetNativeMethodPrefix002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, feature_jdk6_jpda, vm6]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test is designed to test whether SetNativeMethodPrefix()
 *     JVMTI functions work according to specification when multiple agents are set.
 *     The test verifies that:
 *         - the order of applied prefixes is preserved.
 *     Also there are some checks that JVMTI error codes to be correctly returned.
 * COMMENTS
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment duplicate SetNativeMethodPrefix001 in current directory
 * @run driver nsk.jvmti.NativeLibraryCopier
 *      SetNativeMethodPrefix002
 *      SetNativeMethodPrefix002-01
 *      SetNativeMethodPrefix002-02
 *      SetNativeMethodPrefix002-03
 *
 * @run main/othervm/native TestDriver
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.util.Arrays;

public class TestDriver {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "-agentlib:SetNativeMethodPrefix001=trace=all",
                "-agentlib:SetNativeMethodPrefix002-01=trace=all prefix=wa_",
                "-agentlib:SetNativeMethodPrefix002-02=trace=all prefix=wb_",
                "-agentlib:SetNativeMethodPrefix002-03=trace=all prefix=wc_",
                nsk.jvmti.SetNativeMethodPrefix.SetNativeMethodPrefix002.class.getName()
        );

        String envName = Platform.sharedLibraryPathVariableName();
        pb.environment()
          .merge(envName, ".", (x, y) -> y + File.pathSeparator + x);

        String command = Arrays.toString(args);
        System.out.println("exec " + command);
        pb.inheritIO();

        int exitCode = pb.start().waitFor();

        if (exitCode != 95 && exitCode !=0 ) {
            throw new AssertionError(command + " exit code is " + exitCode);
        }
    }
}


