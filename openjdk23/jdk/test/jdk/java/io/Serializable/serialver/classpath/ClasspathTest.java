/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4035147 4785472
 * @library /test/lib
 * @build jdk.test.lib.Utils
 * @build jdk.test.lib.Asserts
 * @build jdk.test.lib.JDKToolFinder
 * @build jdk.test.lib.JDKToolLauncher
 * @build jdk.test.lib.Platform
 * @build jdk.test.lib.process.*
 * @build ClasspathTest
 * @run main serialver.ClasspathTest
 * @summary Test the use of the -classpath switch in the serialver application.
 */

package serialver;

import java.io.File;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.ProcessTools;

public class ClasspathTest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    int a;
    int b;

    public static void main(String args[]) throws Exception {
        JDKToolLauncher serialver =
                JDKToolLauncher.create("serialver")
                               .addToolArg("-classpath")
                               .addToolArg(System.getProperty("test.class.path"))
                               .addToolArg("serialver.ClasspathTest");
        Process p = ProcessTools.startProcess("serialver",
                        new ProcessBuilder(serialver.getCommand()));
        p.waitFor();
        if (p.exitValue() != 0) {
            throw new RuntimeException("error occurs in serialver");
        }
    }
}
