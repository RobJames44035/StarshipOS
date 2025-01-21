/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @summary Handling of duplicated classes in dynamic archive with custom loader
 * @requires vm.cds
 * @library /test/lib
 *          /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/customLoader/test-classes
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build DuplicatedCustomApp CustomLoadee CustomLoadee2 CustomLoadee3 CustomLoadee3Child
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar app.jar DuplicatedCustomApp
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar custom.jar CustomLoadee
 *                  CustomLoadee2 CustomInterface2_ia CustomInterface2_ib
 *                  CustomLoadee3 CustomLoadee3Child
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:./WhiteBox.jar DuplicatedCustomTest
 */

import java.io.File;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class DuplicatedCustomTest extends DynamicArchiveTestBase {
    private static final String ARCHIVE_NAME = CDSTestUtils.getOutputFileName("top.jsa");

    public static void main(String[] args) throws Exception {
        runTest(DuplicatedCustomTest::testDefaultBase);
    }

    private static void testDefaultBase() throws Exception {
        String wbJar = ClassFileInstaller.getJarPath("WhiteBox.jar");
        String use_whitebox_jar = "-Xbootclasspath/a:" + wbJar;
        String appJar = ClassFileInstaller.getJarPath("app.jar");
        String customJarPath = ClassFileInstaller.getJarPath("custom.jar");
        String mainAppClass = "DuplicatedCustomApp";
        String numberOfLoops = "2";

        dump(ARCHIVE_NAME,
            use_whitebox_jar,
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            "-Xlog:cds",
            "-Xlog:cds+dynamic=debug",
            "-cp", appJar,
            mainAppClass, customJarPath, numberOfLoops)
            .assertNormalExit(output -> {
                output.shouldContain("Written dynamic archive 0x")
                      .shouldContain("Skipping CustomLoadee: Duplicated unregistered class")
                      .shouldHaveExitValue(0);
                });

        run(ARCHIVE_NAME,
            use_whitebox_jar,
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            "-Xlog:class+load",
            "-Xlog:cds=debug",
            "-Xlog:cds+dynamic=info",
            "-cp", appJar,
            mainAppClass, customJarPath, numberOfLoops)
            .assertNormalExit(output -> {
                output.shouldContain("DuplicatedCustomApp source: shared objects file (top)")
                      .shouldContain("CustomLoadee source: shared objects file (top)")
                      .shouldHaveExitValue(0);
                });
    }
}
