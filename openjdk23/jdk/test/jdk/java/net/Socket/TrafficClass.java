/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4511783
 * @library /test/lib
 * @summary Test that setTrafficClass/getTraffiClass don't
 *          throw an exception
 * @run main TrafficClass
 * @run main/othervm -Djava.net.preferIPv4Stack=true TrafficClass
 */
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import jdk.test.lib.net.IPSupport;

public class TrafficClass {

    static final int IPTOS_RELIABILITY = 0x4;

    static int failures = 0;

    static void testDatagramSocket(DatagramSocket s) {
        try {
            s.setTrafficClass( IPTOS_RELIABILITY );
            int tc = s.getTrafficClass();
        } catch (Exception e) {
            failures++;
            System.err.println("testDatagramSocket failed: " + e);
        }
    }

    static void testSocket(Socket s) {
        try {
            s.setTrafficClass(IPTOS_RELIABILITY);
            int tc = s.getTrafficClass();
        } catch (Exception e) {
            failures++;
            System.err.println("testSocket failed: " + e);
        }

    }

    public static void main(String args[]) throws Exception {
        IPSupport.throwSkippedExceptionIfNonOperational();

        DatagramSocket ds = new DatagramSocket();
        testDatagramSocket(ds);

        DatagramChannel dc = DatagramChannel.open();
        testDatagramSocket(dc.socket());

        Socket s = new Socket();
        testSocket(s);

        SocketChannel sc = SocketChannel.open();
        testSocket(sc.socket());

        if (failures > 0) {
            throw new Exception(failures + " sub-test(s) failed - " +
                "see log for details.");
        }
    }

}
