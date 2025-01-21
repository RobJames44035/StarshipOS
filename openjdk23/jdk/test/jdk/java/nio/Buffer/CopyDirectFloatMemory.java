/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

// -- This file was mechanically generated: Do not edit! -- //

import java.nio.*;

public class CopyDirectFloatMemory
    extends CopyDirectMemory
{
    private static void init(FloatBuffer b) {
        int n = b.capacity();
        b.clear();
        for (int i = 0; i < n; i++)
            b.put(i, (float)ic(i));
        b.limit(n);
        b.position(0);
    }

    private static void init(float [] a) {
        for (int i = 0; i < a.length; i++)
            a[i] = (float)ic(i + 1);
    }

    public static void test() {



        ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 + 1024);
        FloatBuffer b = bb.asFloatBuffer();

        init(b);
        float [] a = new float[b.capacity()];
        init(a);

        // copyFromFloatArray (a -> b)
        b.put(a);
        for (int i = 0; i < a.length; i++)
            ck(b, b.get(i), (float)ic(i + 1));

        // copyToFloatArray (b -> a)
        init(b);
        init(a);
        b.get(a);
        for (int i = 0; i < a.length; i++)
            if (a[i] != b.get(i))
                fail("Copy failed at " + i + ": '"
                     + a[i] + "' != '" + b.get(i) + "'");
    }
}
