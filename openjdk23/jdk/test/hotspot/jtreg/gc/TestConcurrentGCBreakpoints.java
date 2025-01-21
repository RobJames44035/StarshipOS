/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc;

/*
 * @test TestConcurrentGCBreakpoints
 * @summary Test of WhiteBox concurrent GC control.
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *   -Xbootclasspath/a:.
 *   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *   gc.TestConcurrentGCBreakpoints
 */

import jdk.test.whitebox.WhiteBox;
import jdk.test.whitebox.gc.GC;

public class TestConcurrentGCBreakpoints {

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    private static void testG1SpecificBreakpoints() {
        WB.concurrentGCRunTo(WB.G1_AFTER_REBUILD_STARTED);
        WB.concurrentGCRunTo(WB.G1_BEFORE_REBUILD_COMPLETED);
        WB.concurrentGCRunTo(WB.G1_AFTER_CLEANUP_STARTED);
        WB.concurrentGCRunTo(WB.G1_BEFORE_CLEANUP_COMPLETED);
    }

    // All testN() assume initial state is idle, and restore that state.

    // Step through the common breakpoints.
    private static void testSimpleCycle() throws Exception {
        System.out.println("testSimpleCycle");
        try {
            // Run one cycle.
            WB.concurrentGCRunTo(WB.AFTER_MARKING_STARTED);
            WB.concurrentGCRunTo(WB.BEFORE_MARKING_COMPLETED);
            if (GC.G1.isSelected()) {
                testG1SpecificBreakpoints();
            }
            WB.concurrentGCRunToIdle();
            // Run a second cycle.
            WB.concurrentGCRunTo(WB.AFTER_MARKING_STARTED);
            WB.concurrentGCRunTo(WB.BEFORE_MARKING_COMPLETED);
            if (GC.G1.isSelected()) {
                testG1SpecificBreakpoints();
            }
            WB.concurrentGCRunToIdle();
        } finally {
            WB.concurrentGCRunToIdle();
        }
    }

    // Verify attempted wraparound detected and reported.
    private static void testEndBeforeBreakpointError() throws Exception {
        System.out.println("testEndBeforeBreakpointError");
        try {
            WB.concurrentGCRunTo(WB.BEFORE_MARKING_COMPLETED);
            try {
                WB.concurrentGCRunTo(WB.AFTER_MARKING_STARTED);
            } catch (IllegalStateException e) {
                // Reached end of cycle before desired breakpoint.
            }
        } finally {
            WB.concurrentGCRunToIdle();
        }
    }

    // Verify attempted wraparound detected and reported without throw.
    private static void testEndBeforeBreakpoint() throws Exception {
        System.out.println("testEndBeforeBreakpoint");
        try {
            WB.concurrentGCRunTo(WB.BEFORE_MARKING_COMPLETED);
            if (WB.concurrentGCRunTo(WB.AFTER_MARKING_STARTED, false)) {
                throw new RuntimeException("Unexpected wraparound");
            }
        } finally {
            WB.concurrentGCRunToIdle();
        }
    }

    private static void testUnknownBreakpoint() throws Exception {
        System.out.println("testUnknownBreakpoint");
        try {
            if (WB.concurrentGCRunTo("UNKNOWN BREAKPOINT", false)) {
                throw new RuntimeException("RunTo UNKNOWN BREAKPOINT");
            }
        } finally {
            WB.concurrentGCRunToIdle();
        }
    }

    private static void test() throws Exception {
        try {
            System.out.println("taking control");
            WB.concurrentGCAcquireControl();
            testSimpleCycle();
            testEndBeforeBreakpointError();
            testEndBeforeBreakpoint();
            testUnknownBreakpoint();
        } finally {
            System.out.println("releasing control");
            WB.concurrentGCReleaseControl();
        }
    }

    private static boolean expectSupported() {
        return GC.G1.isSelected() ||
               GC.Z.isSelected() ||
               GC.Shenandoah.isSelected();
    }

    private static boolean expectUnsupported() {
        return GC.Serial.isSelected() ||
               GC.Parallel.isSelected() ||
               GC.Epsilon.isSelected();
    }

    public static void main(String[] args) throws Exception {
        boolean supported = WB.supportsConcurrentGCBreakpoints();
        if (expectSupported()) {
            if (supported) {
                test();
            } else {
                throw new RuntimeException("Expected support");
            }
        } else if (expectUnsupported()) {
            if (supported) {
                throw new RuntimeException("Unexpected support");
            }
        } else {
            throw new RuntimeException("Unknown GC");
        }
    }
}
