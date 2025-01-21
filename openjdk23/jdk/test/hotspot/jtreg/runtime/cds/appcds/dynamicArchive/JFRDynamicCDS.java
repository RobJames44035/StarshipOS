/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8268470
 * @summary Test dynamic CDS with JFR recording.
 *          Dynamic dump should skip the class such as jdk/jfr/events/FileReadEvent
 *          if one of its super classes has been redefined during JFR startup.
 * @requires vm.cds & vm.hasJFR
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build JFRDynamicCDSApp jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar jfr_dynamic_cds_app.jar JFRDynamicCDSApp JFRDynamicCDSApp$StressEvent
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. JFRDynamicCDS
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class JFRDynamicCDS extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(JFRDynamicCDS::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("jfr_dynamic_cds_app.jar");
        String mainClass = "JFRDynamicCDSApp";
        dump(topArchiveName,
            "-Xlog:class+load,cds=debug",
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                output.shouldHaveExitValue(0)
                      .shouldMatch("Skipping.jdk/jfr/events.*Has.been.redefined");
            });

        run(topArchiveName,
            "-Xlog:class+load=info",
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                output.shouldHaveExitValue(0)
                      .shouldMatch(".class.load. jdk.jfr.events.*source:.*jrt:/jdk.jfr")
                      .shouldContain("[class,load] JFRDynamicCDSApp source: shared objects file (top)");
            });
    }
}
