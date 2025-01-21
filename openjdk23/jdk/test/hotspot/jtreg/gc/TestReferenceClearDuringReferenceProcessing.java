/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc;

/* @test id=Shenandoah
 * @bug 8256517
 * @requires vm.gc.Shenandoah
 * @requires vm.gc != "null"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *      -Xbootclasspath/a:.
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      gc.TestReferenceClearDuringReferenceProcessing
 */

/* @test id=Z
 * @bug 8256517
 * @requires vm.gc.Z
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *      -Xbootclasspath/a:.
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseZGC
 *      gc.TestReferenceClearDuringReferenceProcessing
 */

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import jdk.test.whitebox.WhiteBox;

public class TestReferenceClearDuringReferenceProcessing {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    private static Object testObject = new Object();
    private static final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
    private static final WeakReference<Object> ref = new WeakReference<Object>(testObject, queue);

    private static final long TIMEOUT = 10000; // 10sec in millis

    private static void test() {
        while (!WB.isObjectInOldGen(testObject) ||
               !WB.isObjectInOldGen(ref)) {
            WB.fullGC();
        }

        WB.concurrentGCAcquireControl();
        try {
            testObject = null;
            WB.concurrentGCRunTo(WB.AFTER_CONCURRENT_REFERENCE_PROCESSING_STARTED);
            if (!ref.refersTo(null)) {
                throw new RuntimeException("ref not apparently cleared");
            }

            ref.clear();

            WB.concurrentGCRunToIdle();

            Reference<? extends Object> enqueued = null;

            try {
                enqueued = queue.remove(TIMEOUT);
            } catch (InterruptedException e) {
                throw new RuntimeException("queue.remove interrupted");
            }
            if (enqueued == null) {
                throw new RuntimeException("ref not enqueued");
            } else if (enqueued != ref) {
                throw new RuntimeException("some other ref enqeueued");
            }
        } finally {
            WB.concurrentGCReleaseControl();
        }
    }

    public static void main(String[] args) {
        if (WB.supportsConcurrentGCBreakpoints()) {
            // Also requires concurrent reference processing, but we
            // don't have a predicate for that.  For now,
            // use @requires and CLA to limit the applicable collectors.
            test();
        }
    }
}
