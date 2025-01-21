/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS103.hs103t002;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {
    public static AtomicInteger ai = new AtomicInteger(0);
    public static final int size = 10;

    int state = 0;

    public MyThread(String name) {
        super(name);
    }

    public int getThreadState() {
        return state;
    }

    public void run() {
        try {
            doForThis();
            ai.incrementAndGet();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void doForThis() {
        for (int i = 0; i < size; i++) {
            state += 1;
        }
    }
}
