/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Load app classes from CDS archive in parallel threads
 * @library /test/lib
 * @requires vm.cds
 * @compile test-classes/ParallelLoad.java
 * @compile test-classes/ParallelClasses.java
 * @run driver ParallelLoadTest
 */

import java.io.File;

public class ParallelLoadTest {
    public static final int MAX_CLASSES = 40;

    /* For easy stress testing, do this:

       i=0; while jtreg -DParallelLoadTest.app.loops=100 -DParallelLoadTest.boot.loops=100 \
           ParallelLoadTest.java; do i=$(expr $i + 1); echo =====$i; done

     */

    private static final int APP_LOOPS  = Integer.parseInt(System.getProperty("ParallelLoadTest.app.loops", "1"));
    private static final int BOOT_LOOPS = Integer.parseInt(System.getProperty("ParallelLoadTest.boot.loops", "1"));

    public static void main(String[] args) throws Exception {
        JarBuilder.build("parallel_load_app", "ParallelLoad", "ParallelLoadThread", "ParallelLoadWatchdog");
        JarBuilder.build("parallel_load_classes", getClassList());
        String appJar     = TestCommon.getTestJar("parallel_load_app.jar");
        String classesJar = TestCommon.getTestJar("parallel_load_classes.jar");

        // (1) Load the classes from app class loader
        String CP = appJar + File.pathSeparator + classesJar;
        TestCommon.testDump(CP, getClassList());
        for (int i = 0; i < APP_LOOPS; i++) {
            TestCommon.run("-cp", CP,  "ParallelLoad").assertNormalExit();
        }

        // (2) Load the classes from boot class loader
        String bootcp = "-Xbootclasspath/a:" + classesJar;
        TestCommon.testDump(appJar, getClassList(), bootcp);
        for (int i = 0; i < BOOT_LOOPS; i++) {
            TestCommon.run(bootcp, "-cp", appJar,
                           // "-Xlog:class+load=debug",
                           "ParallelLoad").assertNormalExit();
        }
    }

    private static String[] getClassList() {
        String[] classList = new String[MAX_CLASSES];

        int i;
        for (i = 0; i < MAX_CLASSES; i++) {
            classList[i] = "ParallelClass" + i;
        }

        return classList;
    }
}
