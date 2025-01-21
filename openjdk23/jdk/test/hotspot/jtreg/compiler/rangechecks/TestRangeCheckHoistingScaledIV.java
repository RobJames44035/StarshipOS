/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8289996
 * @summary Test range check hoisting for some scaled iv at array index
 * @library /test/lib /
 * @requires vm.flagless
 * @requires vm.debug & vm.compiler2.enabled & (os.simpleArch == "x64" | os.arch == "aarch64")
 * @modules jdk.incubator.vector
 * @run main/othervm compiler.rangechecks.TestRangeCheckHoistingScaledIV
 */

package compiler.rangechecks;

import java.lang.foreign.MemorySegment;
import java.nio.ByteOrder;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorSpecies;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestRangeCheckHoistingScaledIV {

    // Inner class for test loops
    class Launcher {
        private static final int SIZE = 16000;
        private static final VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_64;
        private static final ByteOrder ORDER = ByteOrder.nativeOrder();

        private static byte[] ta = new byte[SIZE];
        private static byte[] tb = new byte[SIZE];

        private static MemorySegment sa = MemorySegment.ofArray(ta);
        private static MemorySegment sb = MemorySegment.ofArray(tb);

        private static int count = 789;

        // Normal array accesses with int range checks
        public static void scaledIntIV() {
            for (int i = 0; i < count; i += 2) {
                tb[7 * i] = ta[3 * i];
            }
        }

        // Memory segment accesses with long range checks
        public static void scaledLongIV() {
            for (long l = 0; l < count; l += 64) {
                ByteVector v = ByteVector.fromMemorySegment(SPECIES, sa, l * 6, ORDER);
                v.intoMemorySegment(sb, l * 15, ORDER);
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 20000; i++) {
                scaledIntIV();
                scaledLongIV();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "--add-modules", "jdk.incubator.vector",
                "-Xbatch", "-XX:+TraceLoopPredicate", Launcher.class.getName());
        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);
        analyzer.outputTo(System.out);

        // Check if int range checks are hoisted
        analyzer.stdoutShouldContain("rc_predicate init * 3");
        analyzer.stdoutShouldContain("rc_predicate init * 7");

        // Check if long range checks are hoisted
        analyzer.stdoutShouldContain("rc_predicate init * 6");
        analyzer.stdoutShouldContain("rc_predicate init * 15");
    }
}
