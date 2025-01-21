/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4954921 8009259
 * @library /test/lib
 * @modules java.base/jdk.internal.ref
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main ExitOnThrow
 * @summary Ensure that if a cleaner throws an exception then the VM exits
 */

import jdk.internal.ref.Cleaner;

import jdk.test.lib.process.ProcessTools;

public class ExitOnThrow {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            ProcessTools.executeTestJava("--add-exports", "java.base/jdk.internal.ref=ALL-UNNAMED",
                                         "ExitOnThrow",
                                         "-executeCleaner")
                        .outputTo(System.out)
                        .errorTo(System.out)
                        .shouldHaveExitValue(1)
                        .shouldContain("java.lang.RuntimeException: Foo!");
        } else {
            Cleaner.create(new Object(),
                           () -> { throw new RuntimeException("Foo!"); } );
            while (true) {
                System.gc();
                Thread.sleep(100);
            }
        }
    }

}
