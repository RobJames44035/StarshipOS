/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8333086
 * @summary Verify the JLine backend is not initialized for simple printing.
 * @enablePreview
 * @modules jdk.internal.le/jdk.internal.org.jline.reader
 *          jdk.internal.le/jdk.internal.org.jline.terminal
 * @library /test/lib
 * @run main LazyJdkConsoleProvider
 */

import java.io.IO;
import jdk.internal.org.jline.reader.LineReader;
import jdk.internal.org.jline.terminal.Terminal;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class LazyJdkConsoleProvider {

    public static void main(String... args) throws Throwable {
        switch (args.length > 0 ? args[0] : "default") {
            case "write" -> {
                System.console().println("Hello!");
                System.console().print("Hello!");
                System.console().format("\nHello!\n");
                System.console().flush();
                IO.println("Hello!");
                IO.print("Hello!");
            }
            case "read" -> System.console().readLine("Hello!");
            case "IO-read" -> {
                IO.readln("Hello!");
            }
            case "default" -> {
                new LazyJdkConsoleProvider().runTest();
            }
        }
    }

    void runTest() throws Exception {
        record TestCase(String testKey, String expected, String notExpected) {}
        TestCase[] testCases = new TestCase[] {
            new TestCase("write", null, Terminal.class.getName()),
            new TestCase("read", LineReader.class.getName(), null),
            new TestCase("IO-read", LineReader.class.getName(), null)
        };
        for (TestCase tc : testCases) {
            ProcessBuilder builder =
                    ProcessTools.createTestJavaProcessBuilder("--enable-preview",
                                                              "-verbose:class",
                                                              "-Djdk.console=jdk.internal.le",
                                                              LazyJdkConsoleProvider.class.getName(),
                                                              tc.testKey());
            OutputAnalyzer output = ProcessTools.executeProcess(builder, "");

            output.waitFor();

            if (output.getExitValue() != 0) {
                throw new AssertionError("Unexpected return value: " + output.getExitValue() +
                                         ", actualOut: " + output.getStdout() +
                                         ", actualErr: " + output.getStderr());
            }
            if (tc.expected() != null) {
                output.shouldContain(tc.expected());
            }

            if (tc.notExpected() != null) {
                output.shouldNotContain(tc.notExpected());
            }
        }
    }

}
