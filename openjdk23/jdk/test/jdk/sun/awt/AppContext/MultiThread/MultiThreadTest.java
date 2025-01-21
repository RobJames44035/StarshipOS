/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8019623
 * @summary Tests that AppContext.getAppContext() works correctly in multi-threads scenario.
 * @author Leonid Romanov
 * @modules java.desktop/sun.awt
 */

import sun.awt.AppContext;

public class MultiThreadTest {
    private static final int NUM_THREADS = 2;

    private static AppContextGetter[] getters = new AppContextGetter[NUM_THREADS];

    public static void main(String[] args) {
        createAndStartThreads();
        compareAppContexts();
    }

    private static void createAndStartThreads() {
        ThreadGroup systemGroup = getSystemThreadGroup();
        for (int i = 0; i < NUM_THREADS; ++i) {
            ThreadGroup tg = new ThreadGroup(systemGroup, "AppContextGetter" + i);
            getters[i] = new AppContextGetter(tg);
        }

        for (int i = 0; i < NUM_THREADS; ++i) {
            getters[i].start();
        }

        for (int i = 0; i < NUM_THREADS; ++i) {
            try {
                getters[i].join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private static ThreadGroup getSystemThreadGroup() {
        ThreadGroup currentThreadGroup =
                Thread.currentThread().getThreadGroup();
        ThreadGroup parentThreadGroup = currentThreadGroup.getParent();
        while (parentThreadGroup != null) {
            currentThreadGroup = parentThreadGroup;
            parentThreadGroup = currentThreadGroup.getParent();
        }

        return currentThreadGroup;
    }

    private static void compareAppContexts() {
        AppContext ctx = getters[0].getAppContext();
        for (int i = 1; i < NUM_THREADS; ++i) {
            if (!ctx.equals(getters[i].getAppContext())) {
                throw new RuntimeException("Unexpected AppContexts difference, could be a race condition");
            }
        }
    }

    private static class AppContextGetter extends Thread {
        private AppContext appContext;

        public AppContextGetter(ThreadGroup tg) {
            super(tg, tg.getName());
        }

        AppContext getAppContext() {
            return appContext;
        }

        @Override
        public void run() {
            appContext = AppContext.getAppContext();
        }
    }
}
