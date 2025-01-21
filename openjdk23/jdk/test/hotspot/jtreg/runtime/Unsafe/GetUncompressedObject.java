/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8022853
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run main GetUncompressedObject
 */

import static jdk.test.lib.Asserts.*;

import jdk.internal.misc.Unsafe;

public class GetUncompressedObject {

    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Allocate some memory and fill it with non-zero values.
        final int size = 32;
        final long address = unsafe.allocateMemory(size);
        unsafe.setMemory(address, size, (byte) 0x23);

        // The only thing we can do is check for null-ness.
        // So, store a null somewhere.
        unsafe.putAddress(address + 16, 0);

        Object nullObj = unsafe.getUncompressedObject(address + 16);
        if (nullObj != null) {
            throw new InternalError("should be null");
        }
    }

}
