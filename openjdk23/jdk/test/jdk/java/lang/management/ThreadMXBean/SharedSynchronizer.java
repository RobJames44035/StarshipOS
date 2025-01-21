/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6337571
 * @summary Test if findDeadlockedThreads works for an ownable synchronizer
 *          in shared mode which has no owner when a thread is parked.
 * @author  Mandy Chung
 *
 * @run main/othervm SharedSynchronizer
 */


import java.util.concurrent.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class SharedSynchronizer {
    public static void main(String[] args) throws Exception {
        MyThread t = new MyThread();
        t.setDaemon(true);
        t.start();

        ThreadMXBean tmbean = ManagementFactory.getThreadMXBean();
        if (!tmbean.isSynchronizerUsageSupported()) {
            System.out.println("Monitoring of synchronizer usage not supported")
;
            return;
        }

        long[] result = tmbean.findDeadlockedThreads();
        if (result != null) {
             throw new RuntimeException("TEST FAILED: result should be null");
        }
    }

    static class MyThread extends Thread {
        public void run() {
            FutureTask f = new FutureTask(
                new Callable() {
                    public Object call() {
                        throw new RuntimeException("should never reach here");
                    }
                }
            );

            // A FutureTask uses the AbstractOwnableSynchronizer in a shared
            // mode (not exclusive mode). When the thread calls f.get(),
            // it will put to park on the ownable synchronizer that
            // is not owned by any thread.
            try {
                f.get();
            } catch (Exception e) {
                RuntimeException re = new RuntimeException(e.getMessage());
                re.initCause(e);
                throw re;
            }
        }
    }
}
