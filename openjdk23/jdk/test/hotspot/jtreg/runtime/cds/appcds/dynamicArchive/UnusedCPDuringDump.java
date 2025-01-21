/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary non-empty dir in -cp should be fine during dump time if only classes
 *          from the system modules are being loaded even though some are
 *          defined to the PlatformClassLoader and AppClassLoader.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build jdk.test.whitebox.WhiteBox
 * @compile ../test-classes/Hello.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Dtest.cds.copy.child.stdout=false -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. UnusedCPDuringDump
 */

import java.io.File;
import jdk.test.lib.cds.CDSTestUtils;

public class UnusedCPDuringDump extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(UnusedCPDuringDump::testDefaultBase);
    }

    static void testDefaultBase() throws Exception {
        String topArchiveName = getNewArchiveName("top");
        doTest(topArchiveName);
    }

    private static void doTest(String topArchiveName) throws Exception {
        File dir = CDSTestUtils.getOutputDirAsFile();
        File emptydir = new File(dir, "emptydir");
        emptydir.mkdir();
        String appJar = JarBuilder.getOrCreateHelloJar();
        String bootClassPath = "-Xbootclasspath/a:" + appJar;

        // Dumping with a non-empty directory in the -cp.
        // It should be fine because the app class won't be loaded from the
        // -cp, it is being loaded from the bootclasspath.
        dump(topArchiveName,
             "-Xlog:cds",
             "-Xlog:cds+dynamic=debug",
             bootClassPath,
             "-cp", dir.getPath(),
             "Hello")
            .assertNormalExit(output -> {
                 output.shouldContain("Written dynamic archive 0x");
                });

        // Running with -cp different from dumping. It should be fine because
        // the runtime classpath won't be checked against unused classpath
        // during dump time.
        run(topArchiveName,
            "-Xlog:class+load",
            "-Xlog:cds+dynamic=debug,cds=debug",
             bootClassPath,
            "-cp", appJar, "Hello")
            .assertNormalExit(output -> {
                    output.shouldContain("Hello World")
                          .shouldHaveExitValue(0);
                });
  }
}
