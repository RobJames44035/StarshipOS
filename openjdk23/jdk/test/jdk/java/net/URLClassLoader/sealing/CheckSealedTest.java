/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 4244970
 * @summary Test to see if sealing violation is detected correctly
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main CheckSealedTest
 */

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static jdk.test.lib.process.ProcessTools.createTestJavaProcessBuilder;
import static jdk.test.lib.process.ProcessTools.executeCommand;

public class CheckSealedTest {
    private static final String ARCHIVE_NAME = "b.jar";
    private static final String TEST_NAME = "CheckSealed";
    public static void main(String[] args)
            throws Throwable {

        String baseDir = System.getProperty("user.dir") + File.separator;
        String javac = JDKToolFinder.getTestJDKTool("javac");

        setup(baseDir);
        String srcDir = System.getProperty("test.src");
        String cp = srcDir + File.separator + "a" + File.pathSeparator
                + srcDir + File.separator + "b.jar" + File.pathSeparator
                + ".";

        // Compile
        ProcessTools.executeCommand(javac, "-cp", cp, "-d", ".",
                srcDir + File.separator + TEST_NAME + ".java");

        List<String[]> allCMDs = List.of(
                // Run test the first time
                new String[]{
                        "-cp", cp, TEST_NAME, "1"
                },
                // Run test the second time
                new String[]{
                        "-cp", cp, TEST_NAME, "2"
                }
        );

        for (String[] cmd : allCMDs) {
            executeCommand(createTestJavaProcessBuilder(cmd))
                        .outputTo(System.out)
                        .errorTo(System.out)
                        .shouldHaveExitValue(0);
        }
    }

    private static void setup(String baseDir) throws IOException {
        Path testJar = Paths.get(System.getProperty("test.src"), ARCHIVE_NAME);
        Files.copy(testJar, Paths.get(baseDir, ARCHIVE_NAME), REPLACE_EXISTING);
    }
}
