/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import nsk.share.runner.*;
import nsk.share.test.ExecutionController;
import nsk.share.Consts;

/**
 * Test that executes a number of tasks.
 *
 * How these tasks are used is determined by MultiRunner.
 * Usually they are executed in separate threads in cycle
 * for some time or for some iterations.
 *
 * @see nsk.share.runner.MultiRunner
 * @see nsk.share.runner.ThreadsRunner
 */
public abstract class ThreadedGCTest extends GCTestBase implements MultiRunnerAware {
        private MultiRunner runner;

        /**
         * Create a task with index i.
         *
         * Subclasses should to override this method
         * to created neccessary tasks.
         *
         * @param i index of task
         * @return task to run or null
         */
        protected abstract Runnable createRunnable(int i);

        protected ExecutionController getExecutionController() {
                return runner.getExecutionController();
        }

        protected final boolean runThreads() {
                for (int i = 0; i < runParams.getNumberOfThreads(); ++i) {
                        Runnable runnable = createRunnable(i);
                        if (runnable != null)
                                runner.add(runnable);
                }
                runner.run();
                return runner.isSuccessful();
        }

        public void run() {
                if (!runThreads())
                        setFailed(true);
        }

        public final void setRunner(MultiRunner runner) {
                this.runner = runner;
        }
}
