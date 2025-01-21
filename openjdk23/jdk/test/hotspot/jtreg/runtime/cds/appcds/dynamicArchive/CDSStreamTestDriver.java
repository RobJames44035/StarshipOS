/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Run the CustomFJPoolTest in dynamic CDSarchive mode.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @compile ../../../../../../jdk/java/util/stream/CustomFJPoolTest.java
 *          test-classes/TestStreamApp.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run testng/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. CDSStreamTestDriver
 */

import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Path;
import jtreg.SkippedException;
import jdk.test.whitebox.gc.GC;

@Test
public class CDSStreamTestDriver extends DynamicArchiveTestBase {
    @Test
    public void testMain() throws Exception {
        runTest(CDSStreamTestDriver::doTest);
    }

    private static final String classDir = System.getProperty("test.classes");
    private static final String mainClass = "TestStreamApp";
    private static final String ps = System.getProperty("path.separator");
    private static final String skippedException = "jtreg.SkippedException: Unable to map shared archive: test did not complete";

    static void doTest() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = JarBuilder.build("streamapp", new File(classDir), null);

        String testngJar = Path.of(Test.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();

        String[] testClassNames = { "CustomFJPoolTest" };

        for (String className : testClassNames) {
            try {
            dumpAndRun(topArchiveName, "-Xlog:cds,cds+dynamic=debug,class+load=trace",
                "-cp", appJar + ps + testngJar,
                mainClass, className);
           } catch (SkippedException s) {
               if (GC.Z.isSelected() && s.toString().equals(skippedException)) {
                   System.out.println("Got " + s.toString() + " as expected.");
                   System.out.println("Because the test was run with ZGC with UseCompressedOops and UseCompressedClassPointers disabled,");
                   System.out.println("but the base archive was created with the options enabled");
              } else {
                   throw new RuntimeException("Archive mapping should always succeed after JDK-8231610 (did the machine run out of memory?)");
              }
           }
        }
    }
}
