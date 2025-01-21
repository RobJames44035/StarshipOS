/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.*;
import jdk.test.lib.classloader.ClassUnloadCommon;
import java.util.concurrent.Semaphore;

// This class loader will deadlock where one thread has a lock for A, trying to get a lock for B
// and the other thread has a lock for B, trying to get a lock for A in the case of
// A extends B extends A.  It should throw ClassCircularityError from both threads.

class MyLoader extends ClassLoader {
    static {
        registerAsParallelCapable();
    }

    private static boolean first = true;

    public Class loadClass(String name) throws ClassNotFoundException {
        // Wait before getting B lock.
        if (name.equals("B") && first) {
            first = false;
            makeThreadWait();
        }
        synchronized(getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c != null) return c;

            if (name.equals("B") && !first) {
              wakeUpThread();
            }

            byte[] b = loadClassData(name);
            if (b != null) {
                return defineClass(name, b, 0, b.length);
            } else {
                return super.loadClass(name);
            }
        }
    }

    private static boolean parallel = false;
    private Object sync = new Object();
    private static volatile boolean waiting = false;
    private static Semaphore mainSem = new Semaphore(0);

    private void makeThreadWait() {
        if (!parallel) { return; }

        // Signal main thread to start t2.
        mainSem.release();

        if (isRegisteredAsParallelCapable()) {
            synchronized(sync) {
                try {
                    ThreadPrint.println("t1 waits parallelCapable loader");
                    waiting = true;
                    sync.wait();  // Give up lock before request to load B
                } catch (InterruptedException e) {}
             }
         } else {
             try {
                ThreadPrint.println("t1 waits non-parallelCapable loader");
                wait(200);  // Give up lock before request to load B
              } catch (InterruptedException e) {}
         }
    }

    // Parallel capable loader should wake up the first thread.
    // Non-parallelCapable class loader thread will be woken up by the jvm.
    private void wakeUpThread() {
        if (isRegisteredAsParallelCapable()) {
            while (!waiting) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
            synchronized(sync) {
                sync.notify();
            }
        }
    }

    private byte[] loadClassData(String name) {
        if (name.equals("A")) {
            ThreadPrint.println("loading A extends B");
            return ClassUnloadCommon.getClassData("A");
        } else if (name.equals("B")) {
            ThreadPrint.println("loading B extends A");
            try {
                return AsmClasses.dumpB();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (!name.startsWith("java")) {
            return ClassUnloadCommon.getClassData(name);
        }
        return null;
    }


    ClassLoadingThread[] threads = new ClassLoadingThread[2];
    private boolean success = true;

    public boolean report_success() {
        for (int i = 0; i < 2; i++) {
          try {
            threads[i].join();
            if (!threads[i].report_success()) success = false;
          } catch (InterruptedException e) {}
        }
        return success;
    }

    void startLoading() {

        for (int i = 0; i < 2; i++) {
            threads[i] = new ClassLoadingThread(this, parallel ? null : mainSem);
            threads[i].setName("Loading Thread #" + (i + 1));
            threads[i].start();
            System.out.println("Thread " + (i + 1) + " was started...");
            // wait to start the second thread if not concurrent
            if (i == 0) {
                try {
                    ThreadPrint.println("Main thread calls mainSem.acquire()");
                    mainSem.acquire();
                } catch (InterruptedException e) {}
            }
        }
    }

    MyLoader(boolean load_in_parallel) {
       parallel = load_in_parallel;
    }
}
