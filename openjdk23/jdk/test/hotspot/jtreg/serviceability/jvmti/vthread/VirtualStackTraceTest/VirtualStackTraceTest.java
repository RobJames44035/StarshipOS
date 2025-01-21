/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test id=default
 * @summary Verifies JVMTI GetStackTrace does not truncate virtual thread stack trace with agent attach
 * @requires vm.jvmti
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true VirtualStackTraceTest
 */

/*
 * @test id=no-vmcontinuations
 * @requires vm.continuations
 * @requires vm.jvmti
 * @run main/othervm/native -Djdk.attach.allowAttachSelf=true -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations VirtualStackTraceTest
 */

import com.sun.tools.attach.VirtualMachine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VirtualStackTraceTest {
    private static final String AGENT_LIB = "VirtualStackTraceTest";

    public static native String[] getStackTrace();

    public static void main(String[] args) throws Exception {
        VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
        vm.loadAgentLibrary(AGENT_LIB);
        VirtualStackTraceTest t = new VirtualStackTraceTest();
        t.runTest();
    }

    void runTest() throws Exception {
        Thread thr = Thread.ofVirtual().name("VT").start(VirtualStackTraceTest::test);
        thr.join();
    }

    private static void test() {
        work();
    }

    private static void work() {
        inner();
    }

    private static void inner() {
        checkCurrentThread();
    }

    private static void checkCurrentThread() {
        System.out.println("Stack trace for " + Thread.currentThread() + ": ");
        var javaStackTrace = Arrays.stream(Thread.currentThread().getStackTrace()).map(StackTraceElement::getMethodName).toList();
        var jvmtiStackTrace = List.of(getStackTrace());

        System.out.println("JVMTI: " + jvmtiStackTrace);
        System.out.println("Java : " + javaStackTrace);

        if (!Objects.equals(jvmtiStackTrace, javaStackTrace)) {
            throw new RuntimeException("VirtualStackTraceTest failed: stack traces do not match");
        }
    }
}
