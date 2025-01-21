/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @summary Test open archive heap regions
 * @requires vm.cds.write.archived.java.heap
 * @comment This test explicitly chooses the type of GC to be used by sub-processes. It may conflict with the GC type set
 * via the -vmoptions command line option of JTREG. vm.gc==null will help the test case to discard the explicitly passed
 * vm options.
 * @requires (vm.gc=="null")
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile ../test-classes/Hello.java
 * @run driver OpenArchiveRegion
 */

import jdk.test.lib.process.OutputAnalyzer;

public class OpenArchiveRegion {
    public static void main(String[] args) throws Exception {
        JarBuilder.getOrCreateHelloJar();
        String appJar = TestCommon.getTestJar("hello.jar");
        String appClasses[] = TestCommon.list("Hello");

        // Dump with open archive heap region, requires G1 GC
        OutputAnalyzer output = TestCommon.dump(appJar, appClasses, "-Xlog:cds=debug");
        TestCommon.checkDump(output, "hp space:");
        output.shouldNotContain("hp space:         0 [");
        output = TestCommon.exec(appJar, "Hello");
        TestCommon.checkExec(output, "Hello World");
        output = TestCommon.exec(appJar, "-XX:+UseSerialGC", "Hello");
        TestCommon.checkExec(output, "Hello World");

        // Dump with open archive heap region disabled when G1 GC is not in use
        output = TestCommon.dump(appJar, appClasses, "-XX:+UseParallelGC");
        TestCommon.checkDump(output);
        output.shouldNotContain("hp space:");
        output = TestCommon.exec(appJar, "Hello");
        TestCommon.checkExec(output, "Hello World");
    }
}
