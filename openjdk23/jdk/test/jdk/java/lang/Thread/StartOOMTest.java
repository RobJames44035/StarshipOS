/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * This test is relatively useful for verifying 6379235, but
 * is too resource intensive, especially on 64 bit systems,
 * to be run automatically, see 6721694.
 *
 * When run it should be typically be run with the server vm
 * and a relatively small java heap, and a large stack size
 * ( to provoke the OOM quicker ).
 *    java -server -Xmx32m -Xms32m -Xss256m StartOOMTest
 */

import java.util.*;

public class StartOOMTest
{
    public static void main(String[] args) throws Throwable {
        Runnable r = new SleepRunnable();
        ThreadGroup tg = new ThreadGroup("buggy");
        List<Thread> threads = new ArrayList<Thread>();
        Thread failedThread;
        int i = 0;
        for (i = 0; ; i++) {
            Thread t = new Thread(tg, r);
            try {
                t.start();
                threads.add(t);
            } catch (Throwable x) {
                failedThread = t;
                System.out.println(x);
                System.out.println(i);
                break;
            }
        }

        int j = 0;
        for (Thread t : threads)
            t.interrupt();

        while (tg.activeCount() > i/2)
            Thread.yield();
        failedThread.start();
        failedThread.interrupt();

        for (Thread t : threads)
            t.join();
        failedThread.join();

        try {
            Thread.sleep(1000);
        } catch (Throwable ignore) {
        }

        int activeCount = tg.activeCount();
        System.out.println("activeCount = " + activeCount);

        if (activeCount > 0) {
            throw new RuntimeException("Failed: there  should be no active Threads in the group");
        }
    }

    static class SleepRunnable implements Runnable
    {
        public void run() {
            try {
                Thread.sleep(60*1000);
            } catch (Throwable t) {
            }
        }
    }
}
