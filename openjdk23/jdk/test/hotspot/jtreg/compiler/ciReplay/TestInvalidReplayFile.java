/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */


/*
 * @test
 * @summary Test that VM rejects and invalid replay file.
 * @library /test/lib
 * @requires vm.compMode != "Xint"
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestInvalidReplayFile
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.io.FileWriter;

public class TestInvalidReplayFile {


    public static void main(String[] args) throws Exception {

        // This test also serves as a very basic sanity test for release VMs (accepting the replay options and
        // attempting to read the replay file). Most of the tests in ciReplay use -XX:CICrashAt to induce artificial
        // crashes into the compiler, and that option is not available for release VMs. Therefore we cannot generate
        // replay files as a test in release builds.

        File f = new File("bogus-replay-file.txt");
        FileWriter w = new FileWriter(f);
        w.write("Bogus 123");
        w.flush();
        w.close();

        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "-XX:+UnlockDiagnosticVMOptions",
                "-Xmx100M",
                "-XX:+ReplayCompiles", "-XX:ReplayDataFile=./bogus-replay-file.txt");

        OutputAnalyzer output_detail = new OutputAnalyzer(pb.start());
        output_detail.shouldNotHaveExitValue(0);
        output_detail.shouldContain("Error while parsing");

    }
}


