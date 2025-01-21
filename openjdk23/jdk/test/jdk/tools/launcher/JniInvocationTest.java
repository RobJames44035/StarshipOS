/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8213362
 * @comment Test uses custom launcher that starts VM using JNI via libjli, only for MacOS
 * @requires os.family == "mac"
 * @library /test/lib
 * @run main/native JniInvocationTest
 */

import java.util.Map;
import jdk.test.lib.Platform;
import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JniInvocationTest {
    public static void main(String[] args) throws IOException {
        Path launcher = Paths.get(System.getProperty("test.nativepath"), "JniInvocationTest");
        System.out.println("Launcher = " + launcher + (Files.exists(launcher) ? " (exists)" : " (not exists)"));
        ProcessBuilder pb = new ProcessBuilder(launcher.toString());
        Map<String, String> env = pb.environment();
        String libdir = Paths.get(Utils.TEST_JDK).resolve("lib").toAbsolutePath().toString();
        env.compute(Platform.sharedLibraryPathVariableName(), (k, v) -> (k == null) ? libdir : v + ":" + libdir);
        OutputAnalyzer outputf = new OutputAnalyzer(pb.start());
        outputf.shouldHaveExitValue(0);
    }
}

