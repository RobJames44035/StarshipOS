/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/* @test
 * @summary Test that pinned objects we lost all Java references to keep
 *          the region and contents alive.
 *          This test simulates this behavior using Whitebox/Unsafe methods
 *          and not real native code for simplicity.
 * @requires vm.gc.G1
 * @requires vm.debug
 * @library /test/lib
 * @modules java.base/jdk.internal.misc:+open
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseG1GC
 *      -Xbootclasspath/a:. -XX:+ZapUnusedHeapArea -Xlog:gc,gc+ergo+cset=trace gc.g1.pinnedobjs.TestPinnedObjectContents
 */

package gc.g1.pinnedobjs;

import jdk.internal.misc.Unsafe;

import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.whitebox.WhiteBox;

public class TestPinnedObjectContents {

    private static final jdk.internal.misc.Unsafe unsafe = Unsafe.getUnsafe();
    private static final WhiteBox wb = WhiteBox.getWhiteBox();

    public static long pinAndGetAddress(Object o) {
        wb.pinObject(o);
        return wb.getObjectAddress(o);
    }

    public static void main(String[] args) throws Exception {
        // Remove garbage from VM initialization.
        wb.fullGC();

        // Allocate to-be pinned object and fill with "random" data.
        final int ArraySize = 100;
        int[] o = new int[ArraySize];
        for (int i = 0; i < o.length; i++) {
            o[i] = i;
        }

        Asserts.assertTrue(!wb.isObjectInOldGen(o), "should not be in old gen already");

        // Remember memory offsets.
        long baseOffset = unsafe.arrayBaseOffset(o.getClass());
        long indexScale = unsafe.arrayIndexScale(o.getClass());
        long address = pinAndGetAddress(o);

        o = null; // And forget the (Java) reference to the int array.

        // Do garbage collections to zap the data surrounding the "dead" object.
        wb.youngGC();
        wb.youngGC();

        for (int i = 0; i < ArraySize; i++) {
            int actual = unsafe.getInt(null, address + baseOffset + i * indexScale);
            if (actual != i) {
                Asserts.fail("Pinned array at offset " + i + " should contain the value " + i + " but is " + actual);
            }
        }
    }
}

