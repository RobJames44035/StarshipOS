/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 5046110
 * @summary Ensure that direct memory can be unreserved
 *          as the reserving thread sleeps
 *
 * @run main/othervm -Xmx16M Chew
 */

import java.nio.*;


public class Chew {

    public static void main(String[] args) {
        for (int i = 0; i < 64; i++)
            ByteBuffer.allocateDirect(1 << 20);
    }

}
