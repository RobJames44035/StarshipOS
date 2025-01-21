/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @summary Test that virtual threads are GC'ed
 * @run junit Collectable
 */

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Collectable {

    /**
     * Test that an unstarted virtual thread can be GC"ed.
     */
    @Test
    void testUnstartedThread() {
        var thread = Thread.ofVirtual().unstarted(() -> { });
        var ref = new WeakReference<>(thread);
        thread = null;
        waitUntilCleared(ref);
    }

    /**
     * Test that a terminated virtual thread can be GC'ed.
     */
    @Test
    void testTerminatedThread() throws Exception {
        var thread = Thread.ofVirtual().start(() -> { });
        thread.join();
        var ref = new WeakReference<>(thread);
        thread = null;
        waitUntilCleared(ref);
    }

    private static void waitUntilCleared(WeakReference<?> ref) {
        while (ref.get() != null) {
            System.gc();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignore) { }
        }
    }
}
