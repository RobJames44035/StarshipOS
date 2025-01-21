/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test SanityTest
 * @summary Sanity check of String.intern() & GC
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI SanityTest
 */

import java.util.*;
import jdk.test.whitebox.WhiteBox;


public class SanityTest {
    public static Object tmp;
    public static void main(String... args) {

        WhiteBox wb = WhiteBox.getWhiteBox();
        StringBuilder sb = new StringBuilder();
        sb.append("1234x"); sb.append("x56789");
        String str = sb.toString();

        if (wb.isInStringTable(str)) {
            throw new RuntimeException("String " + str + " is already interned");
        }
        str.intern();
        if (!wb.isInStringTable(str)) {
            throw new RuntimeException("String " + str + " is not interned");
        }
        str = sb.toString();
        wb.fullGC();
        if (wb.isInStringTable(str)) {
            throw new RuntimeException("String " + str + " is in StringTable even after GC");
        }
    }
}
