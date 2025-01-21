/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies JVMTI GetLocalXXX/SetLocalXXX return errors for unsuspended vthreads
 * @library /test/lib
 * @run main/othervm/native -agentlib:GetSetLocalUnsuspended GetSetLocalUnsuspended
 */


public class GetSetLocalUnsuspended {
    private static final String agentLib = "GetSetLocalUnsuspended";

    private static native void testUnsuspendedThread(Thread thread);

    private static volatile boolean doStop;

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interruption in Thread.sleep: \n\t" + e);
        }
    }

    static final Runnable SLEEPING_THREAD = () -> {
        while (!doStop) {
            sleep(1);
        }
    };

    private static void testPlatformThread() throws Exception {
        doStop = false;
        Thread thread = Thread.ofPlatform().name("SleepingPlatformThread").start(SLEEPING_THREAD);
        testUnsuspendedThread(thread);
        doStop = true;
        thread.join();
    }

    private static void testVirtualThread() throws Exception {
        doStop = false;
        Thread thread = Thread.ofVirtual().name("SleepingVirtualThread").start(SLEEPING_THREAD);
        testUnsuspendedThread(thread);
        doStop = true;
        thread.join();
    }

    private void runTest() throws Exception {
        testPlatformThread();
        testVirtualThread();
    }

    public static void main(String[] args) throws Exception {
        try {
            System.loadLibrary(agentLib);
        } catch (UnsatisfiedLinkError ex) {
            System.err.println("Failed to load " + agentLib + " lib");
            System.err.println("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }

        GetSetLocalUnsuspended obj = new GetSetLocalUnsuspended();
        obj.runTest();

    }
}
