/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @summary Test view buffer bulk operations for large buffers.
 * @bug 4463011
 *
 * @modules java.base/jdk.internal.misc
 * @build Basic
 * @run main CopyDirectMemory
 */

import java.nio.*;

public class CopyDirectMemory
    extends Basic
{
    public static void main(String [] args) {
        CopyDirectByteMemory.test();
        CopyDirectCharMemory.test();
        CopyDirectShortMemory.test();
        CopyDirectIntMemory.test();
        CopyDirectLongMemory.test();
        CopyDirectFloatMemory.test();
        CopyDirectDoubleMemory.test();
    }
}
