/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4630914
 * @summary Should only be one PUE even if multiple ICMPs were received
 */
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class ThereCanBeOnlyOne {

    static void doTest(InetAddress ia, boolean testSend) throws Exception {
        DatagramChannel dc1 = DatagramChannel.open();
        dc1.socket().bind((SocketAddress)null);
        int port = dc1.socket().getLocalPort();
        InetSocketAddress isa = new InetSocketAddress(ia, port);
        dc1.connect(isa);

        ByteBuffer bb = ByteBuffer.allocateDirect(512);
        bb.put("hello".getBytes());
        bb.flip();

        /*
         * Send a bunch of packets to the destination
         */
        int outstanding = 0;
        for (int i=0; i<20; i++) {
            try {
                bb.rewind();
                dc1.write(bb);
                outstanding++;
            } catch (PortUnreachableException e) {
                /* PUE throw => assume none outstanding now */
                outstanding = 0;
            }
            if (outstanding > 1) {
                break;
            }
        }
        if (outstanding < 1) {
            System.err.println("Insufficient exceptions outstanding - Test Skipped (Passed).");
            dc1.close();
            return;
        }

        /*
         * Give time for ICMP port unreachables to return
         */
        Thread.currentThread().sleep(5000);

        /*
         * The next send or receive should cause a PUE to be thrown
         */
        boolean gotPUE = false;
        boolean gotTimeout = false;
        dc1.configureBlocking(false);

        try {
            if (testSend) {
                bb.rewind();
                dc1.write(bb);
            } else {
                bb.clear();
                dc1.receive(bb);
            }
        } catch (PortUnreachableException pue) {
            System.err.println("Got one PUE...");
            gotPUE = true;
        }

        /*
         * The next receive should not get another PUE
         */
        if (gotPUE) {
            try {
                dc1.receive(bb);
            } catch (PortUnreachableException pue) {
                throw new Exception("PUs should have been consumed");
            }
        } else {
            // packets discarded. Okay
        }

        dc1.close();
    }


    public static void main(String args[]) throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        doTest(ia, true);
        doTest(ia, false);
    }

}
