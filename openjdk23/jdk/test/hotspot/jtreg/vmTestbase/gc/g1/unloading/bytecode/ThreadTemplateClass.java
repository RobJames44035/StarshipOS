/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.bytecode;

/**
 * This is another template class that extends Thread.
 */
public class ThreadTemplateClass extends Thread {

    synchronized public void finishThread() {
        notifyAll();
    }

    @Override
    public synchronized void run() {
        super.run();
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException ", e);
        }
    }

    static int field2 = -1;

    public static void methodForCompilation(Object object) {
        int i = object.hashCode();
        i = i * 2000 / 1994 + 153;
        field2 = i;
    }


}
