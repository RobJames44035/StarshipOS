/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestAllocationFailure
 * @summary Ensure the output for a minor GC with G1 that has allocation failure contains the correct strings.
 * @requires vm.gc.G1
 * @requires vm.debug
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   gc.g1.TestAllocationFailure
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestAllocationFailure {

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava("-XX:+UseG1GC",
                                                                    "-Xmx32M",
                                                                    "-Xmn16M",
                                                                    "-XX:+G1GCAllocationFailureALot",
                                                                    "-XX:G1GCAllocationFailureALotCount=100",
                                                                    "-XX:G1GCAllocationFailureALotInterval=1",
                                                                    "-XX:+UnlockDiagnosticVMOptions",
                                                                    "-Xlog:gc",
                                                                    GCTestWithAllocationFailure.class.getName());

        System.out.println(output.getStdout());
        output.shouldContain("(Evacuation Failure:");
        output.shouldHaveExitValue(0);
    }

    static class GCTestWithAllocationFailure {
        private static byte[] garbage;
        private static byte[] largeObject;
        private static Object[] holder = new Object[200]; // Must be larger than G1GCAllocationFailureALotCount

        public static void main(String [] args) {
            largeObject = new byte[16 * 1024 * 1024];
            System.out.println("Creating garbage");
            // Create 16 MB of garbage. This should result in at least one GC,
            // (Heap size is 32M, we use 17MB for the large object above)
            // which is larger than G1GCAllocationFailureALotInterval.
            for (int i = 0; i < 16 * 1024; i++) {
                holder[i % holder.length] = new byte[1024];
            }
            System.out.println("Done");
        }
    }
}
