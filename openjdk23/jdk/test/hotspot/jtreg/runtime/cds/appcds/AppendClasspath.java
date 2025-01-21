/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary At run time, it is OK to append new elements to the classpath that was used at dump time.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @compile test-classes/HelloMore.java
 * @run driver AppendClasspath
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class AppendClasspath {

  public static void main(String[] args) throws Exception {
    String appJar = JarBuilder.getOrCreateHelloJar();
    String appJar2 = JarBuilder.build("AppendClasspath_HelloMore", "HelloMore");

    // Dump an archive with a specified JAR file in -classpath
    TestCommon.testDump(appJar, TestCommon.list("Hello"));

    // PASS: 1) runtime with classpath containing the one used in dump time
    TestCommon.run(
        "-cp", appJar + File.pathSeparator + appJar2,
        "HelloMore")
      .assertNormalExit();

    // PASS: 2) runtime has an non-existing jar in the -cp
    String outDir = CDSTestUtils.getOutputDir();
    String newFile = "non-exist.jar";
    String nonExistPath = outDir + File.separator + newFile;
    String classPath = appJar + File.pathSeparator + nonExistPath;
    File nonExistJar = new File(outDir, newFile);
    if (nonExistJar.exists()) {
        nonExistJar.delete();
    }
    TestCommon.run(
        "-cp", classPath,
        "-Xlog:class+path=trace",
        "Hello")
      .assertNormalExit();

    final String errorMessage1 = "Unable to use shared archive";
    final String errorMessage2 = "shared class paths mismatch";
    // FAIL: 1) runtime with classpath different from the one used in dump time
    // (runtime has an extra jar file prepended to the class path)
    TestCommon.run(
        "-Xlog:cds",
        "-cp", appJar2 + File.pathSeparator + appJar,
        "HelloMore")
        .assertAbnormalExit(errorMessage1, errorMessage2);

    // FAIL: 2) runtime with classpath part of the one used in dump time
    TestCommon.testDump(appJar + File.pathSeparator + appJar2,
                                      TestCommon.list("Hello"));
    TestCommon.run(
        "-Xlog:cds",
        "-cp", appJar2,
        "Hello")
        .assertAbnormalExit(errorMessage1, errorMessage2);

    // FAIL: 3) runtime with same set of jar files in the classpath but
    // with different order
    TestCommon.run(
        "-Xlog:cds",
        "-cp", appJar2 + File.pathSeparator + appJar,
        "HelloMore")
        .assertAbnormalExit(errorMessage1, errorMessage2);
    }
}
