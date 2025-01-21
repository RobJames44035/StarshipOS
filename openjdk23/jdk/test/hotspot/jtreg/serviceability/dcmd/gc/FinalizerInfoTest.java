/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.PidJcmdExecutor;

/*
 * @test
 * @summary
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.xml
 *          java.management
 * @run testng FinalizerInfoTest
 */
public class FinalizerInfoTest {
    static ReentrantLock lock = new ReentrantLock();
    static volatile int wasInitialized = 0;
    static volatile int wasTrapped = 0;
    static final String cmd = "GC.finalizer_info";
    static final int objectsCount = 1000;

    class MyObject {
        public MyObject() {
            // Make sure object allocation/deallocation is not optimized out
            wasInitialized += 1;
        }
        @SuppressWarnings("removal")
        protected void finalize() {
            // Trap the object in a finalization queue
            wasTrapped += 1;
            lock.lock();
        }
    }

    public void run(CommandExecutor executor) {
        try {
            lock.lock();
            for(int i = 0; i < objectsCount; ++i) {
                new MyObject();
            }
            System.out.println("Objects initialized: " + objectsCount);
            System.gc();

            while(wasTrapped < 1) {
                // Waiting for gc thread.
            }

            OutputAnalyzer output = executor.execute(cmd);
            output.shouldContain("MyObject");
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void pid() {
        run(new PidJcmdExecutor());
    }
}
