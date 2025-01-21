/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach037;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach037Target extends TargetApplicationWaitingAgents {

    static class ThreadGeneratingEvents extends Thread {

        ThreadGeneratingEvents() {
            super("ThreadGeneratingEvents");
        }

        // Forces string concat initialization that might otherwise only occur
        // on an error path from a callback function. The problematic 'shape' is:
        // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;
        String preInitStringBooleanConcat(String s, boolean b) {
            return "A string " + s + b;
        }

        public void run() {
            try {
                String msg = preInitStringBooleanConcat("test", true);
                //potentially use msg so it isn't elided by the JIT
                if (System.currentTimeMillis() == 0) {
                    log.display("You should never see this " + msg);
                }

                Object monitor = new Object();

                log.display(Thread.currentThread() + " is provoking MonitorWait/MonitorWaited events");

                synchronized (monitor) {
                    monitor.wait(500);
                }
            } catch (Throwable t) {
                setStatusFailed("Unexpected exception: " + t);
                t.printStackTrace(log.getOutStream());
            }
        }
    }

    protected void targetApplicationActions() throws InterruptedException {
        ThreadGeneratingEvents threadGeneratingEvents = new ThreadGeneratingEvents();
        threadGeneratingEvents.start();
        threadGeneratingEvents.join();
    }

    public static void main(String[] args) {
        new attach037Target().runTargetApplication(args);
    }
}
