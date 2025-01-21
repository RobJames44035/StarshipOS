/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7190310
 * @summary Inlining WeakReference.get(), and hoisting $referent may lead to non-terminating loops
 *
 * @run main/othervm/timeout=600 -Xbatch compiler.c2.Test7190310
 */

/*
 * Note bug exhibits as infinite loop, timeout is helpful.
 * It should normally finish pretty quickly, but on some especially slow machines
 * it may not.  The companion _unsafe test lacks a timeout, but that is okay.
 */
package compiler.c2;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.ref.Cleaner;

public class Test7190310 {
    private static Object str = new Object() {
        public String toString() {
            return "The Object";
        }
    };
    private final static ReferenceQueue<Object> rq =
            new ReferenceQueue<Object>();
    private final static WeakReference<Object> wr =
            new WeakReference<Object>(str, rq);

    public static void main(String[] args)
            throws InterruptedException {
        Cleaner.create().register(str, () -> System.out.println("The Object is being finalized"));
        Thread reader = new Thread() {
            public void run() {
                while (wr.get() != null) {
                }
                System.out.println("wr.get() returned null");
            }
        };

        Thread queueReader = new Thread() {
            public void run() {
                try {
                    Reference<? extends Object> ref = rq.remove();
                    System.out.println(ref);
                    System.out.println("queueReader returned, ref==wr is "
                            + (ref == wr));
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrupted - exiting");
                }
            }
        };

        reader.start();
        queueReader.start();

        Thread.sleep(1000);
        str = null;
        System.gc();
    }
}

