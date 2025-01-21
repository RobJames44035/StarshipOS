/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/* @test
 * @bug 8014890
 * @summary Verify that a race between ReferenceQueue.enqueue() and poll() does not occur.
 * @author thomas.schatzl@oracle.com
 * @run main/othervm -Xmx10M EnqueuePollRace
 */

import java.lang.ref.*;

public class EnqueuePollRace {

    public static void main(String args[]) throws Exception {
        new WeakRef().run();
        System.out.println("Test passed.");
    }

    static class WeakRef {
        ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
        final int numReferences = 100;
        final Reference refs[] = new Reference[numReferences];
        final int iterations = 1000;

        void run() throws InterruptedException {
            for (int i = 0; i < iterations; i++) {
                queue = new ReferenceQueue<Object>();

                for (int j = 0; j < refs.length; j++) {
                    refs[j] = new WeakReference(new Object(), queue);
                }

                System.gc(); // enqueues references into the list of discovered references

                // now manually enqueue all of them
                for (int j = 0; j < refs.length; j++) {
                    refs[j].enqueue();
                }
                // and get them back. There should be exactly numReferences
                // entries in the queue now.
                int foundReferences = 0;
                while (queue.poll() != null) {
                    foundReferences++;
                }

                if (foundReferences != refs.length) {
                    throw new RuntimeException("Got " + foundReferences + " references in the queue, but expected " + refs.length);
                }
            }
        }
    }
}
