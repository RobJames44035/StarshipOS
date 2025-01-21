/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t036.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t036.t036
 */

package jit.t.t036;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_int2byte, opc_int2char, opc_int2short

public class t036
{
    public static final GoldChecker goldChecker = new GoldChecker( "t036" );

    static void show(int i, byte b, char c, short s)
    {
        t036.goldChecker.println();
        t036.goldChecker.println(i);
        t036.goldChecker.println(b);
        t036.goldChecker.println((int) c);
        t036.goldChecker.println(s);
    }

    public static void main(String argv[])
    {
        int i;
        char c;
        short s;
        byte b;

        i = 39; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -1; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 127; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 128; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -128; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -129; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 32767; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 32768; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -32768; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -32769; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 65535; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = 65536; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -65536; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
        i = -65537; b = (byte) i; c = (char) i; s = (short) i; show(i,b,c,s);
                                                               t036.goldChecker.check();
    }
}
