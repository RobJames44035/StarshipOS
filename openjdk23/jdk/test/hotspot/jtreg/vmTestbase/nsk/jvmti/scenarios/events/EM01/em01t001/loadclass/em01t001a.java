/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM01;

public class em01t001a extends Thread {
    public static int toProvokePreparation;

    public em01t001a() {
        setName("em01t001a_Thread");
    }

    public void run() {
        synchronized(em01t001.threadStarting) {
            em01t001.threadStarting.notify();
        }

        synchronized(em01t001.threadWaiting) {
        }
    }
}
