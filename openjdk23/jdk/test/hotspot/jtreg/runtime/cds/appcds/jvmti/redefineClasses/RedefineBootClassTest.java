/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8342303
 * @summary Redefine a shared super class loaded by the boot loader. The vtable of its archived child class must be updated
 * @library /test/lib
 *          /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @requires vm.cds
 * @requires vm.jvmti
 * @run driver RedefineClassHelper
 * @build RedefineBootClassTest
 *        RedefineBootClassApp
 *        BootSuper BootChild
 * @run driver RedefineBootClassTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class RedefineBootClassTest {
    public static String bootClasses[] = {
        "BootSuper",
        "BootChild",
    };
    public static String appClasses[] = {
        "RedefineBootClassApp",
        "Util",
    };
    public static String sharedClasses[] = TestCommon.concat(bootClasses, appClasses);

    public static void main(String[] args) throws Throwable {
        runTest();
    }

    public static void runTest() throws Throwable {
        String bootJar =
            ClassFileInstaller.writeJar("RedefineClassBoot.jar", bootClasses);
        String appJar =
            ClassFileInstaller.writeJar("RedefineClassApp.jar", appClasses);

        String bootCP = "-Xbootclasspath/a:" + bootJar;
        String agentCmdArg = "-javaagent:redefineagent.jar";

        TestCommon.testDump(appJar, sharedClasses, bootCP, "-Xlog:cds,cds+class=debug");

        OutputAnalyzer out = TestCommon.execAuto("-cp", appJar,
                bootCP,
                "-XX:+UnlockDiagnosticVMOptions",
                "-Xlog:cds=info,class+load",
                agentCmdArg,
               "RedefineBootClassApp", bootJar);
        out.reportDiagnosticSummary();
        TestCommon.checkExec(out);
    }
}
