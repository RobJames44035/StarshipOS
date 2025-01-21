/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test MismatchedPathTriggerMemoryRelease
 * @summary Mismatched path at runtime will cause reserved memory released
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run main MismatchedPathTriggerMemoryRelease
 */

import jdk.test.lib.process.OutputAnalyzer;

public class MismatchedPathTriggerMemoryRelease {
    private static String ERR_MSGS[] = {
        "shared class paths mismatch (hint: enable -Xlog:class+path=info to diagnose the failure)",
        "Unable to map shared spaces"};
    private static String RELEASE_SPACE_MATCH = "Released shared space .* 0(x|X)[0-9a-fA-F]+$";
    private static String OS_RELEASE_MSG = "os::release_memory failed";

    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.getOrCreateHelloJar();

        OutputAnalyzer dumpOutput = TestCommon.dump(
            appJar, new String[] {"Hello"}, "-XX:SharedBaseAddress=0");
        TestCommon.checkDump(dumpOutput, "Loading classes to share");

        // Normal exit
        OutputAnalyzer execOutput = TestCommon.exec(appJar, "Hello");
        TestCommon.checkExec(execOutput, "Hello World");

        // mismatched jar
        execOutput = TestCommon.exec("non-exist.jar",
                                     "-Xshare:auto",
                                     "-Xlog:os,cds=debug",
                                     "-XX:NativeMemoryTracking=detail",
                                     "-XX:SharedBaseAddress=0",
                                     "Hello");
        execOutput.shouldHaveExitValue(1);
        for (String err : ERR_MSGS) {
            execOutput.shouldContain(err);
        }
        execOutput.shouldMatch(RELEASE_SPACE_MATCH);
        execOutput.shouldNotContain(OS_RELEASE_MSG); // os::release only log release failed message
    }
}
