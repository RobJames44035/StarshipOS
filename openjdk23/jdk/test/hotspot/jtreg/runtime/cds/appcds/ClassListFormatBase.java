/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;

/**
 * Base class for DumpClassListWithLF, customerLoader/ClassListFormat[A,B,C...].java
 */
public class ClassListFormatBase {
    protected static String RUN_ONLY_TEST = null;

    static void dumpShouldFail(String caseHelp, String appJar, String[] appClasses,
                               String... expected_errors) throws Throwable {
        if (RUN_ONLY_TEST != null && !caseHelp.startsWith(RUN_ONLY_TEST)) {
            System.out.println("Skipped via RUN_ONLY_TEST: " + caseHelp);
            return;
        }
        System.out.println("------------------------------");
        System.out.println(caseHelp);
        System.out.println("------------------------------");

        try {
            OutputAnalyzer output = TestCommon.dump(appJar, appClasses, "-Xlog:cds+lambda=debug");
            output.shouldHaveExitValue(1);
            for (String s : expected_errors) {
                output.shouldContain(s);
            }
        } catch (Throwable t) {
            System.out.println("FAILED CASE: " + caseHelp);
            throw t;
        }
    }

    static void dumpShouldPass(String caseHelp, String appJar, String[] appClasses,
                               String... expected_msgs) throws Throwable {
        if (RUN_ONLY_TEST != null && !caseHelp.startsWith(RUN_ONLY_TEST)) {
            System.out.println("Skipped via RUN_ONLY_TEST: " + caseHelp);
            return;
        }
        System.out.println("------------------------------");
        System.out.println(caseHelp);
        System.out.println("------------------------------");

        try {
            OutputAnalyzer output = TestCommon.dump(appJar, appClasses, "-Xlog:cds", "-Xlog:cds+lambda=debug");
            output.shouldHaveExitValue(0);
            output.shouldContain("Dumping");
            for (String s : expected_msgs) {
                output.shouldContain(s);
            }
        } catch (Throwable t) {
            System.out.println("FAILED CASE: " + caseHelp);
            throw t;
        }
    }

    static String[] classlist(String... args) {
        return TestCommon.list(args);
    }
}
