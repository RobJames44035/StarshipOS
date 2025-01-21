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

public class OrderDouble extends Order {
    private static void ckDoubleBuffer(DoubleBuffer buf, ByteOrder expected) {
        ck(buf.asReadOnlyBuffer().order(), expected);
        ck(buf.duplicate().order(), expected);
        ck(buf.slice().order(), expected);




    }

    static void ckDoubleBuffer() {
        double[] array = new double[LENGTH];
        DoubleBuffer buf = DoubleBuffer.wrap(array);
        ck(buf.order(), nord);
        ckDoubleBuffer(buf, nord);

        buf = DoubleBuffer.wrap(array, LENGTH/2, LENGTH/2);
        ck(buf.order(), nord);
        ckDoubleBuffer(buf, nord);

        buf = DoubleBuffer.allocate(LENGTH);
        ck(buf.order(), nord);
        ckDoubleBuffer(buf, nord);





















    }
}

