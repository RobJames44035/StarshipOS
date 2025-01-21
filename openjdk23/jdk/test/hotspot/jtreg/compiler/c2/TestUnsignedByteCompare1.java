/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8253191
 *
 * @library /test/lib
 * @modules java.base/jdk.internal.vm.annotation
 *
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-TieredCompilation compiler.c2.TestUnsignedByteCompare1
 */
package compiler.c2;

import java.lang.invoke.*;
import jdk.internal.vm.annotation.DontInline;
import jdk.test.lib.Asserts;

public class TestUnsignedByteCompare1 {

    @DontInline static boolean testByteGT0(byte[] val) { return (val[0] & mask()) >  0; }
    @DontInline static boolean testByteGE0(byte[] val) { return (val[0] & mask()) >= 0; }
    @DontInline static boolean testByteEQ0(byte[] val) { return (val[0] & mask()) == 0; }
    @DontInline static boolean testByteNE0(byte[] val) { return (val[0] & mask()) != 0; }
    @DontInline static boolean testByteLE0(byte[] val) { return (val[0] & mask()) <= 0; }
    @DontInline static boolean testByteLT0(byte[] val) { return (val[0] & mask()) <  0; }

    static void testValue(byte b) {
        byte[] bs = new byte[] { b };
        Asserts.assertEquals(((b & mask()) >  0), testByteGT0(bs), errorMessage(b, "GT0"));
        Asserts.assertEquals(((b & mask()) >= 0), testByteGE0(bs), errorMessage(b, "GE0"));
        Asserts.assertEquals(((b & mask()) == 0), testByteEQ0(bs), errorMessage(b, "EQ0"));
        Asserts.assertEquals(((b & mask()) != 0), testByteNE0(bs), errorMessage(b, "NE0"));
        Asserts.assertEquals(((b & mask()) <= 0), testByteLE0(bs), errorMessage(b, "LE0"));
        Asserts.assertEquals(((b & mask()) <  0), testByteLT0(bs), errorMessage(b, "LT0"));
    }

    public static void main(String[] args) {
        for (int mask = 0; mask <= 0xFF; mask++) {
            setMask(mask);
            for (int i = 0; i < 20_000; i++) {
                testValue((byte) i);
            }
        }
        System.out.println("TEST PASSED");
    }

    static String errorMessage(byte b, String type) {
        return String.format("%s: val=0x%x mask=0x%x", type, b, mask());
    }

    // Mutable mask as a compile-time constant.

    private static final CallSite     MASK_CS = new MutableCallSite(MethodType.methodType(int.class));
    private static final MethodHandle MASK_MH = MASK_CS.dynamicInvoker();

    static int mask() {
        try {
            return (int) MASK_MH.invokeExact();
        } catch (Throwable t) {
            throw new InternalError(t); // should NOT happen
        }
    }

    static void setMask(int mask) {
        MethodHandle constant = MethodHandles.constant(int.class, mask);
        MASK_CS.setTarget(constant);
    }
}
