/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4156278
 * @summary Basic functional test for
 *          Runtime.exec(String[] command, String[] env, File path) and
 *          Runtime.exec(String command, String[] env, File path).
 * @requires vm.flagless
 * @library /test/lib
 * @run testng/othervm SetCwd
 */
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

import static jdk.test.lib.Asserts.assertTrue;

public class SetCwd {

    private static final String TEST_CLASSES = System.getProperty(
            "test.classes", ".");

    private static final String[] CMD_ARRAY = new String[2];

    @BeforeTest
    public static void setUp() throws Exception {
        CMD_ARRAY[0] = System.getProperty("java.home") + File.separator +
                "bin" + File.separator + "java";
        CMD_ARRAY[1] = SimpleProcess.class.getName();
    }

    @Test
    public void testRuntimeExecWithArray() throws Exception {
        Process process = Runtime.getRuntime().exec(CMD_ARRAY, null,
                new File(TEST_CLASSES));
        verifyProcessOutput(process);
    }

    @Test
    public void testRuntimeExecWithString() throws Exception {
        String cmd = String.join(" ", CMD_ARRAY);
        @SuppressWarnings("deprecation")
        Process process = Runtime.getRuntime().exec(cmd, null,
                new File(TEST_CLASSES));
        verifyProcessOutput(process);
    }

    // Verify the process has executed by comparing its output with the expected
    private void verifyProcessOutput(Process process) throws Exception {
        process.waitFor();
        assertTrue(process.exitValue() == 0);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line = reader.readLine();
            if (!line.startsWith(TEST_CLASSES)) {
                String error = String.format("Expected process output first line: " +
                        "'%s' Actual: '%s'", TEST_CLASSES, line);
                throw new Exception(error);
            }
        }
    }

    // This class main will be the entry point for test subprocesses
    static class SimpleProcess {
        public static void main (String[] args) throws Exception {
            File dir = new File(".");
            System.out.println(dir.getCanonicalPath());
        }
    }
}
