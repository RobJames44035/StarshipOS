/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testng.Assert.fail;

public class UpcallTestHelper extends NativeTestHelper {

    public OutputAnalyzer runInNewProcess(Class<?> target, boolean useSpec, String... programArgs) throws IOException, InterruptedException {
        return runInNewProcess(target, useSpec, List.of(), List.of(programArgs));
    }

    public OutputAnalyzer runInNewProcess(Class<?> target, boolean useSpec, List<String> vmArgs, List<String> programArgs) throws IOException, InterruptedException {
        assert !target.isArray();

        List<String> command = new ArrayList<>(List.of(
            "--enable-native-access=ALL-UNNAMED",
            "-Djava.library.path=" + System.getProperty("java.library.path"),
            "-Djdk.internal.foreign.UpcallLinker.USE_SPEC=" + useSpec
        ));
        command.addAll(vmArgs);
        command.add(target.getName());
        command.addAll(programArgs);

        try {
            ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(command);
            // note that it's important to use ProcessTools.startProcess here since this makes sure output streams of the
            // fork don't fill up, which could make the process stall while writing to stdout/stderr
            Process process = ProcessTools.startProcess(target.getName(), pb, null, null, 1L, TimeUnit.MINUTES);
            OutputAnalyzer output = new OutputAnalyzer(process);
            output.outputTo(System.out);
            output.errorTo(System.err);
            return output;
        } catch (TimeoutException e) {
            fail("Timeout while waiting for forked process");
            return null;
        }
    }
}
