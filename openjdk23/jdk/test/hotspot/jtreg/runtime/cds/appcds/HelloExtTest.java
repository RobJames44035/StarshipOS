/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary a simple test for loading a class using the platform class loader
 *          (which used to be called the "extension loader) in AppCDS
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/HelloExt.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver HelloExtTest
 */

import jdk.test.lib.process.OutputAnalyzer;

public class HelloExtTest {

  public static void main(String[] args) throws Exception {
    JarBuilder.build("helloExt", "HelloExt");

    String appJar = TestCommon.getTestJar("helloExt.jar");
    JarBuilder.build(true, "WhiteBox", "jdk/test/whitebox/WhiteBox");
    String whiteBoxJar = TestCommon.getTestJar("WhiteBox.jar");
    String bootClassPath = "-Xbootclasspath/a:" + whiteBoxJar;

    TestCommon.dump(appJar,
        TestCommon.list("javax/annotation/processing/FilerException", "[Ljava/lang/Comparable;"),
        bootClassPath);

    String prefix = ".class.load. ";
    String class_pattern = ".*LambdaForm[$]MH[/][0123456789].*";
    String suffix = ".*source: shared objects file.*";
    String pattern = prefix + class_pattern + suffix;

    TestCommon.run("-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI",
            "-cp", appJar, bootClassPath, "-Xlog:class+load", "HelloExt")
        .assertNormalExit(output -> output.shouldNotMatch(pattern));


    TestCommon.run("-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI",
            "-cp", appJar, bootClassPath, "-Xlog:class+load",
            "-XX:+PrintSharedArchiveAndExit",
            "HelloExt")
        .assertNormalExit(output ->  output.shouldNotMatch(class_pattern));
  }
}
