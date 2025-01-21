/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test MyLoaderTest
 * @bug 8262046
 * @summary Call handle_parallel_super_load, loading parallel threads that throw CCE
 * @modules java.base/jdk.internal.misc
 * @library /testlibrary/asm
 * @library /test/lib
 * @compile -XDignore.symbol.file AsmClasses.java
 * @compile test-classes/ClassInLoader.java test-classes/A.java test-classes/B.java ../share/ThreadPrint.java
 * @run main/othervm ParallelSuperTest
 * @run main/othervm ParallelSuperTest -parallel -parallelCapable
 */

public class ParallelSuperTest {
    public static void main(java.lang.String[] args) throws Exception {
        boolean parallel = false;
        boolean parallelCapable = false;
        boolean success = true;
        for (int i = 0; i < args.length; i++) {
            try {
                // Don't print debug info
                if (args[i].equals("-parallel")) {
                    parallel = true;
                } else if (args[i].equals("-parallelCapable")) {
                    parallelCapable = true;
                } else {
                    System.out.println("Unrecognized " + args[i]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid parameter: " + args[i - 1] + " " + args[i]);
            }
        }
        // The -parallel -parallelCapable case will deadlock on locks for A and B if
        // the jvm doesn't eagerly try to load A's superclass from the second thread.
        // ie. needs to call SystemDictionary::handle_parallel_super_load
        if (parallelCapable) {
            MyLoader ldr = new MyLoader(parallel);
            ldr.startLoading();
            success = ldr.report_success();
        } else {
            MyNonParallelLoader ldr = new MyNonParallelLoader(parallel);
            ldr.startLoading();
            success = ldr.report_success();
        }
        if (success) {
            System.out.println("PASSED");
        } else {
            throw new RuntimeException("FAILED");
        }
    }
}
