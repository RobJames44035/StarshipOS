/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc;

/* @test
 * @requires vm.gc != "Shenandoah" | vm.opt.ShenandoahGCMode != "iu"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *      -Xbootclasspath/a:.
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      gc.TestReferenceRefersToDuringConcMark
 */

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import jdk.test.whitebox.WhiteBox;

public class TestReferenceRefersToDuringConcMark {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    private static volatile Object testObject = null;

    private static WeakReference<Object> testWeak = null;

    private static void setup() {
        testObject = new Object();
        testWeak = new WeakReference<Object>(testObject);
    }

    private static void gcUntilOld(Object o) throws Exception {
        if (!WB.isObjectInOldGen(o)) {
            WB.fullGC();
            if (!WB.isObjectInOldGen(o)) {
                fail("object not promoted by full gc");
            }
        }
    }

    private static void gcUntilOld() throws Exception {
        gcUntilOld(testObject);
        gcUntilOld(testWeak);
    }

    private static void fail(String msg) throws Exception {
        throw new RuntimeException(msg);
    }

    private static void expectNotCleared(Reference<Object> ref,
                                         String which) throws Exception {
        if (ref.refersTo(null)) {
            fail("expected " + which + " to not be cleared");
        }
    }

    private static void expectValue(Reference<Object> ref,
                                    Object value,
                                    String which) throws Exception {
        expectNotCleared(ref, which);
        if (!ref.refersTo(value)) {
            fail(which + " doesn't refer to expected value");
        }
    }

    private static void checkInitialStates() throws Exception {
        expectValue(testWeak, testObject, "testWeak");
    }

    private static void discardStrongReferences() {
        testObject = null;
    }

    private static void testConcurrentCollection() throws Exception {
        setup();
        gcUntilOld();

        WB.concurrentGCAcquireControl();
        try {
            checkInitialStates();

            discardStrongReferences();

            WB.concurrentGCRunTo(WB.BEFORE_MARKING_COMPLETED);

            // For most collectors - the configurations tested here -,
            // calling get() will keep testObject alive.
            if (testWeak.get() == null) {
                fail("testWeak unexpectedly == null");
            }

            WB.concurrentGCRunToIdle();

            expectNotCleared(testWeak, "testWeak");
        } finally {
            WB.concurrentGCReleaseControl();
        }
    }

    public static void main(String[] args) throws Exception {
        if (WB.supportsConcurrentGCBreakpoints()) {
            testConcurrentCollection();
        }
    }
}
