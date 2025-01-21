/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @summary Basic functional test of ThreadLocal
 * @author Josh Bloch
 */

public class Basic {
    static ThreadLocal n = new ThreadLocal() {
        int i = 0;
        protected synchronized Object initialValue() {
            return new Integer(i++);
        }
    };

    public static void main(String args[]) throws Exception {
        int threadCount = 100;
        Thread th[] = new Thread[threadCount];
        final int x[] = new int[threadCount];

        // Start the threads
        for(int i=0; i<threadCount; i++) {
            th[i] = new Thread() {
                public void run() {
                    int threadId = ((Integer)(n.get())).intValue();
                    for (int j=0; j<threadId; j++) {
                        x[threadId]++;
                        Thread.currentThread().yield();
                    }
                }
            };
            th[i].start();
        }

        // Wait for the threads to finish
        for(int i=0; i<threadCount; i++)
            th[i].join();

        // Check results
        for(int i=0; i<threadCount; i++)
            if (x[i] != i)
                throw(new Exception("x[" + i + "] =" + x[i]));
    }
}
