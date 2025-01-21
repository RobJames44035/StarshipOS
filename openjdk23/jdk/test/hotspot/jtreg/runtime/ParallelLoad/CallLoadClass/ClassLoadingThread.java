/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.util.concurrent.Semaphore;

class ClassLoadingThread extends Thread {

    private ClassLoader ldr = null;
    private int which;
    private Semaphore syncOrder;

    public ClassLoadingThread(ClassLoader loader, int i, Semaphore sem) {
        ldr = loader;
        which = i;
        syncOrder = sem;
    }

    private boolean success = true;
    public boolean report_success() { return success; }

    public void callForName() {
        try {
            ThreadPrint.println("Starting forName thread ...");
            // Initiate class loading using specified type
            Class<?> a = Class.forName("A", true, ldr);
            Object obj = a.getConstructor().newInstance();
        } catch (Throwable e) {
            ThreadPrint.println("Exception is caught: " + e);
            e.printStackTrace();
            success = false;
        }
    }

    public void callLoadClass() {
        try {
            ThreadPrint.println("Starting loadClass thread ...");
            Class<?> a = ldr.loadClass("A");
            Object obj = a.getConstructor().newInstance();
            success = false; // Should have thrown LinkageError
        } catch (Throwable e) {
            // If you call loadClass directly, this will result in LinkageError
            ThreadPrint.println("Exception is caught: " + e);
            e.printStackTrace();
            success = (e instanceof LinkageError);
        }
    }

    public void run() {
       if (which == 0) {
           callLoadClass();
       } else {
           try {
               syncOrder.acquire();  // wait until loadClass is waiting.
           } catch (InterruptedException idc) {}
           callForName();
       }
    }
}
