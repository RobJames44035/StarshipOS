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

public class OrderInt extends Order {
    private static void ckIntBuffer(IntBuffer buf, ByteOrder expected) {
        ck(buf.asReadOnlyBuffer().order(), expected);
        ck(buf.duplicate().order(), expected);
        ck(buf.slice().order(), expected);




    }

    static void ckIntBuffer() {
        int[] array = new int[LENGTH];
        IntBuffer buf = IntBuffer.wrap(array);
        ck(buf.order(), nord);
        ckIntBuffer(buf, nord);

        buf = IntBuffer.wrap(array, LENGTH/2, LENGTH/2);
        ck(buf.order(), nord);
        ckIntBuffer(buf, nord);

        buf = IntBuffer.allocate(LENGTH);
        ck(buf.order(), nord);
        ckIntBuffer(buf, nord);





















    }
}

