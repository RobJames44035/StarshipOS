/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @key jfr
 * @summary Tests emitting events in a dynamically loaded Java agent
 * @requires vm.hasJFR
 *
 * @library /test/lib /test/jdk
 * @modules java.instrument
 *
 * @build jdk.jfr.javaagent.EventEmitterAgent
 *
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             jdk.jfr.javaagent.EventEmitterAgent EventEmitterAgent.jar
 *
 * @run main/othervm -Djdk.attach.allowAttachSelf=true jdk.jfr.javaagent.TestLoadedAgent
 */

package jdk.jfr.javaagent;

import com.sun.tools.attach.VirtualMachine;


public class TestLoadedAgent {
    public static void main(String... arg) throws Exception {
        long pid = ProcessHandle.current().pid();
        VirtualMachine vm = VirtualMachine.attach(Long.toString(pid));
        vm.loadAgent("EventEmitterAgent.jar");
        vm.detach();
        EventEmitterAgent.validateRecording();
    }
}
