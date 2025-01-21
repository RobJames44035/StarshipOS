/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestMinAndInitialSurvivorRatioFlags
 * @summary Verify that MinSurvivorRatio and InitialSurvivorRatio flags work
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver/timeout=240 gc.arguments.TestMinAndInitialSurvivorRatioFlags
 */

import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Utils;
import jdk.test.whitebox.WhiteBox;

/* Test verifies that VM can start with any GC when MinSurvivorRatio and
 * InitialSurvivorRatio flags passed and for Parallel GC it verifies that
 * after start up survivor ratio equal to InitialSurvivorRatio value and
 * that actual survivor ratio will never be less than MinSurvivorRatio.
 */
public class TestMinAndInitialSurvivorRatioFlags {

    public static final long M = 1024 * 1024;
    public static final long HEAP_SIZE = 200 * M;
    public static final long NEW_SIZE = 100 * M;

    public static void main(String args[]) throws Exception {
        LinkedList<String> options = new LinkedList<>(
                Arrays.asList(Utils.getFilteredTestJavaOpts("-XX:[^ ]*SurvivorRatio=[^ ]+"))
        );

        testSurvivorRatio(5, -1, -1, options, true);
        testSurvivorRatio(10, -1, -1, options, true);
        testSurvivorRatio(-1, 5, 3, options, true);
        testSurvivorRatio(-1, 15, 3, options, true);
        testSurvivorRatio(-1, 15, 3, options, false);
        testSurvivorRatio(-1, 10, 10, options, true);
        testSurvivorRatio(-1, 3, 15, options, true);
        testSurvivorRatio(-1, 3, 15, options, false);
    }

    /**
     * Test that MinSurvivorRatio and InitialSurvivorRatio flags work.
     *
     * @param survivorRatio value for -XX:SurvivorRatio option, omitted if negative
     * @param initRatio value for -XX:InitialSurvivorRatio option, omitted if negative
     * @param minRatio value for -XX:MinSurvivorRatio option, omitted if negative
     * @param options additional options for VM
     * @param useAdaptiveSizePolicy turn on or off UseAdaptiveSizePolicy option
     */
    public static void testSurvivorRatio(int survivorRatio,
            int initRatio,
            int minRatio,
            LinkedList<String> options,
            boolean useAdaptiveSizePolicy) throws Exception {

        LinkedList<String> vmOptions = new LinkedList<>(options);
        Collections.addAll(vmOptions,
                "-Xbootclasspath/a:.",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI",
                "-XX:MaxNewSize=" + NEW_SIZE, "-XX:NewSize=" + NEW_SIZE,
                "-Xmx" + HEAP_SIZE, "-Xms" + HEAP_SIZE,
                (survivorRatio >= 0 ? "-XX:SurvivorRatio=" + survivorRatio : ""),
                (initRatio >= 0 ? "-XX:InitialSurvivorRatio=" + initRatio : ""),
                (minRatio >= 0 ? "-XX:MinSurvivorRatio=" + minRatio : ""),
                (useAdaptiveSizePolicy ? "-XX:+UseAdaptiveSizePolicy" : "-XX:-UseAdaptiveSizePolicy"),
                SurvivorRatioVerifier.class.getName(),
                Integer.toString(survivorRatio),
                Integer.toString(initRatio),
                Integer.toString(minRatio),
                Boolean.toString(useAdaptiveSizePolicy)
        );
        vmOptions.removeIf((String p) -> p.isEmpty());
        OutputAnalyzer analyzer = GCArguments.executeLimitedTestJava(vmOptions);
        analyzer.shouldHaveExitValue(0);
    }

    /**
     * Class that verifies survivor ratio.
     * Will be executed in tested VM. Checks initial size of eden and survivor paces with alignment.
     */
    public static class SurvivorRatioVerifier {

        public static WhiteBox wb = WhiteBox.getWhiteBox();

        public static final int MAX_ITERATIONS = 10;
        public static final int ARRAY_LENGTH = 10000;
        public static final int CHUNK_SIZE = 10000;

        public static byte garbage[][] = new byte[ARRAY_LENGTH][];

