/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4321350
 * @bug 4516522
 * @summary Check that InetAddress.getByName() throws UHE with dotted
 *          IP address with octets out of range (Windows specific bug)
 *         or when bad IPv6 Litterals addresses are passed.
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BadDottedIPAddress {

    public static void main(String args[]) throws Exception {

        String host = "999.999.999.999";

        boolean exc_thrown = false;
        try {
            InetAddress ia = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            exc_thrown = true;
        }

        if (!exc_thrown) {
            throw new Exception("UnknownHostException was not thrown for: "
                + host);
        }

        host = "[]";
        exc_thrown = false;
        try {
            InetAddress ia = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            exc_thrown = true;
        } catch (Exception e) {
        }

        if (!exc_thrown) {
            throw new Exception("UnknownHostException was not thrown for: "
                + host);
        }

        host = "[127.0.0.1]";
        exc_thrown = false;
        try {
            InetAddress ia = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            exc_thrown = true;
        } catch (Exception e) {
        }

        if (!exc_thrown) {
            throw new Exception("UnknownHostException was not thrown for: "
                + host);
        }

        host = "[localhost]";
        exc_thrown = false;
        try {
            InetAddress ia = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            exc_thrown = true;
        } catch (Exception e) {
        }

        if (!exc_thrown) {
            throw new Exception("UnknownHostException was not thrown for: "
                + host);
        }
    }
}
