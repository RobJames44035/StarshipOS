/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @requires os.family != "Windows"
 * @run driver TestGetCreatedJavaVMs
 */
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;


public class TestGetCreatedJavaVMs {
    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("GetCreatedJavaVMs");
        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        output.shouldHaveExitValue(0);
        output.reportDiagnosticSummary();
    }
}
