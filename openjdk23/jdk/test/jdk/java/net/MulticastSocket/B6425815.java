/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 *
 * @bug 6425815
 *
 * @summary java.net.MulticastSocket.setTimeToLive(255) reports 'Socket closed' (WinXP, IPv6)
 *
 */
import java.net.*;
import java.io.IOException;

public class B6425815 {
    public static void main(String[] args) throws Exception {
        InetAddress ia;
        MulticastSocket ms;

        try {
            ia = InetAddress.getByName("::1");
            ms = new MulticastSocket(new InetSocketAddress(ia, 1234));
        } catch (Exception e) {
            // If this fails, it means the system doesn't have IPV6
            // support, therefore this test shouldn't be run.
            ia = null;
            ms = null;
        }
        if (ms != null) {
            ms.setTimeToLive(254);
            if (ms.getTimeToLive() != 254) {
                throw new RuntimeException("time to live is incorrect!");
            }
            ms.close();
        }
    }
}
