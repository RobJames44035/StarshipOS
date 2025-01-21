/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package javacserver.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javacserver.util.Log;

/**
 * Use a fixed thread pool to limit the amount of concurrent javac compilation
 * that can happen.
 */
public class CompilerThreadPool {
    private static final int POOLSIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService pool;

    public CompilerThreadPool() {
        this.pool = Executors.newFixedThreadPool(POOLSIZE);
    }

    public void execute(Runnable runnable) {
        this.pool.execute(runnable);
    }

    public void shutdown() {
        Log.debug("Shutting down javacserver thread pool");
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    Log.error("Thread pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public int poolSize() {
        return POOLSIZE;
    }
}
