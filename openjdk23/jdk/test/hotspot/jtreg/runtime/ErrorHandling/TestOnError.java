/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test TestOnError
 * @bug 8078470
 * @summary Test using -XX:OnError=<cmd>
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @requires vm.flagless
 * @requires vm.debug
 * @run driver TestOnError
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;

public class TestOnError {

    public static void main(String[] args) throws Exception {
        String msg = "Test Succeeded";

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
           "-XX:-CreateCoredumpOnCrash",
           "-XX:ErrorHandlerTest=14", // trigger potential SEGV
           "-XX:OnError=echo " + msg,
           TestOnError.class.getName());

        OutputAnalyzer output = new OutputAnalyzer(pb.start());

        /* Actual output will include:
           #
           # -XX:OnError="echo Test Succeeded"
           #   Executing /bin/sh -c "echo Test Succeeded"...
           Test Succeeded

           So we don't want to match on the "# Executing ..." line, and they
           both get written to stdout.
        */
        output.stdoutShouldMatch("^" + msg); // match start of line only
        System.out.println("PASSED");
    }
}
