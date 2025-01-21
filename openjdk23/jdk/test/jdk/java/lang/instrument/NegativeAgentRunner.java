/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.java.lang.instrument;

import java.lang.RuntimeException;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class NegativeAgentRunner {

    public static void main(String argv[]) throws Exception {
        if (argv.length != 2) {
            throw new RuntimeException("Agent and exception class names are expected in arguments");
        }
        String agentClassName = argv[0];
        String excepClassName = argv[1];
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "-javaagent:" + agentClassName + ".jar", "-Xmx128m", "-XX:-CreateCoredumpOnCrash",
                agentClassName);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain(excepClassName);
        if (0 == output.getExitValue()) {
            throw new RuntimeException("Expected error but got exit value 0");
        }
    }
}
