/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8183509
 * @summary keytool should not allow multiple commands
 * @library /test/lib
 * @build jdk.test.lib.SecurityTools
 *        jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main/othervm DupCommands
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

public class DupCommands {

    public static void main(String[] args) throws Throwable {

        kt("-list -printcert")
                .shouldHaveExitValue(1)
                .shouldContain("Only one command is allowed");

        kt("-import -importcert") // command with different names
                .shouldHaveExitValue(1)
                .shouldContain("Only one command is allowed");

        kt("-help -v -v")
                .shouldHaveExitValue(0)
                .shouldContain("The -v option is specified multiple times.");

        kt("-help -id 1 -id 2") // some options are allowed multiple times
                .shouldHaveExitValue(0)
                .shouldNotContain("specified multiple times.");
    }

    static OutputAnalyzer kt(String arg) throws Exception {
        return SecurityTools.keytool(arg);
    }
}
