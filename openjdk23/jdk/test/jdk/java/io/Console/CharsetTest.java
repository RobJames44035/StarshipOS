/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Paths;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/**
 * @test
 * @bug 8264208 8265918
 * @summary Tests Console.charset() method. "expect" command in Windows/Cygwin
 *          does not work as expected. Ignoring tests on Windows.
 * @requires (os.family == "linux") | (os.family == "mac")
 * @library /test/lib
 * @run main CharsetTest en_US.ISO8859-1 ISO-8859-1
 * @run main CharsetTest en_US.US-ASCII US-ASCII
 * @run main CharsetTest en_US.UTF-8 UTF-8
 */
public class CharsetTest {
    public static void main(String... args) throws Throwable {
        if (args.length == 0) {
            // no arg means child java process being tested.
            Console con = System.console();
            System.out.println(con.charset());
            return;
        } else {
            // check "expect" command availability
            var expect = Paths.get("/usr/bin/expect");
            if (!Files.exists(expect) || !Files.isExecutable(expect)) {
                System.out.println("'expect' command not found. Test ignored.");
                return;
            }

            // invoking "expect" command
            var testSrc = System.getProperty("test.src", ".");
            var testClasses = System.getProperty("test.classes", ".");
            var jdkDir = System.getProperty("test.jdk");
            OutputAnalyzer output = ProcessTools.executeProcess(
                    "expect",
                    "-n",
                    testSrc + "/script.exp",
                    jdkDir + "/bin/java",
                    args[0],
                    args[1],
                    testClasses);
            output.reportDiagnosticSummary();
            var eval = output.getExitValue();
            if (eval != 0) {
                throw new RuntimeException("Test failed. Exit value from 'expect' command: " + eval);
            }
        }
    }
}
