/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4660944
 * @summary Test DatagramChannel's receive after close
 */

import java.nio.*;
import java.nio.channels.*;
import java.net.*;

public class Receive {
    public static void main(String args[]) throws Exception {
        ByteBuffer bb = ByteBuffer.allocate(10);
        DatagramChannel dc1 = DatagramChannel.open();
        dc1.close();
        try {
            dc1.receive(bb);
            throw new Exception("Receive on closed DC did not throw");
        } catch (ClosedChannelException cce) {
            // Correct result
        }
    }
}
