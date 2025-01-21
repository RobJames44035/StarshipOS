/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.jvmti.scenarios.hotswap.HS203.hs203t004;

public class MyThread extends Thread {

    public static volatile boolean stop = true;

    public int threadState = 0;

    public final static int run_for = 10000;

    public MyThread() {
        System.out.println(" MyThread :: MyThread()");
    }

    public void run() {
        doThisFunction();
        doTask3();
    }

    public void doThisFunction() {
        System.out.println(" MyThread.doThisFunction().");

        while (stop);

        threadState = 0;

        System.out.println(" End of doThisFunction.");
    }

    public void doTask2() {
        for (int i = 0; i < 10; i++) {
            threadState = 0;
        }
    }

    public void doTask3() {
        doTask2();
    }
}
