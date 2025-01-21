/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test id=platform
 * @summary Verifies JVMTI ClearAllFramePops clears all FramePop requests
 * @library /test/lib
 * @run main/othervm/native -agentlib:ClearAllFramePops ClearAllFramePops platform
 */
/*
 * @test id=virtual
 * @summary Verifies JVMTI ClearAllFramePops clears all FramePop requests
 * @library /test/lib
 * @run main/othervm/native -agentlib:ClearAllFramePops ClearAllFramePops virtual
 */

public class ClearAllFramePops {

    final static int MAX_THREADS_LIMIT = 10;
    final static int NESTING_DEPTH = 5;
    final static String TEST_THREAD_NAME_BASE = "Test Thread #";

    native static void clearAllFramePops();
    native static void getReady();
    native static void check();

    public static void main(String args[]) {
        boolean isVirtual = args.length > 0 && args[0].equals("virtual");
        final int THREADS_LIMIT = Math.min(Runtime.getRuntime().availableProcessors() + 1, MAX_THREADS_LIMIT);
        Thread[] t = new Thread[THREADS_LIMIT];
        getReady();
        Thread.Builder builder = (isVirtual ? Thread.ofVirtual() : Thread.ofPlatform())
                .name(TEST_THREAD_NAME_BASE, 0);
        for (int i = 0; i < THREADS_LIMIT; i++) {
            t[i] = builder.start(new TestTask());
        }
        for (int i = 0; i < THREADS_LIMIT; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                throw new Error("Unexpected: " + e);
            }
        }
        check();
    }

    static class TestTask implements Runnable {
        int nestingCount = 0;

        public void run() {
            if (nestingCount < NESTING_DEPTH) {
                nestingCount++;
                run();
            } else {
                clearAllFramePops();
            }
        }
    }
}
