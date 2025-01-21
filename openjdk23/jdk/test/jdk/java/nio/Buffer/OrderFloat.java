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

public class OrderFloat extends Order {
    private static void ckFloatBuffer(FloatBuffer buf, ByteOrder expected) {
        ck(buf.asReadOnlyBuffer().order(), expected);
        ck(buf.duplicate().order(), expected);
        ck(buf.slice().order(), expected);




    }

    static void ckFloatBuffer() {
        float[] array = new float[LENGTH];
        FloatBuffer buf = FloatBuffer.wrap(array);
        ck(buf.order(), nord);
        ckFloatBuffer(buf, nord);

        buf = FloatBuffer.wrap(array, LENGTH/2, LENGTH/2);
        ck(buf.order(), nord);
        ckFloatBuffer(buf, nord);

        buf = FloatBuffer.allocate(LENGTH);
        ck(buf.order(), nord);
        ckFloatBuffer(buf, nord);





















    }
}

