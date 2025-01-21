/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Utils;

/* @test
 * @bug 6354345
 * @summary Check that multiple -agentlib statements in command line fails
 *
 * @library /test/lib
 * @modules java.management
 * @build DoubleAgentTest Exit0
 * @run driver DoubleAgentTest
 */

public class DoubleAgentTest {

    private static final String TEST_CLASSES = System.getProperty(
            "test.classes", ".");

    public static void main(String[] args) throws Throwable {
        String jdwpOption = "-agentlib:jdwp=transport=dt_socket"
                         + ",server=y" + ",suspend=n" + ",address=*:0";

        OutputAnalyzer output = ProcessTools.executeTestJava("-classpath",
                TEST_CLASSES,
                jdwpOption, // Notice jdwpOption specified twice
                jdwpOption,
                "Exit0");

        output.shouldContain("Cannot load this JVM TI agent twice");
        output.shouldHaveExitValue(1);
    }

}
