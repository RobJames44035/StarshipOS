/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary CDS dumping with JVMTI agent.
 * @requires vm.cds
 * @requires vm.jvmti
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile ../../test-classes/Hello.java
 * @run main/othervm/native DumpingWithJvmtiAgent
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.process.OutputAnalyzer;

public class DumpingWithJvmtiAgent {
    private static final String AGENT_LIB_ONLOAD = "AddToSystemCLSearchOnLoad";

    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.getOrCreateHelloJar();

        // CDS dump with a JVMTI agent with the AllowArchivingWithJavaAgent option.
        // vm should exit with an error message.
        OutputAnalyzer out = TestCommon.dump(
           appJar,
           TestCommon.list("Hello"),
           "-XX:+UnlockDiagnosticVMOptions", "-XX:+AllowArchivingWithJavaAgent",
           "-agentlib:" + AGENT_LIB_ONLOAD + "=" + appJar,
           "-Djava.library.path=" + System.getProperty("java.library.path"));
        out.shouldContain("CDS dumping does not support native JVMTI agent, name: " + AGENT_LIB_ONLOAD)
           .shouldHaveExitValue(1);

        // CDS dump with a JVMTI agent without the AllowArchivingWithJavaAgent option.
        // vm should exit with an error message.
        out = TestCommon.dump(
           appJar,
           TestCommon.list("Hello"),
           "-agentlib:" + AGENT_LIB_ONLOAD + "=" + appJar,
           "-Djava.library.path=" + System.getProperty("java.library.path"));
        out.shouldContain("CDS dumping does not support native JVMTI agent, name: " + AGENT_LIB_ONLOAD)
           .shouldHaveExitValue(1);
    }
}
