/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 4274624
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *        GetContentType GetContentTypeTest
 * @run main/othervm GetContentTypeTest
 * @summary Test JarURLConnection.getContentType would
 *          return default "content/unknown"
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetContentTypeTest {
    public static void main(String[] args) throws Throwable {
        Path resJar = Paths.get(System.getProperty("test.src"),
                "resource.jar");
        Path classes = Paths.get(System.getProperty("test.classes"));
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "-cp", resJar + File.pathSeparator + classes, "GetContentType");
        new OutputAnalyzer(pb.start())
                .outputTo(System.out)
                .errorTo(System.out)
                .shouldHaveExitValue(0);
    }
}
