/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4940372
 * @summary Ensure that the wakeup state is cleared by selectNow()
 */

import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class WakeupNow {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }

    // Test if selectNow clears wakeup with the wakeup fd and
    // another fd in the selector.
    // This fails before the fix on Linux
    private static void test1() throws Exception {
        Selector sel = Selector.open();
        Pipe p = Pipe.open();
        p.source().configureBlocking(false);
        p.source().register(sel, SelectionKey.OP_READ);
        sel.wakeup();
        // ensure wakeup is consumed by selectNow
        Thread.sleep(2000);
        sel.selectNow();
        long startTime = System.nanoTime();
        int n = sel.select(2000);
        long endTime = System.nanoTime();
        p.source().close();
        p.sink().close();
        sel.close();
        long delta = endTime - startTime;
        if (delta < 1_000_000_000)
            throw new RuntimeException("test failed with delta " + delta);
    }

    // Test if selectNow clears wakeup with only the wakeup fd
    // in the selector.
    // This fails before the fix on Solaris
    private static void test2() throws Exception {
        Selector sel = Selector.open();
        sel.wakeup();
        // ensure wakeup is consumed by selectNow
        Thread.sleep(2000);
        sel.selectNow();
        long startTime = System.nanoTime();
        int n = sel.select(2000);
        long endTime = System.nanoTime();
        sel.close();
        long delta = endTime - startTime;
        if (delta < 1_000_000_000)
            throw new RuntimeException("test failed with delta " + delta);
    }

}
