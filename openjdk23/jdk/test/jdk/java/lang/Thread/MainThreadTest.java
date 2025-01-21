/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4533087
 * @summary Test to see if the main thread is in its thread group
 */

public class MainThreadTest {
    public static void main(String args[]) {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        int n = tg.activeCount();
        Thread[] ts = new Thread[n];
        int m = tg.enumerate(ts);
        for (int i = 0; i < ts.length; i++) {
            if (Thread.currentThread() == ts[i]) {
                return;
            }
        }
        throw new RuntimeException(
            "Current thread is not in its own thread group!");
    }
}
