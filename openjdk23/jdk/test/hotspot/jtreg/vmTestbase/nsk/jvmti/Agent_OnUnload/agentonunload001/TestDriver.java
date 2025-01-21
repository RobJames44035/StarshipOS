/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/Agent_OnUnload/agentonunload001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This JVMTI test exercises JVMTI thread function Agent_OnUnload()().
 *     This test checks that Agent_OnUnload() is invoked on shutdown,
 *     after class execution. This test does not create JVMTI environment.
 *     The test uses native method checkLoadStatus() to check value of
 *     internal native variable 'status' set by JVM_OnUnload(). Also the test
 *     uses sh script to check if JVM_OnUnload() was invoked and printed
 *     key message to stdout stream.
 *     If JVM_OnUnload() was invoked before class has been executed, then
 *     checkLoadStatus() returns FAILED and the test fails with exit status 97.
 *     If JVM_OnUnload was not executed and no key message was printed to
 *     stdout stream, then test fails with exit status 97.
 *     Otherwise, the test passes with exit status 95.
 * COMMENTS
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm/native TestDriver
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestDriver {
    public static void main(String[] args) throws Exception {
        OutputAnalyzer oa = ProcessTools.executeTestJava(
                "-agentlib:agentonunload001=-waittime=5",
                nsk.jvmti.Agent_OnUnload.agentonunload001.class.getName());
        oa.shouldHaveExitValue(95);
        oa.stdoutShouldContain("KEY PHRASE: Agent_OnUnload() was invoked");
    }
}

