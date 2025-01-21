/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Linux kernel stack guard should not cause segfaults on x86-32
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @requires os.family == "linux"
 * @compile T.java
 * @run main/native TestStackGap
 */


import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestStackGap {
    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("stack-gap");
        pb.environment().put("CLASSPATH", Utils.TEST_CLASS_PATH);
        new OutputAnalyzer(pb.start())
            .shouldHaveExitValue(0);

        pb = ProcessTools.createNativeTestProcessBuilder("stack-gap",
                                                         "-XX:+DisablePrimordialThreadGuardPages");
        pb.environment().put("CLASSPATH", Utils.TEST_CLASS_PATH);
        new OutputAnalyzer(pb.start())
            .shouldHaveExitValue(0);
    }
}

