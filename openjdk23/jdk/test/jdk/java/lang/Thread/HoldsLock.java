/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug     4363076
 * @summary Basic functional test of Thread.holdsLock(Object)
 * @author  Josh Bloch and Steffen Grarup
 */

public class HoldsLock {
    private static Object target = null;

    private static void checkLock(boolean value) {
        if (Thread.holdsLock(target) != value)
            throw new RuntimeException("Should be " + value);
    }

    static class LockThread extends Thread {
        public void run() {
            checkLock(false);
            synchronized(target) {
                checkLock(true);
            }
            checkLock(false);
        }
    }

    public static void main(String args[]) throws Exception {
        // Test null obj case
        try {
            checkLock(false);
            throw new RuntimeException("NullPointerException not thrown");
        } catch (NullPointerException e) {
        };

        // Test uncontested case
        target = new Object();
        checkLock(false);
        synchronized(target) {
            checkLock(true);
        }
        checkLock(false);

        // Test contested case
        synchronized(target) {
            checkLock(true);
            new LockThread().start();
            checkLock(true);
            Thread.sleep(100);
            checkLock(true);
        }
        checkLock(false);
    }
}
