/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.test.lib.thread;

import java.util.concurrent.ThreadFactory;

/*
    This factory is used to start new threads in tests.
    It supports creation of virtual threads when jtreg test.thread.factory plugin is enabled.
*/

public class TestThreadFactory {

    private static ThreadFactory threadFactory = "Virtual".equals(System.getProperty("test.thread.factory"))
            ? virtualThreadFactory() : platformThreadFactory();

    public static Thread newThread(Runnable task) {
        return threadFactory.newThread(task);
    }

    public static Thread newThread(Runnable task, String name) {
        Thread t = threadFactory.newThread(task);
        t.setName(name);
        return t;
    }

    private static ThreadFactory platformThreadFactory() {
        return Thread.ofPlatform().factory();
    }

    private static ThreadFactory virtualThreadFactory() {
        return Thread.ofVirtual().factory();
    }
}
