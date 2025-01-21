/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

 /*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.process.OutputAnalyzer
 * @build ProgramTest
 * @run main/native ProgramTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class ProgramTest {
    public static void main(String... args) throws Exception {
        String lib = System.getProperty("test.nativepath");
        ProcessBuilder pb = new ProcessBuilder(lib + "/sanity_SimpleNativeLauncher");
        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        output.shouldHaveExitValue(0);
        output.stdoutShouldContain("Hello");
    }
}