        public static void main(String args[]) throws Exception {
            if (args.length != 4) {
                throw new IllegalArgumentException("Expected 4 args: <survivorRatio> <initRatio> <minRatio> <useAdaptiveSizePolicy>");
            }
            final int survivorRatio = Integer.valueOf(args[0]);
            final int initRatio = Integer.valueOf(args[1]);
            final int minRatio = Integer.valueOf(args[2]);
            final boolean useAdaptiveSizePolicy = Boolean.valueOf(args[3]);

            // we stop testing only here to ensure that JVM will accept
            // both MinSurvivorRatio and InitialSurvivorRatio regardles to GC
            if (GCTypes.YoungGCType.getYoungGCType() != GCTypes.YoungGCType.PSNew) {
                System.out.println("Test is only applicable to Parallel GC");
                return;
            }

            // verify initial survivor ratio
            verifySurvivorRatio(survivorRatio, initRatio, minRatio, useAdaptiveSizePolicy, true);

            // force GC
            AllocationHelper allocator = new AllocationHelper(MAX_ITERATIONS, ARRAY_LENGTH, CHUNK_SIZE,
                    () -> (verifySurvivorRatio(survivorRatio, initRatio, minRatio, useAdaptiveSizePolicy, false)));
            allocator.allocateMemoryAndVerify();
        }

        /**
         * Verify actual survivor ratio.
         *
         * @param survivorRatio value of SurvivorRatio option, omitted if negative
         * @param initRatio value of InitialSurvivorRatio option, omitted if negative
         * @param minRatio value of MinSurvivorRatio option, omitted if negative
         * @param useAdaptiveSizePolicy value of UseAdaptiveSizePolicy option
         * @param verifyInitialRatio true if we are going to verify initial ratio
         */
        public static Void verifySurvivorRatio(int survivorRatio,
                int initRatio,
                int minRatio,
                boolean useAdaptiveSizePolicy,
                boolean verifyInitialRatio) {

            MemoryUsage edenUsage = HeapRegionUsageTool.getEdenUsage();
            MemoryUsage survivorUsage = HeapRegionUsageTool.getSurvivorUsage();

            long alignedNewSize = edenUsage.getMax() + 2 * survivorUsage.getMax();
            long generationAlignment = wb.psHeapGenerationAlignment();

            if (survivorRatio >= 0) {
                // -XX:SurvivorRatio was passed to JVM, actual ratio should be SurvivorRatio + 2
                long expectedSize = HeapRegionUsageTool.alignDown(alignedNewSize / (survivorRatio + 2),
                        generationAlignment);

                if (survivorUsage.getCommitted() != expectedSize) {
                    throw new RuntimeException("Expected survivor size is: " + expectedSize
                            + ", but observed size is: " + survivorUsage.getCommitted());
                }
            } else if (verifyInitialRatio || !useAdaptiveSizePolicy) {
                // In case of initial ratio verification or disabled adaptive size policy
                // ratio should be equal to InitialSurvivorRatio value
                long expectedSize = HeapRegionUsageTool.alignDown(alignedNewSize / initRatio,
                        generationAlignment);
                if (survivorUsage.getCommitted() != expectedSize) {
                    throw new RuntimeException("Expected survivor size is: " + expectedSize
                            + ", but observed size is: " + survivorUsage.getCommitted());
                }
            } else {
                // In any other case actual survivor ratio should not be lower than MinSurvivorRatio
                // or is should be equal to InitialSurvivorRatio
                long expectedMinSize = HeapRegionUsageTool.alignDown(alignedNewSize / minRatio,
                        generationAlignment);
                long expectedInitSize = HeapRegionUsageTool.alignDown(alignedNewSize / initRatio,
                        generationAlignment);
                if (survivorUsage.getCommitted() != expectedInitSize
                        && survivorUsage.getCommitted() < expectedMinSize) {
                    throw new RuntimeException("Expected survivor size should be " + expectedMinSize
                            + " or should be greater then " + expectedMinSize
                            + ", but observer survivor size is " + survivorUsage.getCommitted());
                }
            }
            return null;
        }
    }
}
