/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6910605
 * @summary C2: NullPointerException/ClassCaseException is thrown when C2 with DeoptimizeALot is used
 *
 * @run main/othervm -Xmx128m -XX:+IgnoreUnrecognizedVMOptions -XX:+DeoptimizeALot
 *      -XX:+DoEscapeAnalysis -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:InlineSmallCode=2000
 *      compiler.c2.Test6910605_2
 */

package compiler.c2;

/*
 * Added InlineSmallCode=2000 to guarantee inlining of StringBuilder::append() to allow scalar replace StringBuilder object.
 *
 * original test: gc/gctests/StringGC
 */

public class Test6910605_2 {
    private final String toAdd = "0123456789abcdef";
    private int maxLength;
    private static final int numberOfThreads = 8;

    private class StringAdder extends Thread {
        private String s;

        public void test() {
            s = s + toAdd;
        }

        public void run() {
            do {
                test();
            } while (s.length() < maxLength);
        }
    }

    public void test() throws InterruptedException {
        maxLength = toAdd.length() * 15000 / numberOfThreads;
        StringAdder[] sa = new StringAdder[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            sa[i] = new StringAdder();
            sa[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            sa[i].join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test6910605_2 t = new Test6910605_2();
        t.test();
    }
}
