/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t019.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t019.t019
 */

package jit.t.t019;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_baload, opc_bastore,
// opc_caload, opc_castore,
// opc_saload, opc_sastore

public class t019
{
    public static final GoldChecker goldChecker = new GoldChecker( "t019" );

    public static void main(String argv[])
    {
        byte b[] = new byte[4];
        char c[] = new char[4];
        short s[] = new short[4];

        b[0] = 0; b[1] = 127; b[2] = (byte) 128; b[3] = (byte) 255;
        for(int i=0; i<4; i+=1)
            t019.goldChecker.println("b[" + i + "] == " + b[i]);

        t019.goldChecker.println();
        c[0] = 0; c[1] = 32767; c[2] = 32768; c[3] = 65535;
        for(int i=0; i<4; i+=1)
            t019.goldChecker.println("(int) c[" + i + "] == " + (int) c[i]);

        t019.goldChecker.println();
        s[0] = 0; s[1] = 32767; s[2] = (short) 32768; s[3] = (short) 65535;
        for(int i=0; i<4; i+=1)
            t019.goldChecker.println("s[" + i + "] == " + s[i]);
        t019.goldChecker.check();
    }
}
