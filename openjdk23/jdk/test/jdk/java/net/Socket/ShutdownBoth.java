/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4425485
 * @summary Check that shutdownInput followed by shutdownOutput
 *          doesn't throw an exception.
 */
import java.net.*;

public class ShutdownBoth {

    public static void main(String args[]) throws Exception {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ServerSocket ss = new ServerSocket(0, 50, loopback);
        Socket s1 = new Socket(ss.getInetAddress(), ss.getLocalPort());
        Socket s2 = ss.accept();

        try {
            s1.shutdownInput();
            s1.shutdownOutput();            // failed b55
        } finally {
            s1.close();
            s2.close();
            ss.close();
        }
    }

}
