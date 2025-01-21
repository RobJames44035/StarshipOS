/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Initiating and defining classloader test.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @compile test-classes/HelloWB.java
 * @compile test-classes/ForNameTest.java
 * @compile test-classes/BootClassPathAppendHelper.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver ClassLoaderTest
 */

import java.io.File;
import jdk.test.lib.process.OutputAnalyzer;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        JarBuilder.build(true, "ClassLoaderTest-WhiteBox", "jdk/test/whitebox/WhiteBox");
        JarBuilder.getOrCreateHelloJar();
        JarBuilder.build("ClassLoaderTest-HelloWB", "HelloWB");
        JarBuilder.build("ClassLoaderTest-ForName", "ForNameTest");
        ClassLoaderTest test = new ClassLoaderTest();
        test.testBootLoader();
        test.testDefiningLoader();
    }

    public void testBootLoader() throws Exception {
        String appJar = TestCommon.getTestJar("ClassLoaderTest-HelloWB.jar");
        String appClasses[] = {"HelloWB"};
        String whiteBoxJar = TestCommon.getTestJar("ClassLoaderTest-WhiteBox.jar");
        String bootClassPath = "-Xbootclasspath/a:" + appJar +
            File.pathSeparator + whiteBoxJar;

        TestCommon.dump(appJar, appClasses, bootClassPath);

        TestCommon.run(
            "-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI",
            "-cp", appJar, bootClassPath, "HelloWB")
          .assertNormalExit(output -> output.shouldContain("HelloWB.class.getClassLoader() = null"));
    }

    public void testDefiningLoader() throws Exception {
        // The boot loader should be used to load the class when it's
        // on the bootclasspath, regardless who is the initiating classloader.
        // In this test case, the AppClassLoader is the initiating classloader.
        String helloJar = TestCommon.getTestJar("hello.jar");
        String appJar = helloJar + System.getProperty("path.separator") +
                        TestCommon.getTestJar("ClassLoaderTest-ForName.jar");
        String whiteBoxJar = TestCommon.getTestJar("ClassLoaderTest-WhiteBox.jar");
        String bootClassPath = "-Xbootclasspath/a:" + helloJar +
            File.pathSeparator + whiteBoxJar;

        // Archive the "Hello" class from the appended bootclasspath
        TestCommon.dump(helloJar, TestCommon.list("Hello"), bootClassPath);

        TestCommon.run("-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI",
            "-cp", appJar, bootClassPath, "-Xlog:class+path=trace", "ForNameTest")
          .assertNormalExit();
    }
}
