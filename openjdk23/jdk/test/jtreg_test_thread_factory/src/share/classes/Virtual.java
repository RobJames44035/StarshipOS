/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.util.concurrent.ThreadFactory;

public class Virtual implements ThreadFactory {

    static {
        // This property is used by ProcessTools and some tests
        try {
            System.setProperty("test.thread.factory", "Virtual");
        } catch (Throwable t) {
            // might be thrown by security manager
        }
    }

    static final ThreadFactory VIRTUAL_TF = Thread.ofVirtual().factory();

    @Override
    public Thread newThread(Runnable task) {
        return VIRTUAL_TF.newThread(task);
    }
}
