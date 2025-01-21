/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @key jfr
 * @summary Tests emitting event before main using a Java agent
 * @requires vm.hasJFR
 *
 * @library /test/lib
 * @modules java.instrument
 *
 * @build jdk.jfr.javaagent.EventEmitterAgent
 *
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             jdk.jfr.javaagent.EventEmitterAgent EventEmitterAgent.jar
 *
 * @run main/othervm -javaagent:EventEmitterAgent.jar jdk.jfr.javaagent.TestPremainAgent
 */

package jdk.jfr.javaagent;


public class TestPremainAgent {
    public static void main(String... arg) throws Exception {
        EventEmitterAgent.validateRecording();
    }
}
