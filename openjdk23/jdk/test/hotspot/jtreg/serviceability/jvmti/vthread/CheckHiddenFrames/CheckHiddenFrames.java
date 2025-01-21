/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8341273
 * @summary Verifies JVMTI properly hides frames which are in VTMS transition
 * @run main/othervm/native -agentlib:CheckHiddenFrames CheckHiddenFrames
 */

public class CheckHiddenFrames {
    static native boolean checkHidden(Thread t);

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread = Thread.startVirtualThread(CheckHiddenFrames::test);
        System.out.println("Started virtual thread: " + thread);

        if (!checkHidden(thread)) {
            thread.interrupt();
            throw new RuntimeException("CheckHiddenFrames failed!");
        }
        thread.interrupt();
        thread.join();
    }

    static void test() {
        sleep(1000000);
    }
}
