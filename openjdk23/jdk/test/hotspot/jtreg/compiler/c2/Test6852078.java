/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6852078
 * @summary Disable SuperWord optimization for unsafe read/write
 * @modules java.corba/com.sun.corba.se.impl.encoding
 *          java.corba/com.sun.jndi.toolkit.corba
 *
 * @ignore 8194310
 * @run main compiler.c2.Test6852078
 */

package compiler.c2;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import com.sun.jndi.toolkit.corba.CorbaUtils;

import java.nio.ByteBuffer;
import java.util.Hashtable;

public class Test6852078 {

    public Test6852078(String [] args) {

        int capacity = 128;
        ByteBuffer bb = ByteBuffer.allocateDirect(capacity);
        ByteBufferWithInfo bbwi = new ByteBufferWithInfo( CorbaUtils.getOrb(null, -1, new Hashtable()), bb);
        byte[] tmpBuf;
        tmpBuf = new byte[bbwi.buflen];

        for (int i = 0; i < capacity; i++)
            tmpBuf[i] = bbwi.byteBuffer.get(i);
    }

    public static void main(String [] args) {
        long start = System.currentTimeMillis();
        for (int i=0; i<2000; i++) {
            // To protect slow systems from test-too-long timeouts
            if ((i > 100) && ((System.currentTimeMillis() - start) > 100000))
               break;
            Test6852078 t = new Test6852078(args);
        }
    }
}
