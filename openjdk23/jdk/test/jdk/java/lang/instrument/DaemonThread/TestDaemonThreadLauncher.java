/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestDaemonThreadLauncher {
    public static void main(String args[]) throws Exception {
        for(int i=0; i<50; i++) {
            ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-javaagent:DummyAgent.jar", "TestDaemonThread", ".");
            OutputAnalyzer analyzer = ProcessTools.executeProcess(pb);
            analyzer.shouldNotContain("ASSERTION FAILED");
            analyzer.shouldHaveExitValue(0);
        }
    }
}
