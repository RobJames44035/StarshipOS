/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package gc;

/*
 * @test TestPLABAdaptToMinTLABSizeG1
 * @bug 8289137
 * @summary Make sure that Young/OldPLABSize adapt to MinTLABSize setting.
 * @requires vm.gc.G1
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.TestPLABAdaptToMinTLABSize -XX:+UseG1GC
 */

/*
 * @test TestPLABAdaptToMinTLABSizeParallel
 * @bug 8289137
 * @summary Make sure that Young/OldPLABSize adapt to MinTLABSize setting.
 * @requires vm.gc.Parallel
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.TestPLABAdaptToMinTLABSize -XX:+UseParallelGC
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestPLABAdaptToMinTLABSize {
    private static void runTest(boolean shouldSucceed, String... extraArgs) throws Exception {
        ArrayList<String> testArguments = new ArrayList<String>();
        testArguments.add("-Xmx12m");
        testArguments.add("-XX:+PrintFlagsFinal");
        Collections.addAll(testArguments, extraArgs);
        testArguments.add("-version");

        OutputAnalyzer output = ProcessTools.executeTestJava(testArguments);

        System.out.println(output.getStderr());

        if (shouldSucceed) {
            output.shouldHaveExitValue(0);

            long oldPLABSize = Long.parseLong(output.firstMatch("OldPLABSize\\s+=\\s(\\d+)",1));
            long youngPLABSize = Long.parseLong(output.firstMatch("YoungPLABSize\\s+=\\s(\\d+)",1));
            long minTLABSize = Long.parseLong(output.firstMatch("MinTLABSize\\s+=\\s(\\d+)",1));

            System.out.println("OldPLABSize=" + oldPLABSize + " YoungPLABSize=" + youngPLABSize +
                               "MinTLABSize=" + minTLABSize);

        } else {
            output.shouldNotHaveExitValue(0);
        }
    }

    public static void main(String[] args) throws Exception {
        String gc = args[0];
        runTest(true, gc, "-XX:MinTLABSize=100k");
        // Should not succeed when explicitly specifying invalid combination.
        runTest(false, gc, "-XX:MinTLABSize=100k", "-XX:OldPLABSize=5k");
        runTest(false, gc, "-XX:MinTLABSize=100k", "-XX:YoungPLABSize=5k");
    }
}
