/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Classes used by CDS at runtime should be in the archived
 * @bug 8324259
 * @requires vm.cds
 * @requires vm.compMode != "Xcomp"
 * @comment Running this test with -Xcomp may load other classes which
 *          are not used in other modes
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run driver DumpRuntimeClassesTest
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;

public class DumpRuntimeClassesTest {
    public static void main(String[] args) throws Exception {
        // build The app
        String appClass = "Hello";
        String classList = "hello.classlist";
        String archiveName = "hello.jsa";
        JarBuilder.build("hello", appClass);
        String appJar = TestCommon.getTestJar("hello.jar");

        // Dump class list
        CDSTestUtils.dumpClassList(classList, "-cp", appJar, appClass);

        // Dump archive
        CDSOptions opts = (new CDSOptions())
            .addPrefix("-cp", appJar, "-XX:SharedClassListFile=" + classList)
            .setArchiveName(archiveName);
        CDSTestUtils.createArchive(opts);

        // Run with archive and ensure all the classes used were in the archive
        CDSOptions runOpts = (new CDSOptions())
            .addPrefix("-cp", appJar, "-Xlog:class+load,cds=debug")
            .setArchiveName(archiveName)
            .setUseVersion(false)
            .addSuffix(appClass);
        CDSTestUtils.runWithArchive(runOpts)
            .shouldNotContain("source: jrt:/java.base");
    }
}
