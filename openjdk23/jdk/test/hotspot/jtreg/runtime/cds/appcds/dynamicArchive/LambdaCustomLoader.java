/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Lambda proxy class loaded by a custom class loader will not be archived.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build CustomLoaderApp LambHello jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar custom_loader_app.jar CustomLoaderApp LambHello
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. LambdaCustomLoader
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class LambdaCustomLoader extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(LambdaCustomLoader::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("custom_loader_app.jar");
        String mainClass = "CustomLoaderApp";

        // 1. Host class loaded by a custom loader is initialized during dump time.
        dump(topArchiveName,
            "-Xlog:class+load,cds=debug,cds+dynamic",
            "-cp", appJar, mainClass, appJar, "init", "keep-alive")
            .assertNormalExit(output -> {
                output.shouldMatch("Skipping.LambHello[$][$]Lambda.*0x.*:.Hidden.class")
                      .shouldHaveExitValue(0);
            });

        run(topArchiveName,
            "-Xlog:class+load,class+unload",
            "-cp", appJar, mainClass, appJar, "init")
            .assertNormalExit(output -> {
                output.shouldMatch("class.load.*LambHello[$][$]Lambda.*0x.*source:.LambHello")
                      .shouldContain("LambHello source: shared objects file (top)")
                      .shouldHaveExitValue(0);
            });

        // 2. Host class loaded by a custom loader is NOT initialized during dump time.
        dump(topArchiveName,
            "-Xlog:class+load,cds=debug,cds+dynamic",
            "-cp", appJar, mainClass, appJar, "keep-alive")
            .assertNormalExit(output -> {
                output.shouldHaveExitValue(0);
            });

        run(topArchiveName,
            "-Xlog:class+load,class+unload",
            "-cp", appJar, mainClass, appJar)
            .assertNormalExit(output -> {
                output.shouldContain("LambHello source: shared objects file (top)")
                      .shouldHaveExitValue(0);
            });
    }
}
