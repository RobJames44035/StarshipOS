/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Archive an base app class in a base archive and its lambda proxy
 *          class in a dynamic archive. During runtime, the base app class
 *          should be loaded from the base archive and the lambda proxy class
 *          should be loaded from the dynamic archive.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build SimpleApp jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar simpleApp.jar SimpleApp MyClass MyInterface
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. LambdaForClassInBaseArchive
 */

import java.io.File;
import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class LambdaForClassInBaseArchive extends DynamicArchiveTestBase {
    static final String classList = CDSTestUtils.getOutputFileName("classlist");
    static final String appClass = "SimpleApp";

    public static void main(String[] args) throws Exception {
        runTest(LambdaForClassInBaseArchive::testCustomBase);
    }

    static void testCustomBase() throws Exception {
        String topArchiveName = getNewArchiveName("top");
        String baseArchiveName = getNewArchiveName("base");
        doTestCustomBase(baseArchiveName, topArchiveName);
    }

    private static void doTestCustomBase(String baseArchiveName, String topArchiveName) throws Exception {
        String appJar = ClassFileInstaller.getJarPath("simpleApp.jar");
        // dump class list by running the SimpleApp
        CDSTestUtils.dumpClassList(classList, "-cp", appJar, appClass);

        // create a custom base archive based on the class list
        TestCommon.dumpBaseArchive(baseArchiveName,
                        "-XX:SharedClassListFile=" + classList,
                        "-cp", appJar, appClass);

        // create a dynamic archive with the custom base archive.
        // The SimpleApp class is in the base archive. Its interface
        // will be accessed using a lambda expression and the lambda
        // proxy class will be archived in the dynamic archive.
        dump2(baseArchiveName, topArchiveName,
              "-Xlog:cds,cds+dynamic",
              "-cp", appJar,
              appClass, "lambda")
            .assertNormalExit(out -> {
                    out.shouldHaveExitValue(0)
                       .shouldMatch("Archiving hidden SimpleApp[$][$]Lambda");
                });

        // Run with both base and dynamic archives. The SimpleApp class
        // should be loaded from the base archive. Its lambda proxy class
        // should be loaded from the dynamic archive.
        run2(baseArchiveName, topArchiveName,
              "-Xlog:cds,cds+dynamic",
              "-Xlog:class+load=trace",
              "-cp", appJar,
              appClass, "lambda")
            .assertNormalExit(out -> {
                    out.shouldHaveExitValue(0)
                       .shouldContain("SimpleApp source: shared objects file")
                       .shouldMatch(".class.load. SimpleApp[$][$]Lambda.*/0x.*source:.*shared.*objects.*file.*(top)");
                });
    }
}
