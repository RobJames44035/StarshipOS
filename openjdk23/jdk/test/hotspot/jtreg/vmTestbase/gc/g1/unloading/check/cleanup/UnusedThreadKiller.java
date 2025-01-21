/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check.cleanup;

import java.lang.reflect.Method;

/**
 * This utility class helps to finish threads that are not required anymore.
 */
public class UnusedThreadKiller implements CleanupAction {

    private long threadId;

    public UnusedThreadKiller(long threadId) {
        this.threadId = threadId;
    }

    @Override
    public void cleanup() throws Exception {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getId() == threadId) {
                for (Method m : thread.getClass().getMethods()) {
                    if ("finishThread".equals(m.getName())) {
                        m.invoke(thread);
                    }
                }
            }
        }
    }
}
