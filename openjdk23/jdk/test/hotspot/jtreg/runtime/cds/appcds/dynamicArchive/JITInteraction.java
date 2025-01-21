/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary Test interaction with JIT threads during vm exit.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build TestJIT
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar testjit.jar TestJIT
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:./WhiteBox.jar JITInteraction
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class JITInteraction extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(JITInteraction::testDefaultBase);
    }

    // Test with default base archive + top archive
    static void testDefaultBase() throws Exception {
        String topArchiveName = getNewArchiveName("top");
        doTest(topArchiveName);
    }

    private static void doTest(String topArchiveName) throws Exception {
        String appJar = ClassFileInstaller.getJarPath("testjit.jar");
        String mainClass = "TestJIT";

        dump2_WB(null, topArchiveName,
                 "-Xlog:cds",
                 "-Xlog:cds+dynamic",
                 "-XX:-UseOnStackReplacement",
                 "-XX:+PrintCompilation",
                 "-cp", appJar, mainClass)
                .assertNormalExit(output -> {
                    output.shouldContain("Written dynamic archive 0x");
                });
    }
}
