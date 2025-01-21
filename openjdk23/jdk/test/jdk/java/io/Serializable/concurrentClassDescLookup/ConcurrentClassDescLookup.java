/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @summary Verify that concurrent class descriptor lookups function properly,
 *          even when class descriptor initialization is slow or throws an
 *          exception.
 */

import java.io.*;
import java.util.concurrent.CountDownLatch;

class Good implements Serializable {
    private static final long serialVersionUID = 6319710844400051132L;

    static {
        try { Thread.sleep(1000); } catch (InterruptedException ex) {}
    }
}

class Bad implements Serializable {
    // explicit suid triggers class initialization during classdesc lookup
    private static final long serialVersionUID = 0xBAD;
    static {
        try { Thread.sleep(1000); } catch (InterruptedException ex) {}
        if ("foo".equals("foo")) {
            throw new RuntimeException();
        }
    }
}

class SuccessfulLookup extends Thread {
    Class<?> cl;
    long suid;
    final CountDownLatch lookupLatch;
    boolean ok;

    SuccessfulLookup(Class<?> cl, long suid, CountDownLatch lookupLatch) {
        this.cl = cl;
        this.suid = suid;
        this.lookupLatch = lookupLatch;
    }

    public void run() {
        lookupLatch.countDown(); // let others know we are ready
        try {
            lookupLatch.await(); // await for others
        } catch (InterruptedException ex) {}
        for (int i = 0; i < 100; i++) {
            if (ObjectStreamClass.lookup(cl).getSerialVersionUID() != suid) {
                return;
            }
        }
        ok = true;
    }
}

class FailingLookup extends Thread {
    Class<?> cl;
    final CountDownLatch lookupLatch;
    boolean ok;

    FailingLookup(Class<?> cl, CountDownLatch lookupLatch) {
        this.cl = cl;
        this.lookupLatch = lookupLatch;
    }

    public void run() {
        lookupLatch.countDown(); // let others know we are ready
        try {
            lookupLatch.await(); // await for others
        } catch (InterruptedException ex) {}
        for (int i = 0; i < 100; i++) {
            try {
                ObjectStreamClass.lookup(cl);
                return;
            } catch (Throwable th) {
            }
        }
        ok = true;
    }
}

public class ConcurrentClassDescLookup {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = ConcurrentClassDescLookup.class.getClassLoader();
        Class<?> cl = Class.forName("Good", false, loader);
        int numSuccessfulLookups = 50;
        CountDownLatch sLookupLatch = new CountDownLatch(numSuccessfulLookups);
        SuccessfulLookup[] slookups = new SuccessfulLookup[numSuccessfulLookups];
        for (int i = 0; i < slookups.length; i++) {
            slookups[i] = new SuccessfulLookup(cl, 6319710844400051132L, sLookupLatch);
            slookups[i].start();
        }
        System.out.println("awaiting completion of " + slookups.length + " SuccessfulLookup");
        for (int i = 0; i < slookups.length; i++) {
            slookups[i].join();
            if (!slookups[i].ok) {
                throw new Error();
            }
        }
        System.out.println("all " + slookups.length + " SuccessfulLookup completed");
        cl = Class.forName("Bad", false, loader);
        int numFailingLookups = 50;
        CountDownLatch fLookupLatch = new CountDownLatch(numFailingLookups);
        FailingLookup[] flookups = new FailingLookup[numFailingLookups];
        for (int i = 0; i < flookups.length; i++) {
            flookups[i] = new FailingLookup(cl, fLookupLatch);
            flookups[i].start();
        }
        System.out.println("awaiting completion of " + flookups.length + " FailingLookup");
        for (int i = 0; i < flookups.length; i++) {
            flookups[i].join();
            if (!flookups[i].ok) {
                throw new Error();
            }
        }
        System.out.println("all " + flookups.length + " FailingLookup completed");
    }
}
