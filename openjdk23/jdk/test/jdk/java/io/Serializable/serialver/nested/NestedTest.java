/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312217 4785473
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @build NestedTest
 * @run main serialver.NestedTest
 * @summary  To test the use of nested class specification using the '.'
 *           notation instead of the '$' notation.
 */

package serialver;

import java.io.Serializable;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.ProcessTools;

public class NestedTest implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class Test1 implements Serializable {
        private static final long serialVersionUID = 1L;

        public static class Test2 implements Serializable{
            private static final long serialVersionUID = 100L;
        }
    }

    public static void main(String args[]) throws Exception {
        JDKToolLauncher serialver =
                JDKToolLauncher.create("serialver")
                               .addToolArg("-classpath")
                               .addToolArg(System.getProperty("test.class.path"))
                               .addToolArg("serialver.NestedTest.Test1.Test2");
        Process p = ProcessTools.startProcess("serialver",
                        new ProcessBuilder(serialver.getCommand()));
        p.waitFor();
        if (p.exitValue() != 0) {
            throw new RuntimeException("error occurs in serialver.");
        }
    }
}
