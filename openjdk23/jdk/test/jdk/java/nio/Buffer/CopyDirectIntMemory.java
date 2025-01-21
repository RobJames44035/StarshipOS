/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

// -- This file was mechanically generated: Do not edit! -- //

import java.nio.*;

public class CopyDirectIntMemory
    extends CopyDirectMemory
{
    private static void init(IntBuffer b) {
        int n = b.capacity();
        b.clear();
        for (int i = 0; i < n; i++)
            b.put(i, (int)ic(i));
        b.limit(n);
        b.position(0);
    }

    private static void init(int [] a) {
        for (int i = 0; i < a.length; i++)
            a[i] = (int)ic(i + 1);
    }

    public static void test() {



        ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 + 1024);
        IntBuffer b = bb.asIntBuffer();

        init(b);
        int [] a = new int[b.capacity()];
        init(a);

        // copyFromIntArray (a -> b)
        b.put(a);
        for (int i = 0; i < a.length; i++)
            ck(b, b.get(i), (int)ic(i + 1));

        // copyToIntArray (b -> a)
        init(b);
        init(a);
        b.get(a);
        for (int i = 0; i < a.length; i++)
            if (a[i] != b.get(i))
                fail("Copy failed at " + i + ": '"
                     + a[i] + "' != '" + b.get(i) + "'");
    }
}
