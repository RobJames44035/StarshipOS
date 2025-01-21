/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * Utility class for ThreadMXBean tests
 */
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Utils {
    private static final ThreadMXBean tm = ManagementFactory.getThreadMXBean();
    private static final int MAX_RETRY = 200;

    public static boolean waitForBlockWaitingState(Thread t) {
        // wait for the thread to transition to the expected state
        int retryCount=0;
        while (t.getState() == Thread.State.RUNNABLE && retryCount < MAX_RETRY) {
            goSleep(100);
            retryCount++;
        }
        return (t.getState() != Thread.State.RUNNABLE);
    }

    public static boolean waitForThreadState(Thread t, Thread.State expected) {
        // wait for the thread to transition to the expected state
        int retryCount=0;
        while (t.getState() != expected && retryCount < MAX_RETRY) {
            goSleep(100);
            retryCount++;
        }
        return (t.getState() == expected);
    }

    public static void checkThreadState(Thread t, Thread.State expected) {
        waitForThreadState(t, expected);

        Thread.State state = tm.getThreadInfo(t.getId()).getThreadState();
        if (state == null) {
            throw new RuntimeException(t.getName() + " expected to have " +
                expected + " but got null.");
        }
        if (state != expected) {
            t.dumpStack();
            throw new RuntimeException(t.getName() +
                 " expected to have " + expected + " but got " + state);
        }
    }

    public static void goSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("TEST FAILED: Unexpected exception.");
            throw new RuntimeException(e);
        }
    }

}
