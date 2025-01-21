/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Test jvmti class file loader hook interaction with AppCDS
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @requires vm.cds
 * @requires vm.jvmti
 * @build ClassFileLoadHook
 * @run main/othervm/native ClassFileLoadHookTest
 */


import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class ClassFileLoadHookTest {
    public static String sharedClasses[] = {
        "ClassFileLoadHook",
        "ClassFileLoadHook$TestCaseId",
        "LoadMe",
        "java/sql/SQLException"
    };

    public static void main(String[] args) throws Exception {
        String wbJar =
            ClassFileInstaller.writeJar("WhiteBox.jar", "jdk.test.whitebox.WhiteBox");
        String appJar =
            ClassFileInstaller.writeJar("ClassFileLoadHook.jar", sharedClasses);
        String useWb = "-Xbootclasspath/a:" + wbJar;

        // First, run the test class directly, w/o sharing, as a baseline reference
        CDSOptions opts = (new CDSOptions())
            .setUseVersion(false)
            .setXShareMode("off")
            .addSuffix("-XX:+UnlockDiagnosticVMOptions",
                       "-XX:+WhiteBoxAPI",
                       useWb,
                       "-agentlib:SimpleClassFileLoadHook=LoadMe,beforeHook,after_Hook",
                       "ClassFileLoadHook",
                       "" + ClassFileLoadHook.TestCaseId.SHARING_OFF_CFLH_ON);
        CDSTestUtils.run(opts)
                    .assertNormalExit();

        // Run with AppCDS, but w/o CFLH - second baseline
        TestCommon.testDump(appJar, sharedClasses, useWb);
        OutputAnalyzer out = TestCommon.exec(appJar,
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI", useWb,
                "ClassFileLoadHook",
                "" + ClassFileLoadHook.TestCaseId.SHARING_ON_CFLH_OFF);

        TestCommon.checkExec(out);


        // Now, run with AppCDS with -Xshare:auto and CFLH
        out = TestCommon.execAuto("-cp", appJar,
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI", useWb,
                "-agentlib:SimpleClassFileLoadHook=LoadMe,beforeHook,after_Hook",
                "ClassFileLoadHook",
                "" + ClassFileLoadHook.TestCaseId.SHARING_AUTO_CFLH_ON);

        opts = (new CDSOptions()).setXShareMode("auto");
        TestCommon.checkExec(out, opts);

        // Now, run with AppCDS -Xshare:on and CFLH
        out = TestCommon.exec(appJar,
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI", useWb,
                "-agentlib:SimpleClassFileLoadHook=LoadMe,beforeHook,after_Hook",
                "ClassFileLoadHook",
                "" + ClassFileLoadHook.TestCaseId.SHARING_ON_CFLH_ON);
        TestCommon.checkExec(out);

        // JEP 483: if dumped with -XX:+AOTClassLinking, cannot use archive when CFLH is enabled
        TestCommon.testDump(appJar, sharedClasses, useWb, "-XX:+AOTClassLinking");
        out = TestCommon.exec(appJar,
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI", useWb,
                "-agentlib:SimpleClassFileLoadHook=LoadMe,beforeHook,after_Hook",
                "-Xlog:cds",
                "ClassFileLoadHook",
                "" + ClassFileLoadHook.TestCaseId.SHARING_ON_CFLH_ON);
        if (out.contains("Using AOT-linked classes: false (static archive: no aot-linked classes")) {
            // JTREG is executed with VM options that do not support -XX:+AOTClassLinking, so
            // the static archive was not created with aot-linked classes.
            out.shouldHaveExitValue(0);
        } else {
            out.shouldContain("CDS archive has aot-linked classes. It cannot be used when JVMTI ClassFileLoadHook is in use.");
            out.shouldNotHaveExitValue(0);
        }
    }
}
