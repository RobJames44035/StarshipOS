/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

package vm.mlvm.share;

import vm.share.options.Option;

import java.util.concurrent.CyclicBarrier;


public abstract class MultiThreadedTest extends MlvmTest {

    @Option(name = "threadsExtra", default_value = "1",
            description = "Summand of absolute thread count that does not"
                    + " depend on CPU count")
    private int threadsExtra;

    @Option(name = "threadsPerCpu", default_value = "0",
            description = "Summand of absolute thread count that is multiplied"
                    + " by CPU count")
    private int threadsPerCpu;

    protected MultiThreadedTest() {
        // fields will be initialized later by the Option framework
    }

    protected abstract boolean runThread(int threadNum) throws Throwable;

    protected int calcThreadNum() {
        // TODO: multiply by StressThreadFactor: JDK-8142970
        return threadsPerCpu * Runtime.getRuntime().availableProcessors()
                + threadsExtra;
    }

    @Override
    public boolean run() throws Throwable {
        Thread.UncaughtExceptionHandler exHandler = (Thread t, Throwable e) -> {
            markTestFailed("Exception in thread %s" + t.getName(), e);
        };
        int threadNum = calcThreadNum();
        Env.traceNormal("Threads to start in this test: " + threadNum);
        final CyclicBarrier startBarrier = new CyclicBarrier(threadNum + 1);

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            final int ii = i;
            threads[i] = new Thread(() -> {
                boolean passed = false;
                try {
                    startBarrier.await();
                    if (runThread(ii)) {
                        passed = true;
                    } else {
                        Env.complain("Failed test in %s",
                                Thread.currentThread());
                    }
                } catch (Throwable e) {
                    Env.complain(e, "Caught exception in %s",
                            Thread.currentThread());
                }
                if (!passed) {
                    markTestFailed("Thread " + Thread.currentThread()
                            + " failed");
                }
            });
            threads[i].setUncaughtExceptionHandler(exHandler);
            threads[i].start();
        }

        startBarrier.await();
        Env.traceNormal(threadNum + " threads have started");

        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }

        Env.traceNormal("All threads have finished");
        return true;
    }

}
