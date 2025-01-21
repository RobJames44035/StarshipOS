/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* Type-specific source code for unit test
 *
 * Regenerate the OrderX classes via genOrder.sh whenever this file changes.
 * We check in the generated source files so that the test tree can be used
 * independently of the rest of the source tree.
 */

// -- This file was mechanically generated: Do not edit! -- //

import java.nio.*;

public class OrderLong extends Order {
    private static void ckLongBuffer(LongBuffer buf, ByteOrder expected) {
        ck(buf.asReadOnlyBuffer().order(), expected);
        ck(buf.duplicate().order(), expected);
        ck(buf.slice().order(), expected);




    }

    static void ckLongBuffer() {
        long[] array = new long[LENGTH];
        LongBuffer buf = LongBuffer.wrap(array);
        ck(buf.order(), nord);
        ckLongBuffer(buf, nord);

        buf = LongBuffer.wrap(array, LENGTH/2, LENGTH/2);
        ck(buf.order(), nord);
        ckLongBuffer(buf, nord);

        buf = LongBuffer.allocate(LENGTH);
        ck(buf.order(), nord);
        ckLongBuffer(buf, nord);





















    }
}

