/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Make sure CDS works with a minimal test case that uses a CONSTANT_Dynamic constant-pool entry
 * @requires (vm.cds)
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build CondyHello
 * @build jdk.test.whitebox.WhiteBox CondyHelloTest CondyHelloApp
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar condy_hello.jar CondyHello CondyHelloApp
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run driver CondyHelloTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class CondyHelloTest {

    static String classes[] = {
        "CondyHello",
        "CondyHelloApp",
    };

    public static void main(String[] args) throws Exception {
        String wbJar = ClassFileInstaller.getJarPath("WhiteBox.jar");
        String use_whitebox_jar = "-Xbootclasspath/a:" + wbJar;
        String appJar = ClassFileInstaller.getJarPath("condy_hello.jar");

        TestCommon.dump(appJar, TestCommon.list(classes), use_whitebox_jar);

        TestCommon.run("-XX:+UnlockDiagnosticVMOptions",
                       "-XX:+WhiteBoxAPI",
                       "-cp", appJar,
                       use_whitebox_jar,
                       "CondyHelloApp")
          .assertNormalExit();
    }
}
