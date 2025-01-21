/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test id=default
 * @bug 8325187
 * @summary Verifies JVMTI InterruptThread works for virtual threads.
 * @run main/othervm/native -agentlib:InterruptRawMonitor InterruptRawMonitor
 *
 * @test id=virtual
 * @bug 8325187
 * @summary Verifies JVMTI InterruptThread works for virtual threads.
 * @run main/othervm/native -agentlib:InterruptRawMonitor InterruptRawMonitor -v
 */

import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptRawMonitor {
    private static final String AGENT_LIB = "InterruptRawMonitor";
    static native void test();
    static native void waitForCondition(Thread t);

    public static void main(String[] args) throws Exception {
        Thread thread;
        if (args.length > 0 && "-v".equals(args[0])) {
            thread = Thread.ofVirtual().unstarted(InterruptRawMonitor::test);
        } else {
            thread = Thread.ofPlatform().unstarted(InterruptRawMonitor::test);
        }
        System.out.println(thread);
        thread.start();
        waitForCondition(thread);
        thread.interrupt();
        thread.join();
    }
}
