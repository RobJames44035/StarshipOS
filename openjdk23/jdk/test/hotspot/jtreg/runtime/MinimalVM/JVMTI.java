/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @requires vm.flavor == "minimal"
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run driver JVMTI
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class JVMTI {

    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-minimal",
                "-agentlib:jdwp=server=y,transport=dt_socket,address=5000,suspend=n",
                "-version");
        new OutputAnalyzer(pb.start())
                .shouldContain("Debugging agents are not supported in this VM")
                .shouldHaveExitValue(1);

    }
}
