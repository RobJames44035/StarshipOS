/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @summary Non jar file in the classpath will be skipped during dump time and runtime.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @compile test-classes/HelloMore.java
 * @run driver NonJarInClasspath
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class NonJarInClasspath {

    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.getOrCreateHelloJar();
        String appJar2 = JarBuilder.build("hellomore", "HelloMore");

        String outDir = CDSTestUtils.getOutputDir();
        String newFile = "non-exist.jar";
        String nonJarPath = outDir + File.separator + newFile;
        String classPath = appJar + File.pathSeparator + nonJarPath + File.pathSeparator + appJar2;
        File nonJar = new File(outDir, newFile);
        nonJar.createNewFile();

        TestCommon.testDump(classPath, TestCommon.list("Hello", "HelloMore"));

        TestCommon.run(
            "-cp", classPath,
            "-Xlog:class+load",
            "Hello")
          .assertNormalExit(out -> {
              out.shouldContain("Hello source: shared objects file");
          });
    }
}
