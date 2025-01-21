/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Example test with multi-threaded use of the CompileFramework.
 *          Tests that the source and class directories are set up correctly.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 * @run driver compile_framework.tests.TestConcurrentCompilation
 */

package compile_framework.tests;

import compiler.lib.compile_framework.*;

import java.util.ArrayList;
import java.util.List;

public class TestConcurrentCompilation {

    // Generate a source java file as String
    public static String generate(int i) {
        return String.format("""
                             public class XYZ {
                                 public static int test() {
                                     return %d;
                                 }
                             }
                             """, i);
    }

    public static void test(int i) {
        System.out.println("Generate and compile XYZ for " + i);
        CompileFramework comp = new CompileFramework();
        comp.addJavaSourceCode("XYZ", generate(i));
        comp.compile();

        // Now, sleep to give the other threads time to compile and store their class-files.
        System.out.println("Sleep for " + i);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted for " + i);
        }

        // Now, hopefully all threads have compiled and stored their class-files.
        // We can check if we get the expected result, i.e. the class-file from the current thread.
        System.out.println("Run XYZ.test for " + i);
        int j = (int)comp.invoke("XYZ", "test", new Object[] {});
        if (i != j) {
            System.out.println("Wrong value: " + i + " vs " + j);
            throw new RuntimeException("Wrong value: " + i + " vs " + j);
        }
        System.out.println("Success for " + i);
    }

    public static class MyRunnable implements Runnable {
        private int i;

        public MyRunnable(int i) {
            this.i = i;
        }

        public void run() {
            TestConcurrentCompilation.test(i);
        }
    }

    public static void main(String[] args) {
        System.out.println("Generating threads:");
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new MyRunnable(i));
            thread.start();
            threads.add(thread);
        }
        System.out.println("Waiting to join threads:");
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted", e);
        }
        System.out.println("Success.");
    }
}
