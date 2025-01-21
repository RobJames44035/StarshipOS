/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test id=default
 * @bug 8292695
 * @summary Check that Ctrl-\ or Ctrl-Break (on Windows) causes HotSpot VM to print a full thread dump.
 * @library /vmTestbase
 *          /test/lib
 * @run driver TestBreakSignalThreadDump
 */

/*
 * @test id=with_jsig
 * @bug 8292695
 * @summary Check that Ctrl-\ causes HotSpot VM to print a full thread dump when signal chaining is used.
 * @requires os.family != "windows" & os.family != "aix"
 * @library /vmTestbase
 *          /test/lib
 * @run driver TestBreakSignalThreadDump load_libjsig
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import jdk.test.lib.Platform;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import vm.share.ProcessUtils;

public class TestBreakSignalThreadDump {

    static class TestProcess {
        static {
            System.loadLibrary("ProcessUtils");
        }

        public static void main(String[] argv) throws Exception {
            ProcessUtils.sendCtrlBreak();
            // Wait a bit, as JVM processes the break signal asynchronously.
            Thread.sleep(1000);
            System.out.println("Done!");
        }
    }

    public static void main(String[] argv) throws Exception {
        String main = "TestBreakSignalThreadDump$TestProcess";
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-Djava.library.path=" + Utils.TEST_NATIVE_PATH, main);

        if (argv.length > 0 && argv[0].equals("load_libjsig")) {
            prepend_jsig_lib(pb.environment());
        }

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        output.shouldContain("Full thread dump ");
        output.shouldContain("java.lang.Thread.State: RUNNABLE");
        output.shouldContain("Done!");
    }

    private static void prepend_jsig_lib(Map<String, String> env) {
        Path libjsig = Platform.jvmLibDir().resolve("libjsig." + Platform.sharedLibraryExt());
        if (!Files.exists(libjsig)) {
            throw new RuntimeException("File libjsig not found, path: " + libjsig);
        }
        String env_var = Platform.isOSX() ? "DYLD_INSERT_LIBRARIES" : "LD_PRELOAD";
        env.put(env_var, libjsig.toString());
    }
}
