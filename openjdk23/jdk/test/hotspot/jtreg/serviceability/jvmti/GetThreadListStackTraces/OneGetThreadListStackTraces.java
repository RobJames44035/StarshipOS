/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8242428
 * @summary Verifies JVMTI GetThreadListStackTraces API with thread_count = 1
 * @requires vm.jvmti
 * @library /test/lib
 * @run main/othervm/native -agentlib:OneGetThreadListStackTraces OneGetThreadListStackTraces
 *
 */

public class OneGetThreadListStackTraces {

    private static native void checkCallStacks(Thread thread);

    public static void main(String[] args) throws Exception {
        /* Check call stack native */
        checkCallStacks(Thread.currentThread());
    }
}
