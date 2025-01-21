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

public class OrderChar extends Order {
    private static void ckCharBuffer(CharBuffer buf, ByteOrder expected) {
        ck(buf.asReadOnlyBuffer().order(), expected);
        ck(buf.duplicate().order(), expected);
        ck(buf.slice().order(), expected);

        ck(buf.subSequence(buf.position(), buf.remaining()).order(), expected);
        ck(buf.subSequence(buf.position(), buf.position()).order(), expected);

    }

    static void ckCharBuffer() {
        char[] array = new char[LENGTH];
        CharBuffer buf = CharBuffer.wrap(array);
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = CharBuffer.wrap(array, LENGTH/2, LENGTH/2);
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = CharBuffer.allocate(LENGTH);
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = CharBuffer.wrap("abcdefghijk");
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = CharBuffer.wrap("abcdefghijk", 0, 5);
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = CharBuffer.wrap(array).subSequence(0, LENGTH);
        ck(buf.order(), nord);
        ckCharBuffer(buf, nord);

        buf = ByteBuffer.wrap(new byte[LENGTH]).asCharBuffer();
        ck(buf.order(), be);
        ckCharBuffer(buf, be);

        buf = ByteBuffer.wrap(new byte[LENGTH]).order(le).asCharBuffer();
        ck(buf.order(), le);
        ckCharBuffer(buf, le);

    }
}

