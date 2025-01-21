/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8165246 8010319
 * @summary Test clean_previous_versions flag and processing during class unloading.
 * @requires vm.jvmti
 * @requires vm.opt.final.ClassUnloading
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.compiler
 *          java.instrument
 *          jdk.jartool/sun.tools.jar
 * @run driver RedefineClassHelper
 * @run driver RedefinePreviousVersions test
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

// package access top-level classes to avoid problem with RedefineClassHelper
// and nested types.

class RedefinePreviousVersions_B { }

class RedefinePreviousVersions_Running {
    public static volatile boolean stop = false;
    public static volatile boolean running = false;
    static void localSleep() {
        try {
            Thread.sleep(10); // sleep for 10 ms
        } catch(InterruptedException ie) {
        }
    }

    public static void infinite() {
        running = true;
        while (!stop) { localSleep(); }
    }
}



public class RedefinePreviousVersions {

    public static String newB = """
                class RedefinePreviousVersions_B {
                }
                """;

    public static String newRunning = """
        class RedefinePreviousVersions_Running {
            public static volatile boolean stop = true;
            public static volatile boolean running = true;
            static void localSleep() { }
            public static void infinite() { }
        }
        """;

    public static void main(String[] args) throws Exception {

        if (args.length > 0) {

            // java -javaagent:redefineagent.jar -Xlog:stuff RedefinePreviousVersions
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder( "-javaagent:redefineagent.jar",
               "-Xlog:redefine+class+iklass+add=trace,redefine+class+iklass+purge=trace",
               "RedefinePreviousVersions");
            new OutputAnalyzer(pb.start())
              .shouldContain("Class unloading: should_clean_previous_versions = false")
              .shouldContain("Class unloading: should_clean_previous_versions = true")
              .shouldHaveExitValue(0);
            return;
        }

        // Start with a full GC.
        System.gc();

        // Redefine a class and create some garbage
        // Since there are no methods running, the previous version is never added to the
        // previous_version_list and the flag _should_clean_previous_versions should stay false
        RedefineClassHelper.redefineClass(RedefinePreviousVersions_B.class, newB);

        for (int i = 0; i < 10 ; i++) {
            String s = new String("some garbage");
            System.gc();
        }

        // Start a class that has a method running
        new Thread() {
            public void run() {
                RedefinePreviousVersions_Running.infinite();
            }
        }.start();

        while (!RedefinePreviousVersions_Running.running) {
            Thread.sleep(10); // sleep for 10 ms
        }

        // Since a method of newRunning is running, this class should be added to the previous_version_list
        // of Running, and _should_clean_previous_versions should return true at class unloading.
        RedefineClassHelper.redefineClass(RedefinePreviousVersions_Running.class, newRunning);

        for (int i = 0; i < 10 ; i++) {
            String s = new String("some garbage");
            System.gc();
        }

        // purge should clean everything up, except Xcomp it might not.
        RedefinePreviousVersions_Running.stop = true;

        for (int i = 0; i < 10 ; i++) {
            String s = new String("some garbage");
            System.gc();
        }
    }
}
