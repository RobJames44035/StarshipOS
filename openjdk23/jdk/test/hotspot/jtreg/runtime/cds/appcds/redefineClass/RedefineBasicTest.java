/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Run /serviceability/jvmti/RedefineClasses/RedefineRunningMethods in AppCDS mode to
 *          make sure class redefinition works with CDS.
 * @requires vm.cds
 * @requires vm.jvmti
 * @library /test/lib /test/hotspot/jtreg/serviceability/jvmti/RedefineClasses /test/hotspot/jtreg/runtime/cds/appcds
 * @run driver RedefineClassHelper
 * @build jdk.test.whitebox.WhiteBox RedefineBasic
 * @run driver RedefineBasicTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class RedefineBasicTest {
    public static String sharedClasses[] = {
        "RedefineBasic",
        "RedefineBasic_B",
        "RedefineBasic$SubclassOfB",
        "RedefineBasic$Subclass2OfB",
        "RedefineClassHelper",
        "jdk/test/lib/compiler/InMemoryJavaCompiler",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper$1",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$SourceFile",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$ClassFile"
    };

    public static void main(String[] args) throws Exception {
        String wbJar =
            ClassFileInstaller.writeJar("WhiteBox.jar", "jdk.test.whitebox.WhiteBox");
        String appJar =
            ClassFileInstaller.writeJar("RedefineBasic.jar", sharedClasses);
        String useWb = "-Xbootclasspath/a:" + wbJar;

        OutputAnalyzer output;
        TestCommon.testDump(appJar, sharedClasses, useWb);

        // redefineagent.jar is created by executing "@run driver RedefineClassHelper"
        // which should be called before executing RedefineBasicTest
        output = TestCommon.exec(appJar, useWb,
                                 "-XX:+UnlockDiagnosticVMOptions",
                                 "-XX:+WhiteBoxAPI",
                                 "-javaagent:redefineagent.jar",
                                 "RedefineBasic");
        TestCommon.checkExec(output);
    }
}
