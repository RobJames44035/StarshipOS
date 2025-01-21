/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4842196
 * @summary Test that there is no difference between the JMX version and the
 * JDK version.
 * @author Luis-Miguel Alventosa
 *
 * @library /test/lib
 * @run build ImplVersionTest ImplVersionCommand ImplVersionReader
 * @run main ImplVersionTest
 */

import jdk.test.lib.process.ProcessTools;

import java.io.File;

public class ImplVersionTest {

    public static void main(String[] args) throws Exception {
        // Get test src
        //
        String testSrc = System.getProperty("test.src");
        System.out.println("testSrc = " + testSrc);
        // Get test classes
        //
        String testClasses = System.getProperty("test.classes");
        System.out.println("testClasses = " + testClasses);
        // Get boot class path
        //
        String[] command = new String[] {
            "-Dtest.classes=" + testClasses,
            "ImplVersionCommand",
            System.getProperty("java.runtime.version")
        };

        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(command);
        Process proc = pb.start();
        new ImplVersionReader(proc, proc.getInputStream()).start();
        new ImplVersionReader(proc, proc.getErrorStream()).start();
        int exitValue = proc.waitFor();
        System.out.println("ImplVersionCommand Exit Value = " + exitValue);
        if (exitValue != 0) {
            throw new RuntimeException("TEST FAILED: Incorrect exit value " +
                                       "from ImplVersionCommand " + exitValue);
        }
        // Test OK!
        //
        System.out.println("Bye! Bye!");
    }
}
