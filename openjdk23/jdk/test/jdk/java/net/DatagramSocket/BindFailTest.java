/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/**
 * @test 1.1 98/08/06
 * @bug 4326262
 * @summary  test that a bind failure doesn't consume an fd until
 *           the datagramsocket finalizer runs.
 */
import java.net.DatagramSocket;
import java.net.BindException;
import java.net.SocketException;

public class BindFailTest {

    public static void main(String args[]) throws Exception {
        DatagramSocket s = new DatagramSocket();
        int port = s.getLocalPort();

        for (int i=0; i<32000; i++) {
            try {
                DatagramSocket s2 = new DatagramSocket(port);
            } catch (BindException e) {
            }
        }
    }
}
