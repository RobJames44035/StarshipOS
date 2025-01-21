/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290732
 * @comment Test uses custom launcher that attempts to destroy the VM on both
 *          a daemon and non-daemon thread. The result should be the same in
 *          both cases.
 * @requires vm.flagless
 * @library /test/lib
 * @build Main
 * @run main/native TestDaemonDestroy
 * @run main/native TestDaemonDestroy daemon
 */

// Logic copied from SigTestDriver

import jdk.test.lib.Platform;
import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestDaemonDestroy {

    public static void main(String[] args) throws IOException {
        Path launcher = Paths.get(Utils.TEST_NATIVE_PATH)
            .resolve("daemonDestroy" + (Platform.isWindows() ? ".exe" : ""))
            .toAbsolutePath();

        System.out.println("Launcher = " + launcher +
                           (Files.exists(launcher) ? " (exists)" : " (missing)"));

        List<String> cmd = new ArrayList<>();
        cmd.add(launcher.toString());
        cmd.add("-Djava.class.path=" + Utils.TEST_CLASS_PATH);
        if (args.length > 0) {
            cmd.add("daemon");
        }
        ProcessBuilder pb = new ProcessBuilder(cmd);

        // Need to add libjvm location to LD_LIBRARY_PATH
        String envVar = Platform.sharedLibraryPathVariableName();
        pb.environment().merge(envVar, Platform.jvmLibDir().toString(),
                               (x, y) -> y + File.pathSeparator + x);

        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldHaveExitValue(0);
        oa.shouldNotContain("Error: T1 isAlive");
        oa.shouldContain("T1 finished");
        oa.reportDiagnosticSummary();
    }
}
