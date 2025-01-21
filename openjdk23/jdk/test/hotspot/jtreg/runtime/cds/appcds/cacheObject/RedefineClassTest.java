/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @summary Redefine shared class. GC should not cause crash with cached resolved_references.
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes /test/hotspot/jtreg/runtime/cds/appcds/jvmti
 * @requires vm.cds.write.archived.java.heap
 * @requires vm.jvmti
 * @build jdk.test.whitebox.WhiteBox
 *        RedefineClassApp
 *        InstrumentationClassFileTransformer
 *        InstrumentationRegisterClassFileTransformer
 * @run driver RedefineClassTest
 */

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.util.List;
import jdk.test.lib.Asserts;
import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.helpers.ClassFileInstaller;

public class RedefineClassTest {
    public static String bootClasses[] = {
        "RedefineClassApp$Intf",
        "RedefineClassApp$Bar",
        "jdk.test.whitebox.WhiteBox",
    };
    public static String appClasses[] = {
        "RedefineClassApp",
        "RedefineClassApp$Foo",
    };
    public static String sharedClasses[] = TestCommon.concat(bootClasses, appClasses);

    public static String agentClasses[] = {
        "InstrumentationClassFileTransformer",
        "InstrumentationRegisterClassFileTransformer",
        "Util",
    };

    public static void main(String[] args) throws Throwable {
        runTest();
    }

    public static void runTest() throws Throwable {
        String bootJar =
            ClassFileInstaller.writeJar("RedefineClassBoot.jar", bootClasses);
        String appJar =
            ClassFileInstaller.writeJar("RedefineClassApp.jar", appClasses);
        String agentJar =
            ClassFileInstaller.writeJar("InstrumentationAgent.jar",
                                        ClassFileInstaller.Manifest.fromSourceFile("InstrumentationAgent.mf"),
                                        agentClasses);

        String bootCP = "-Xbootclasspath/a:" + bootJar;

        String agentCmdArg;
        agentCmdArg = "-javaagent:" + agentJar;

        TestCommon.testDump(appJar, sharedClasses, bootCP, "-Xlog:gc+region=trace");

        OutputAnalyzer out = TestCommon.execAuto("-cp", appJar,
                bootCP,
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI",
                "-Xlog:cds=info",
                agentCmdArg,
               "RedefineClassApp", bootJar, appJar);
        out.reportDiagnosticSummary();

        CDSOptions opts = (new CDSOptions()).setXShareMode("auto");
        TestCommon.checkExec(out, opts);
    }
}

