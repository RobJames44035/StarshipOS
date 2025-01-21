/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @compile ../../../../../../jdk/java/util/stream/TestDoubleSumAverage.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. DoubleSumAverageTest
 */

import java.io.File;

public class DoubleSumAverageTest extends DynamicArchiveTestBase {

    public static void main(String[] args) throws Exception {
        runTest(DoubleSumAverageTest::testImpl);
    }

    private static final String classDir = System.getProperty("test.classes");
    private static final String mainClass = "TestDoubleSumAverage";

    static void testImpl() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = JarBuilder.build("stream", new File(classDir), null);

        dumpAndRun(topArchiveName, "-Xlog:cds,cds+dynamic=debug,class+load=trace",
                "-cp", appJar, mainClass);
    }
}
