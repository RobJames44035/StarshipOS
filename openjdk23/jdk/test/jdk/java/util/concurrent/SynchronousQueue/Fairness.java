/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/java.util.concurrent:open
 * @bug 4992438 6633113
 * @summary Checks that fairness setting is respected.
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Fairness {
    private final static VarHandle underlyingTransferQueueAccess;

    static {
        try {
            underlyingTransferQueueAccess =
                MethodHandles.privateLookupIn(
                    SynchronousQueue.class,
                    MethodHandles.lookup()
                ).findVarHandle(
                    SynchronousQueue.class,
                    "transferer",
                    Class.forName(SynchronousQueue.class.getName() + "$Transferer")
            );
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }


    private static void testFairness(boolean fair, final SynchronousQueue<Integer> q)
        throws Throwable
    {
        final LinkedTransferQueue<Integer> underlying =
            (LinkedTransferQueue<Integer>)underlyingTransferQueueAccess.get(q);

        final ReentrantLock lock = new ReentrantLock();
        final Condition ready = lock.newCondition();
        final int threadCount = 10;
        final Throwable[] badness = new Throwable[1];
        lock.lock();
        for (int i = 0; i < threadCount; i++) {
            final Integer I = i;
            Thread t = new Thread() { public void run() {
                try {
                    lock.lock();
                    ready.signal();
                    lock.unlock();
                    q.put(I);
                } catch (Throwable t) { badness[0] = t; }}};
            t.start();
            ready.await();
            // Wait until previous put:ing thread is provably parked
            while (underlying.size() < (i + 1))
                Thread.yield();

            if (underlying.size() > (i + 1))
                throw new Error("Unexpected number of waiting producers: " + i);
        }
        for (int i = 0; i < threadCount; i++) {
            int j = q.take();
            // Non-fair queues are lifo in our implementation
            if (fair ? j != i : j != threadCount - 1 - i)
                throw new Error(String.format("fair=%b i=%d j=%d%n",
                                              fair, i, j));
        }
        if (badness[0] != null) throw new Error(badness[0]);
    }

    public static void main(String[] args) throws Throwable {
        testFairness(false, new SynchronousQueue<>());
        testFairness(false, new SynchronousQueue<>(false));
        testFairness(true,  new SynchronousQueue<>(true));
    }
}
