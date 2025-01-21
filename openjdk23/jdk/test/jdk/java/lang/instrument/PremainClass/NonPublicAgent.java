/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8165276
 * @summary Test that public premain method from non-public agent is NOT rejected to load
 * @library /test/lib
 * @modules java.instrument
 * @build jdk.java.lang.instrument.PremainClass.NonPublicAgent
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             NonPublicAgent NonPublicAgent.jar
 * @run main/othervm -javaagent:NonPublicAgent.jar DummyMain
 */

import java.lang.instrument.Instrumentation;

// This class is intentionally non-public to ensure its premain method is NOT rejected.
class NonPublicAgent {

    // This premain method has to be resolved even if its class is not public
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain: NonPublicAgent was loaded");
    }
}
