/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/* @test
 * @bug 4914611
 * @summary Test for JDWP: -agentlib:jdwp=suspend=n hanging
 * @library /test/lib
 * @modules java.management
 * @compile -g HelloWorld.java
 * @run driver SuspendNoFlagTest
 */
public class SuspendNoFlagTest {

    private static final String TEST_CLASSES = System.getProperty(
            "test.classes", ".");

    public static void main(String[] args) throws Throwable {
        OutputAnalyzer output = ProcessTools.executeTestJava("-classpath",
                TEST_CLASSES,
                "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n",
                "HelloWorld");
        output.shouldHaveExitValue(0);
    }

}
