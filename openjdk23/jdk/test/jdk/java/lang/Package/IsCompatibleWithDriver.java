/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4227825 4785473
 * @summary Test behaviour of Package.isCompatibleWith().
 * @library /test/lib
 * @build A IsCompatibleWith
 *        jdk.test.lib.util.JarUtils
 *        jdk.test.lib.process.*
 * @run main IsCompatibleWithDriver
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Manifest;

import static java.io.File.pathSeparator;

import java.io.InputStream;
import java.nio.file.Files;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.util.JarUtils;

public class IsCompatibleWithDriver {
    public static void main(String args[]) throws Throwable {
        Path classes = Paths.get(System.getProperty("test.classes", ""));
        Path manifest = Paths.get(System.getProperty("test.src"), "test.mf");
        try (InputStream is = Files.newInputStream(manifest)) {
            JarUtils.createJarFile(Paths.get("test.jar"), new Manifest(is),
                    classes, classes.resolve("p"));
        }
        Files.delete(classes.resolve("p").resolve("A.class"));

        OutputAnalyzer analyzer = ProcessTools.executeTestJava("-cp",
                "test.jar" + pathSeparator + classes.toString(), "IsCompatibleWith");
        System.out.println(analyzer.getOutput());
        analyzer.shouldHaveExitValue(0);
    }
}
