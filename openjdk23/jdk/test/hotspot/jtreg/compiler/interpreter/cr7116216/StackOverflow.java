/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7116216
 * @summary The vm crashes when GC happens during throwing a StackOverflow exception
 * @library /
 * @run main/othervm -Xcomp -Xbatch compiler.interpreter.cr7116216.StackOverflow
 */

package compiler.interpreter.cr7116216;

public class StackOverflow {
    static String stackOverflow_largeFrame_liveOopForGC;

    public static int stackOverflow_largeFrame(int call_count, String liveOopForGC) {
        try {
            int return_count = stackOverflow_largeFrame(++call_count, liveOopForGC);
            if (return_count == 0) {
                try {
                    LargeFrame.method_with_many_locals(liveOopForGC, 2,3,4,5,6,7,liveOopForGC);
                } catch (StackOverflowError e2) {
                    // access liveOopForGC to make it a live variable
                    stackOverflow_largeFrame_liveOopForGC = liveOopForGC;
                }
            }
            return return_count - 1;
        } catch (StackOverflowError e) {
            // Return a value that is large enough such that no unrecoverable
            // stack overflow will occur afterwards, but that is small enough
            // such that calling LargeFrame.method_with_many_locals() will
            // cause a StackOverflowError.
            // Don't use a call here because we're out of stack space anyway!
            int tmp = call_count / 2;
            return (tmp < 100 ? tmp : 100);
        }
    }
    public static void main(String args[]) {
        LargeFrame.method_with_many_locals(new Object(), 2,3,4,5,6,7,new Object());

        stackOverflow_largeFrame(0, "this is a live oop to test GC");
        System.out.println("finished ok!");
    }
}
