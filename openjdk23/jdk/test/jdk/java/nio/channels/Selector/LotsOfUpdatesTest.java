/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 6824477
 * @summary Selector.select can fail with IOException "Invalid argument" on
 *     Solaris if maximum number of file descriptors is less than 10000
 * @requires (os.family != "windows")
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *        LotsOfUpdates
 * @run main LotsOfUpdatesTest
 */

import jdk.test.lib.process.ProcessTools;

public class LotsOfUpdatesTest {

    //hard limit needs to be less than 10000 for this bug
    private static final String ULIMIT_SET_CMD = "ulimit -n 2048";

    private static final String JAVA_CMD = ProcessTools.getCommandLine(
            ProcessTools.createLimitedTestJavaProcessBuilder(LotsOfUpdates.class.getName()));

    public static void main(String[] args) throws Throwable {
        ProcessTools.executeCommand("sh", "-c", ULIMIT_SET_CMD + " && " + JAVA_CMD)
                    .shouldHaveExitValue(0);
    }
}
