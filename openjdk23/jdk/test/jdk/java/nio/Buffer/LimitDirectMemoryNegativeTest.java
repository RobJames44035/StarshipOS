/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 4627316 6743526
 * @summary Test option to limit direct memory allocation,
 *          various bad values fail to launch the VM
 * @requires (os.arch == "x86_64") | (os.arch == "amd64")
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *
 * @run main LimitDirectMemoryNegativeTest foo
 * @run main LimitDirectMemoryNegativeTest 10kmt
 * @run main LimitDirectMemoryNegativeTest -1
 */

import jdk.test.lib.process.ProcessTools;

public class LimitDirectMemoryNegativeTest {

    private static final String ERR = "Improperly specified VM option 'MaxDirectMemorySize=";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("missing size argument");
        }

        int exitCode = ProcessTools.executeTestJava(
                                    "-XX:MaxDirectMemorySize=" + args[0],
                                    LimitDirectMemoryNegativeTest.class.getName())
                                   .shouldContain(ERR + args[0])
                                   .getExitValue();
        if (exitCode != 1) {
            throw new RuntimeException("Unexpected exit code: " + exitCode);
        }
    }
}
