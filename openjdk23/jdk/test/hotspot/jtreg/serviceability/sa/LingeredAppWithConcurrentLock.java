/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LingeredAppWithConcurrentLock extends LingeredApp {

    private static final Lock lock = new ReentrantLock();

    public static void lockMethod(Lock lock) {
        lock.lock();
        synchronized (lock) {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String args[]) {
        Thread classLock1 = new Thread(() -> lockMethod(lock));
        Thread classLock2 = new Thread(() -> lockMethod(lock));
        Thread classLock3 = new Thread(() -> lockMethod(lock));

        classLock1.start();
        classLock2.start();
        classLock3.start();

        // Wait until all threads have reached their blocked or timed wait state
        while ((classLock1.getState() != Thread.State.WAITING &&
                classLock1.getState() != Thread.State.TIMED_WAITING) ||
               (classLock2.getState() != Thread.State.WAITING &&
                classLock2.getState() != Thread.State.TIMED_WAITING) ||
               (classLock3.getState() != Thread.State.WAITING &&
                classLock3.getState() != Thread.State.TIMED_WAITING)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("classLock1 state: " + classLock1.getState());
        System.out.println("classLock2 state: " + classLock2.getState());
        System.out.println("classLock3 state: " + classLock3.getState());

        LingeredApp.main(args);
    }
 }
