/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
/*
 * @test
 * @bug 8261090
 * @summary Dump old class with java agent.
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @requires vm.cds
 * @requires vm.jvmti
 * @compile ../../test-classes/OldSuper.jasm
 * @compile SimpleAgent.java
 * @run main/othervm OldClassWithJavaAgent
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class OldClassWithJavaAgent {
    public static String appClasses[] = {"OldSuper"};
    public static String agentClasses[] = {"SimpleAgent"};
    public static String diagnosticOption = "-XX:+AllowArchivingWithJavaAgent";
    public static void main(String[] args) throws Throwable {
        String agentJar =
            ClassFileInstaller.writeJar("SimpleAgent.jar",
                                        ClassFileInstaller.Manifest.fromSourceFile("SimpleAgent.mf"),
                                        agentClasses);

        String appJar =
            ClassFileInstaller.writeJar("OldClassWithJavaAgent.jar", appClasses);
        OutputAnalyzer output = TestCommon.testDump(appJar, TestCommon.list("OldSuper"),
            "-Xlog:cds=debug,class+load",
            "-XX:+UnlockDiagnosticVMOptions", diagnosticOption,
            "-javaagent:" + agentJar + "=OldSuper");

        // The java agent will load and link the class. We will skip old classes
        // which have been linked during CDS dump.
        output.shouldContain("Skipping OldSuper: Old class has been linked");
    }
}
