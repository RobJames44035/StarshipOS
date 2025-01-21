/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @test SystemMembarHandshakeTransitionTest
 * @summary This does a sanity test of the poll in the native wrapper.
 * @requires os.family == "linux" | os.family == "windows"
 * @library /testlibrary /test/lib
 * @build SystemMembarHandshakeTransitionTest HandshakeTransitionTest
 * @run main/native SystemMembarHandshakeTransitionTest
 */

public class SystemMembarHandshakeTransitionTest {

    public static void main(String[] args) throws Exception {
        List<String> commands = new ArrayList<>();
        commands.add("-Djava.library.path=" + Utils.TEST_NATIVE_PATH);
        commands.add("-XX:+UnlockDiagnosticVMOptions");
        commands.add("-XX:+SafepointALot");
        commands.add("-XX:+HandshakeALot");
        commands.add("-XX:GuaranteedSafepointInterval=20");
        commands.add("-XX:ParallelGCThreads=1");
        commands.add("-XX:ConcGCThreads=1");
        commands.add("-XX:CICompilerCount=2");
        commands.add("-XX:+UnlockExperimentalVMOptions");
        commands.add("-XX:+UseSystemMemoryBarrier");
        commands.addAll(Arrays.asList(args));
        commands.add("HandshakeTransitionTest$Test");
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(commands);

        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        output.reportDiagnosticSummary();
        output.shouldMatch("(JOINED|Failed to initialize the requested system memory barrier synchronization.)");
    }
}
