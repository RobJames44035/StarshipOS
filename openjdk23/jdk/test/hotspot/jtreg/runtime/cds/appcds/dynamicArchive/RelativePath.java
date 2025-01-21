/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary Test relative paths specified in the -cp.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @build jdk.test.whitebox.WhiteBox
 * @compile ../test-classes/Hello.java
 * @compile ../test-classes/HelloMore.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. RelativePath
 */

import java.io.File;

public class RelativePath extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(RelativePath::testDefaultBase);
    }

    static void testDefaultBase() throws Exception {
        String topArchiveName = getNewArchiveName("top");
        doTest(topArchiveName);
    }

    private static void doTest(String topArchiveName) throws Exception {
        String appJar = JarBuilder.getOrCreateHelloJar();
        String appJar2 = JarBuilder.build("AppendClasspath_HelloMore", "HelloMore");

        int idx = appJar.lastIndexOf(File.separator);
        String jarName = appJar.substring(idx + 1);
        String jarDir = appJar.substring(0, idx);

        // Create CDS Archive
        dump(topArchiveName, "-Xlog:cds",
            "-Xlog:cds+dynamic=debug",
            "-cp", appJar + File.pathSeparator + appJar2,
            "HelloMore")
            .assertNormalExit(output-> {
                    output.shouldContain("Written dynamic archive 0x");
            });

        // relative path starting with "."
        runWithRelativePath(null, topArchiveName, jarDir,
            "-Xlog:class+load",
            "-Xlog:cds+dynamic=debug,cds=debug",
            "-cp", "." + File.separator + "hello.jar" + File.pathSeparator + appJar2,
            "HelloMore")
            .assertNormalExit(output -> {
                    output.shouldContain("Hello source: shared objects file")
                          .shouldContain("Hello World ... More")
                          .shouldHaveExitValue(0);
                });

        // relative path starting with ".."
        idx = jarDir.lastIndexOf(File.separator);
        String jarSubDir = jarDir.substring(idx + 1);
        runWithRelativePath(null, topArchiveName, jarDir,
            "-Xlog:class+load",
            "-Xlog:cds+dynamic=debug,cds=debug",
            "-cp",
            ".." + File.separator + jarSubDir + File.separator + "hello.jar" + File.pathSeparator + appJar2,
            "HelloMore")
            .assertNormalExit(output -> {
                    output.shouldContain("Hello source: shared objects file")
                          .shouldContain("Hello World ... More")
                          .shouldHaveExitValue(0);
                });

    }
}
