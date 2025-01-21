/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A utility class that wraps the ThreadController, which is used by
 * IncrementalSAXSource for the incremental building of DTM.
 */
public class ThreadControllerWrapper {

    /**
     * The ThreadController pool
     */
    private static ThreadController m_tpool = new ThreadController();

    public static Thread runThread(Runnable runnable, int priority) {
        return m_tpool.run(runnable, priority);
    }

    public static void waitThread(Thread worker, Runnable task)
            throws InterruptedException {
        m_tpool.waitThread(worker, task);
    }

    /**
     * Thread controller utility class for incremental SAX source. Must be
     * overridden with a derived class to support thread pooling.
     *
     * All thread-related stuff is in this class.
     */
    public static class ThreadController {

        /**
         * Will get a thread from the pool, execute the task and return the
         * thread to the pool.
         *
         * The return value is used only to wait for completion
         *
         *
         * @param task the Runnable
         *
         * @param priority if >0 the task will run with the given priority (
         * doesn't seem to be used in xalan, since it's always the default )
         * @return The thread that is running the task, can be used to wait for
         * completion
         */
        public Thread run(Runnable task, int priority) {

            Thread t = new SafeThread(task);
            t.start();

            //if( priority > 0 )
            //    t.setPriority( priority );
            return t;
        }

        /**
         * Wait until the task is completed on the worker thread.
         *
         * @param worker worker thread
         * @param task the Runnable
         *
         * @throws InterruptedException
         */
        public void waitThread(Thread worker, Runnable task)
                throws InterruptedException {

            // This should wait until the transformThread is considered not alive.
            worker.join();
        }
    }
}
