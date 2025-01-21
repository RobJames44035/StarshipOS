/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8213362 8238225 8303669
 * @comment Test JLI_Launch for tools distributed outside JDK
 * @library /test/lib
 * @run main/native JliLaunchTest
 */

import java.util.Map;
import jdk.test.lib.Utils;
import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JliLaunchTest {
    public static void main(String[] args) throws IOException {
        Path launcher = Paths.get(System.getProperty("test.nativepath"),
            "JliLaunchTest" + (Platform.isWindows() ? ".exe" : ""));
        System.out.println("Launcher = " + launcher + (Files.exists(launcher) ? " (exists)" : " (not exists)"));
        ProcessBuilder pb = new ProcessBuilder(launcher.toString(), "--version");
        Map<String, String> env = pb.environment();
        // On windows, the DLL should be in JDK/bin, else in JDK/lib.
        String libdir = Paths.get(Utils.TEST_JDK).resolve(Platform.isWindows() ? "bin" : "lib")
            .toAbsolutePath().toString();
        String pathEnvVar = Platform.sharedLibraryPathVariableName();
        env.compute(pathEnvVar, (k, v) -> (v == null) ? libdir : libdir + File.pathSeparator + v);

        OutputAnalyzer outputf = new OutputAnalyzer(pb.start());
        outputf.shouldHaveExitValue(0);

        if (Platform.isOSX()) {
            Path javaHome = Paths.get(Utils.TEST_JDK);
            if (javaHome.getFileName().toString().equals("Home")) {
                // To exercise this test path you need to make sure the JDK under test is
                // the MacOS bundle and not the simple jdk image. This can currently be
                // achieved by running something like this (from the build output dir):
                // $ make test-only TEST=test/jdk/tools/launcher/JliLaunchTest.java \
                //     JDK_IMAGE_DIR=$PWD/images/jdk-bundle/jdk-15.jdk/Contents/Home
                System.out.println("MacOS bundle distribution detected, also testing Contents/MacOS/libjli.dylib");
                String macosDir = javaHome.getParent().resolve("MacOS").toString();
                ProcessBuilder pb2 = new ProcessBuilder(launcher.toString(), "--version");
                env = pb2.environment();
                env.compute(pathEnvVar, (k, v) -> (v == null) ? macosDir : macosDir + File.pathSeparator + v);

                OutputAnalyzer output2 = new OutputAnalyzer(pb2.start());
                output2.shouldHaveExitValue(0);
            } else {
                System.out.println("Not a MacOS bundle distribution, not testing Contents/MacOS/libjli.dylib");
            }
        }
    }
}
