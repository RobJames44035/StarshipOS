/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.process.OutputAnalyzer;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/*
 * @test
 * @summary Test of diagnostic command Thread.print with virtual threads
 * @requires vm.continuations
 * @library /test/lib
 * @run junit PrintMountedVirtualThread
 */
public class PrintMountedVirtualThread {

    public void run(CommandExecutor executor) throws InterruptedException {
        var shouldFinish = new AtomicBoolean(false);
        var started = new AtomicBoolean();
        final Runnable runnable = new DummyRunnable(shouldFinish, started);
        try {
            Thread vthread = Thread.ofVirtual().name("Dummy Vthread").start(runnable);
            while (!started.get()) {
                Thread.sleep(10);
            }
            /* Execute */
            OutputAnalyzer output = executor.execute("Thread.print");
            output.shouldMatch(".*at " + Pattern.quote(DummyRunnable.class.getName()) + "\\.run.*");
            output.shouldMatch(".*at " + Pattern.quote(DummyRunnable.class.getName()) + "\\.compute.*");
            output.shouldMatch("Mounted virtual thread " + "#" + vthread.threadId());

        } finally {
            shouldFinish.set(true);
        }
    }

    @Test
    public void jmx() throws InterruptedException {
        run(new JMXExecutor());
    }

    static class DummyRunnable implements Runnable {
        private final AtomicBoolean shouldFinish;
        private final AtomicBoolean started;

        public DummyRunnable(AtomicBoolean shouldFinish, AtomicBoolean started) {
           this.shouldFinish = shouldFinish;
           this.started = started;
        }

        public void run() {
            compute();
        }

        void compute() {
            started.set(true);
            while (!shouldFinish.get()) {
                Thread.onSpinWait();
            }
        }
    }

}
