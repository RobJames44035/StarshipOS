/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test id=default
 * @summary Verifies JVMTI can_support_virtual_threads works for agents loaded at startup and into running VM
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:VirtualThreadStartTest VirtualThreadStartTest
 * @run main/othervm/native -agentlib:VirtualThreadStartTest=can_support_virtual_threads VirtualThreadStartTest
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true VirtualThreadStartTest attach
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true VirtualThreadStartTest attach can_support_virtual_threads

/*
 * @test id=no-vmcontinuations
 * @requires vm.continuations
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:VirtualThreadStartTest -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations VirtualThreadStartTest
 * @run main/othervm/native -agentlib:VirtualThreadStartTest=can_support_virtual_threads -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations VirtualThreadStartTest
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations VirtualThreadStartTest attach
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations VirtualThreadStartTest attach can_support_virtual_threads
 */

import com.sun.tools.attach.VirtualMachine;

public class VirtualThreadStartTest {
    private static final String AGENT_LIB = "VirtualThreadStartTest";
    private static final int THREAD_CNT = 10;

    private static native boolean canSupportVirtualThreads();
    private static native int getAndResetStartedThreads();

    public static void main(String[] args) throws Exception {
        System.out.println("loading " + AGENT_LIB + " lib");

        if (args.length > 0 && args[0].equals("attach")) { // agent loaded into running VM case
            String arg = args.length == 2 ? args[1] : "";
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
            vm.loadAgentLibrary(AGENT_LIB, arg);
        }
        getAndResetStartedThreads();

        for (int i = 0; i < THREAD_CNT; i++) {
            Thread.ofVirtual().name("Tested-VT-" + i).start(() -> {}).join();
        }

        // No VirtualThreadStart events are expected if can_support_virtual_threads is disabled.
        int expStartedThreads = canSupportVirtualThreads() ? THREAD_CNT : 0;
        int startedThreads = getAndResetStartedThreads();

        System.out.println("ThreadStart event count: " + startedThreads + ", expected: " + expStartedThreads);

        if (startedThreads != expStartedThreads) {
            throw new RuntimeException("Failed: wrong ThreadStart count: " +
                                       startedThreads + " expected: " + expStartedThreads);
        }
    }
}
