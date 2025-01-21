/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.util.Properties;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.io.IOException;
import java.net.URL;

/* @test
 * @bug 6977738 8029891
 * @summary Test ClassLoader.getResource() that should not deadlock
 #          if another thread is holding the system properties object
 *
 * @build GetResource
 * @run main GetResource
 */

public class GetResource {
    CyclicBarrier go = new CyclicBarrier(2);
    CyclicBarrier done = new CyclicBarrier(2);
    Thread t1, t2;
    public GetResource() {
        t1 = new Thread() {
            public void run() {
                Properties prop = System.getProperties();
                synchronized (prop) {
                    System.out.println("Thread 1 ready");
                    try {
                        go.await();
                        prop.put("property", "value");
                        prop.store(System.out, "");
                        done.await();   // keep holding the lock until t2 finishes
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Thread 1 exits");
            }
        };

        t2 = new Thread()  {
            public void run() {
                System.out.println("Thread 2 ready");
                try {
                    go.await();  // wait until t1 holds the lock of the system properties

                    URL u1 = Thread.currentThread().getContextClassLoader().getResource("unknownresource");
                    done.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 2 exits");
            }
        };
    }

    public void run() throws Exception {
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        new GetResource().run();
    }
}
