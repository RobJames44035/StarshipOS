/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6963811
 * @summary Tests deadlock in Introspector
 * @author Sergey Malenkov
 */

import java.beans.Introspector;
import java.beans.SimpleBeanInfo;

public class Test6963811 implements Runnable {
    private final long time;
    private final boolean sync;

    public Test6963811(long time, boolean sync) {
        this.time = time;
        this.sync = sync;
    }

    public void run() {
        try {
            Thread.sleep(this.time); // increase the chance of the deadlock
            Introspector.getBeanInfo(
                    this.sync ? Super.class : Sub.class,
                    this.sync ? null : Object.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Test6963811(0L, i > 0));
            threads[i].start();
            Thread.sleep(500L); // increase the chance of the deadlock
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static class Super {
    }

    public static class Sub extends Super {
    }

    public static class SubBeanInfo extends SimpleBeanInfo {
        public SubBeanInfo() {
            new Test6963811(1000L, true).run();
        }
    }
}
