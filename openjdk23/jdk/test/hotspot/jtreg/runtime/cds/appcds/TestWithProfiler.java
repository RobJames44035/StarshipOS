/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8145221
 * @summary After creating an AppCDS archive, run the test with the JFR profiler
 *          enabled, and keep calling a method in the archive in a tight loop.
 *          This is to test the safe handling of trampoline functions by the
 *          profiler.
 * @requires vm.hasJFR & vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile test-classes/MyThread.java
 * @compile test-classes/TestWithProfilerHelper.java
 * @run driver TestWithProfiler
 */

import jdk.test.lib.BuildHelper;
import jdk.test.lib.process.OutputAnalyzer;

public class TestWithProfiler {
    public static void main(String[] args) throws Exception {
        JarBuilder.build("myThread", "MyThread", "TestWithProfilerHelper");
        String appJar = TestCommon.getTestJar("myThread.jar");
        OutputAnalyzer output = TestCommon.dump(appJar,
            TestCommon.list("MyThread", "TestWithProfilerHelper"));
        TestCommon.checkDump(output);
        output = TestCommon.exec(appJar,
            "-XX:+UnlockDiagnosticVMOptions",
            "-Xint",
            "-XX:StartFlightRecording:duration=15s,filename=myrecording.jfr,settings=profile,dumponexit=true",
            "TestWithProfilerHelper");
        TestCommon.checkExec(output);
    }
}
