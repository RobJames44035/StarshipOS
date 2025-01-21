/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8304147
 * @summary Test the exit race for dynamic dumping at exit
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build ExitRace jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar exitRace.jar ExitRace ExitRace$1 ExitRace$1$1
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. ExitRaceTest
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class ExitRaceTest extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(ExitRaceTest::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("exitRace.jar");
        String mainClass = "ExitRace";

        dump(topArchiveName,
             "-Xlog:cds+dynamic=debug",
             "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                                output.shouldHaveExitValue(0)
                                      .shouldContain("Preparing for dynamic dump")
                                      .reportDiagnosticSummary();
                              });
    }
}
