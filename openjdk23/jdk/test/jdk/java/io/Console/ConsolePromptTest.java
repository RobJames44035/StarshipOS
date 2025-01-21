/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8331681
 * @summary Verify the java.base's console provider handles the prompt correctly.
 * @library /test/lib
 * @run main/othervm --limit-modules java.base ConsolePromptTest
 * @run main/othervm -Djdk.console=java.base ConsolePromptTest
 */

import java.lang.reflect.Method;
import java.util.Objects;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class ConsolePromptTest {

    public static void main(String... args) throws Throwable {
        for (Method m : ConsolePromptTest.class.getDeclaredMethods()) {
            if (m.getName().startsWith("test")) {
                m.invoke(new ConsolePromptTest());
            }
        }
    }

    void testCorrectOutputReadLine() throws Exception {
        doRunConsoleTest("testCorrectOutputReadLine", "inp", "%s");
    }

    void testCorrectOutputReadPassword() throws Exception {
        doRunConsoleTest("testCorrectOutputReadPassword", "inp", "%s");
    }

    void doRunConsoleTest(String testName,
                          String input,
                          String expectedOut) throws Exception {
        ProcessBuilder builder =
                ProcessTools.createTestJavaProcessBuilder(ConsoleTest.class.getName(),
                                                          testName);
        OutputAnalyzer output = ProcessTools.executeProcess(builder, input);

        output.waitFor();

        if (output.getExitValue() != 0) {
            throw new AssertionError("Unexpected return value: " + output.getExitValue() +
                                     ", actualOut: " + output.getStdout() +
                                     ", actualErr: " + output.getStderr());
        }

        String actualOut = output.getStdout();

        if (!Objects.equals(expectedOut, actualOut)) {
            throw new AssertionError("Unexpected stdout content. " +
                                     "Expected: '" + expectedOut + "'" +
                                     ", got: '" + actualOut + "'");
        }

        String expectedErr = "";
        String actualErr = output.getStderr();

        if (!Objects.equals(expectedErr, actualErr)) {
            throw new AssertionError("Unexpected stderr content. " +
                                     "Expected: '" + expectedErr + "'" +
                                     ", got: '" + actualErr + "'");
        }
    }

    public static class ConsoleTest {
        public static void main(String... args) {
            switch (args[0]) {
                case "testCorrectOutputReadLine" ->
                    System.console().readLine("%%s");
                case "testCorrectOutputReadPassword" ->
                    System.console().readPassword("%%s");
                default -> throw new UnsupportedOperationException(args[0]);
            }

            System.exit(0);
        }
    }
}
