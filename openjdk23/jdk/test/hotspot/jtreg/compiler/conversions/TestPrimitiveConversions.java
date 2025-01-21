/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package compiler.conversions;

import jdk.test.lib.Asserts;

/*
 * @test
 * @bug 8234617
 * @summary Test implicit narrowing conversion of primivite values at putfield.
 * @library /test/lib /
 * @compile Conversion.jasm
 * @run main/othervm -Xbatch -XX:CompileCommand=dontinline,compiler.conversions.Conversion::*
 *                   compiler.conversions.TestPrimitiveConversions
 */
public class TestPrimitiveConversions {

    public static void main(String[] args) {
        Conversion conv = new Conversion();
        for (int i = 0; i < 100_000; ++i) {
            int res = conv.testBooleanConst();
            Asserts.assertEquals(res, 0);
            res = conv.testBoolean(2); // 2^1 (maximum boolean value is 1)
            Asserts.assertEquals(res, 0);
            res = conv.testByteConst();
            Asserts.assertEquals(res, 0);
            res = conv.testByte(256); // 2^8 (maximum byte value is 2^7-1)
            Asserts.assertEquals(res, 0);
            res = conv.testCharConst();
            Asserts.assertEquals(res, 0);
            res = conv.testChar(131072); // 2^17 (maximum char value is 2^16-1)
            Asserts.assertEquals(res, 0);
            res = conv.testShortConst();
            Asserts.assertEquals(res, 0);
            res = conv.testShort(65536); // 2^16 (maximum short value is 2^15-1)
            Asserts.assertEquals(res, 0);
        }
    }
}
