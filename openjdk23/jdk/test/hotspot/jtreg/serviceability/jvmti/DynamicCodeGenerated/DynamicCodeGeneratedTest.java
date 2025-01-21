/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8212155
 * @summary Test concurrent enabling and posting of DynamicCodeGenerated events.
 * @requires vm.jvmti
 * @library /test/lib
 * @run main/othervm/native -agentlib:DynamicCodeGenerated DynamicCodeGeneratedTest
 */

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class DynamicCodeGeneratedTest {
    static {
        System.loadLibrary("DynamicCodeGenerated");
    }
    public static native void changeEventNotificationMode();

    public static void main(String[] args) throws Exception {
        // Try to enable DynamicCodeGenerated event while it is posted
        // using JvmtiDynamicCodeEventCollector from VtableStubs::find_stub
        Thread threadChangeENM = new Thread(() -> {
            changeEventNotificationMode();
        });
        threadChangeENM.setDaemon(true);
        threadChangeENM.start();

        Runnable task = () -> {
            String result = "string" + System.currentTimeMillis();
            // Park to provoke re-mounting of virtual thread.
            LockSupport.parkNanos(1);
            Reference.reachabilityFence(result);
        };

        for (int i = 0; i < 10; i++) {
            List<Thread> threads = new ArrayList();
            for (int j = 0; j < 200; j++) {
                threads.add(Thread.ofVirtual().unstarted(task));
                threads.add(Thread.ofPlatform().unstarted(task));
            }

            for (Thread t: threads) {
                t.start();
            }
            for (Thread t: threads) {
                t.join();
            }
        }
    }
}
