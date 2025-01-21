/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Test that the JVM detects the OS hugepage/THP settings correctly.
 * @library /test/lib
 * @requires vm.flagless
 * @requires os.family == "linux"
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestHugePageDetection
 */

import java.util.*;
import jdk.test.lib.os.linux.HugePageConfiguration;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestHugePageDetection {

    public static void main(String[] args) throws Exception {

        ArrayList<String> finalargs = new ArrayList<String>();
        String[] defaultArgs = {
            "-Xlog:pagesize", "-Xmx64M", "-XX:-CreateCoredumpOnCrash"
        };
        finalargs.addAll(Arrays.asList(defaultArgs));
        finalargs.add("-version");

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                new String[] {"-Xlog:pagesize", "-Xmx64M", "-version"});

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.reportDiagnosticSummary();
        output.shouldHaveExitValue(0);

        // The configuration detected by the JVM should match the OS settings

        HugePageConfiguration configurationFromOS = HugePageConfiguration.readFromOS();
        System.out.println("Configuration read from OS: " + configurationFromOS);

        HugePageConfiguration configurationFromLog = HugePageConfiguration.readFromJVMLog(output);
        System.out.println("Configuration read from JVM log: " + configurationFromLog);

        if (configurationFromOS.equals(configurationFromLog)) {
            System.out.println("Okay");
        } else {
            throw new RuntimeException("Configurations differ");
        }

        // If we want to run

    }

}
