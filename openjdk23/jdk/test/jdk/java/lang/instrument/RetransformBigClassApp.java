/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.io.*;

public class RetransformBigClassApp {
    /**
     * Memory leak is assumed, if application consumes more than specified amount of memory during its execution.
     * The number is given in KB.
     */
    private static final long MEM_LEAK_THRESHOLD = 32 * 1024; // 32MB

    public static void main(String[] args) throws Exception {
        System.out.println("Creating instance of " +
            RetransformBigClassAgent.clz);
        RetransformBigClassAgent.clz.newInstance();

        // Do a short warmup before creating the NMT baseline
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ie) {
        }

        NMTHelper.baseline();

        int count = 0;
        while (!RetransformBigClassAgent.doneRetransforming) {
            System.out.println("App loop count: " + ++count);
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ie) {
            }
        }
        System.out.println("App looped  " + count + " times.");

        long committedDiff = NMTHelper.committedDiff();
        if (committedDiff > MEM_LEAK_THRESHOLD) {
            throw new Exception("FAIL: Committed memory usage increased by " + committedDiff + "KB " +
                               "(greater than " + MEM_LEAK_THRESHOLD + "KB)");
        }
        System.err.println("PASS: Committed memory usage increased by " + committedDiff + "KB " +
                           "(not greater than " + MEM_LEAK_THRESHOLD + "KB)");
    }
}
