/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;

/*
 * This is a base class for the following test cases:
 *   ParallelTestMultiFP.java
 *   ParallelTestSingleFP.java
 */
public class ParallelTestBase {
    public static final int MAX_CLASSES = 40; // must match ../test-classes/ParallelLoad.java
    public static int NUM_THREADS = 4;        // must match ../test-classes/ParallelLoad.java

    public static final int SINGLE_CUSTOM_LOADER = 1;
    public static final int MULTI_CUSTOM_LOADER  = 2;

    public static final int FINGERPRINT_MODE = 1;

    public static void run(String[] args, int loaderType, int mode) throws Exception {
        String[] cust_classes = new String[MAX_CLASSES];
        String[] cust_list;

        if (mode == FINGERPRINT_MODE) {
            cust_list = new String[MAX_CLASSES];
        } else {
            cust_list = new String[MAX_CLASSES * NUM_THREADS];
        }

        for (int i = 0; i<MAX_CLASSES; i++) {
            cust_classes[i] = "ParallelClass" + i;
        }
        String customJarPath = JarBuilder.build("ParallelTestBase", cust_classes);

        for (int i = 0, n=0; i<MAX_CLASSES; i++) {
            int super_id = 1;
            if (mode == FINGERPRINT_MODE) {
                // fingerprint mode -- no need to use the "loader:" option.
                int id = i + 2;
                cust_list[i] = cust_classes[i] + " id: " + id + " super: " + super_id + " source: " + customJarPath;
            } else {
                throw new RuntimeException("Only FINGERPRINT_MODE is supported");
            }
        }

        String app_list[];
        String mainClass;
        String appJar;

        if (mode == FINGERPRINT_MODE) {
            appJar = JarBuilder.build("parallel_fp",
                                      "ParallelLoad",
                                      "ParallelLoadThread",
                                      "ParallelLoadWatchdog");
            app_list = new String[] {
                "java/lang/Object id: 1",
                "ParallelLoad",
                "ParallelLoadThread",
                "ParallelLoadWatchdog"
            };
            mainClass = "ParallelLoad";
        } else {
            throw new RuntimeException("Currently only FINGERPRINT_MODE is supported");
        }

        OutputAnalyzer output;
        TestCommon.testDump(appJar, TestCommon.concat(app_list, cust_list));

        String loaderTypeArg = (loaderType == SINGLE_CUSTOM_LOADER) ? "SINGLE_CUSTOM_LOADER" : "MULTI_CUSTOM_LOADER";
        String modeArg = "FINGERPRINT_MODE";

        output = TestCommon.exec(appJar,
                                 // command-line arguments ...
                                 "--add-opens=java.base/java.security=ALL-UNNAMED",
                                 mainClass, loaderTypeArg, modeArg, customJarPath);
        TestCommon.checkExec(output);
    }
}
