/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8231666
 * @summary Test checks that new threads could be successfully started when the thread
 * table introduced in 8185005 is growing. The test enables the thread table by calling
 * ThreadMXBean.getThreadInfo() and then creates a number of threads to force the thread
 * table to grow.
 *
 * @run main ThreadStartTest
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadStartTest {
    public static void main(String[] args) {

        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        // Enable thread table
        mbean.getThreadInfo(Thread.currentThread().getId());

        // Create a large number of threads to make the thread table grow
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            });
            t.start();
        }
    }
}
