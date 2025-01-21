/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8310297
 * @summary Negative fields shorter than an int may not be sign extended in cpool
 *          but should in static field initializers. javac gives an error for this but
 *          not other sources of bytecodes.  With checked_cast<> this crashes.
 * @compile CompatByteFieldInit.jasm
 * @run main ByteFieldInitTest
 */

import java.lang.reflect.Field;

public class ByteFieldInitTest {

    final static byte b = -128;     // compare with 0x80
    final static short s = -32768;  // compare with 0x8000

    public static void main(java.lang.String[] unused) throws Exception {
        // javac is smart enough to complain about the other class's byte and short values when referred
        // to directly.  With checked_cast<> loading this class should fail.
        Class<?> c = Class.forName("CompatByteFieldInit");
        Field cb = c.getDeclaredField("b");
        Field cs = c.getDeclaredField("s");
        if (b != cb.getByte(null) || s != cs.getShort(null)) {
            throw new RuntimeException("constant pool init not compatible " + cb.getByte(null) + " " + cs.getShort(null));
        } else {
            System.out.println("Fields are same test passed");
        }
    }
}


