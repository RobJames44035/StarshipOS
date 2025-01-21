/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM01;

public class em01t002a extends Thread {
    public static int toProvokePreparation;

    public em01t002a() {
        setName("em01t002a_Thread");
    }

    public void run() {
        synchronized(em01t002.threadStarting) {
            em01t002.threadStarting.notify();
        }

        synchronized(em01t002.threadWaiting) {
        }
    }
}
