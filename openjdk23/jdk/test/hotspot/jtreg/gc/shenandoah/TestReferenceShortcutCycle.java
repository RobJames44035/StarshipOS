/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc.shenandoah;

/* @test id=satb-100
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @modules java.base
 * @run main jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *      -Xbootclasspath/a:.
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=satb -XX:ShenandoahGarbageThreshold=100 -Xmx100m
 *      gc.shenandoah.TestReferenceShortcutCycle
 */

/* @test id=generational-100
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @modules java.base
 * @run main jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *      -Xbootclasspath/a:.
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:ShenandoahGarbageThreshold=100 -Xmx100m
 *      gc.shenandoah.TestReferenceShortcutCycle
 */

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import jdk.test.whitebox.WhiteBox;

public class TestReferenceShortcutCycle {
    private static final int NUM_ITEMS = 100000;

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    private static final class TestObject {
        public final int value;

        public TestObject(int value) {
            this.value = value;
        }
    }

    private static WeakReference[] refs;

    private static void setup() {
        refs = new WeakReference[NUM_ITEMS];
        for (int i = 0; i < NUM_ITEMS; i++) {
            refs[i] = new WeakReference<>(new TestObject(i));
        }
    }

    private static void fail(String msg) throws Exception {
        throw new RuntimeException(msg);
    }

    private static void testConcurrentCollection() throws Exception {
        setup();
        WB.concurrentGCAcquireControl();
        try {
            WB.concurrentGCRunToIdle();
            WB.concurrentGCRunTo(WB.AFTER_CONCURRENT_REFERENCE_PROCESSING_STARTED);
            for (int i = 0; i < NUM_ITEMS; i++) {
                if (refs[i].get() != null) {
                    fail("resurrected referent");
                }
            }
        } finally {
            WB.concurrentGCReleaseControl();
        }
    }
    public static void main(String[] args) throws Exception {
        testConcurrentCollection();
    }
}
