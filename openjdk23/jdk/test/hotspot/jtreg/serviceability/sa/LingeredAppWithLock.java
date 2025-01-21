/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;


public class LingeredAppWithLock extends LingeredApp {
    private static Object lockObj = new Object();

    public static void lockMethod(Object lock) {
        synchronized (lock) {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void waitMethod() {
        synchronized (lockObj) {
            try {
                lockObj.wait(300000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String args[]) {
        Thread classLock1 = new Thread(() -> lockMethod(LingeredAppWithLock.class));
        Thread classLock2 = new Thread(() -> lockMethod(LingeredAppWithLock.class));
        Thread objectLock = new Thread(() -> lockMethod(classLock1));
        Thread primitiveLock = new Thread(() -> lockMethod(int.class));
        Thread objectWait = new Thread(() -> waitMethod());

        classLock1.start();
        classLock2.start();
        objectLock.start();
        primitiveLock.start();
        objectWait.start();

        // Wait until all threads have reached their blocked or timed wait state
        while ((classLock1.getState() != Thread.State.BLOCKED &&
                classLock1.getState() != Thread.State.TIMED_WAITING) ||
               (classLock2.getState() != Thread.State.BLOCKED &&
                classLock2.getState() != Thread.State.TIMED_WAITING) ||
               (objectLock.getState() != Thread.State.TIMED_WAITING) ||
               (primitiveLock.getState() != Thread.State.TIMED_WAITING) ||
               (objectWait.getState() != Thread.State.TIMED_WAITING)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }

        LingeredApp.main(args);
    }
 }
