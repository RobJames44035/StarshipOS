/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8225193
 * @requires os.family != "windows"
 * @library /test/lib
 * @run driver RemovingUnixDomainSocketTest
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import jdk.test.lib.Utils;
import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class RemovingUnixDomainSocketTest {

    // timeout (in seconds)
    private static final long timeout = Utils.adjustTimeout(60);

    private static void runJCmd(long pid) throws InterruptedException, IOException {
        JDKToolLauncher jcmd = JDKToolLauncher.createUsingTestJDK("jcmd");
        jcmd.addVMArgs(Utils.getFilteredTestJavaOpts("-showversion"));
        jcmd.addToolArg(Long.toString(pid));
        jcmd.addToolArg("VM.version");

        ProcessBuilder pb = new ProcessBuilder(jcmd.getCommand());
        Process jcmdProc = pb.start();

        OutputAnalyzer out = new OutputAnalyzer(jcmdProc);

        if (!jcmdProc.waitFor(timeout, TimeUnit.SECONDS)) {
            log("jcmd is still running after " + timeout + " seconds, terminating...");
            jcmdProc.destroy();
            jcmdProc.waitFor();
        }

        log("jcmd stdout: [" + out.getStdout() + "];\n" +
            "jcmd  stderr: [" + out.getStderr() + "]\n" +
            "jcmd  exitValue = " + out.getExitValue());

        out.shouldHaveExitValue(0);
        out.stderrShouldBeEmptyIgnoreDeprecatedWarnings();
    }

    public static void main(String... args) throws Exception {
        LingeredApp app = null;
        try {
            app = LingeredApp.startApp();

            // Access to Attach Listener
            runJCmd(app.getPid());

            // Remove unix domain socket file
            File sockFile = Path.of(System.getProperty("java.io.tmpdir"),
                                   ".java_pid" + app.getPid())
                               .toFile();
            log("Remove " + sockFile.toString());
            sockFile.delete();

            // Access to Attach Listener again
            runJCmd(app.getPid());
        } finally {
            LingeredApp.stopApp(app);
        }
    }

    static void log(Object s) {
        System.out.println(String.valueOf(s));
    }
}
