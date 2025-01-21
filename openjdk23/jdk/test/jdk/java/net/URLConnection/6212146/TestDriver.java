/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 6212146
 * @summary URLConnection.connect() fails on JAR Entry it creates
 * file handler leak
 * @library /test/lib
 * @requires vm.flagless
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *        Test
 * @run main/othervm TestDriver
 */

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.ProcessTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TestDriver {
    private static final String BASE_DIR = System.getProperty("user.dir")
            +  "/jars/";
    private static final String ARCHIVE_NAME = "test.jar";
    private static final String CMD_ULIMIT = "ulimit -n 300;";

    public static void main(String[] args)
            throws Throwable {
        setup(BASE_DIR);
        String testCMD = CMD_ULIMIT + JDKToolFinder.getTestJDKTool("java")
                + " Test " + BASE_DIR + " " + ARCHIVE_NAME;
        boolean isWindows = System.getProperty("os.name").startsWith("Windows");
        if (isWindows) {
            testCMD = testCMD.replace("\\", "/");
        }
        ProcessTools.executeCommand("sh", "-c", testCMD)
                    .outputTo(System.out)
                    .errorTo(System.err)
                    .shouldHaveExitValue(0);
    }

    private static void setup(String baseDir) throws IOException {
        Path testJar = Paths.get(System.getProperty("test.src"), ARCHIVE_NAME);
        Path targetDir = Paths.get(baseDir);
        Files.createDirectories(targetDir);
        Files.copy(testJar, targetDir.resolve(ARCHIVE_NAME), REPLACE_EXISTING);
    }
}
