/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4921888
 * @summary Tests that we do not get a NullPointException.
 * @author Shanliang JIANG
 *
 * @run clean CloseFailedClientTest
 * @run build CloseFailedClientTest
 * @run main CloseFailedClientTest
 */

import java.net.MalformedURLException;
import java.io.IOException;

import javax.management.*;
import javax.management.remote.*;

/**
 * Try to connect a client to a no-existing or not started server,
 * expected to receive an IOException.
 *
 */
public class CloseFailedClientTest {
    /**
     * we use a fix port on which we hope no server is running,
     * or a server running on it will give an IOException when our
     * clients try to connect to it.
     * The port 999 is specified in
     * http://www.iana.org/assignments/port-numbers
     * as:
     * garcon           999/tcp
     * applix           999/udp        Applix ac
     * puprouter        999/tcp
     * puprouter        999/udp
     *
     * If the test fails because a server runs on this port and does
     * not give back an IOException, we can change to another one like
     * 9999
     */
    private static final int port = 999;

    private static final String[] protocols = {"rmi", "iiop", "jmxmp"};

    public static void main(String[] args) throws Exception {
        System.out.println("Test to close a failed client.");

        boolean ok = true;
        for (int i = 0; i < protocols.length; i++) {
            try {
                if (!test(protocols[i])) {
                    System.out.println("Test failed for " + protocols[i]);
                    ok = false;
                } else {
                    System.out.println("Test successed for " + protocols[i]);
                }
            } catch (Exception e) {
                System.out.println("Test failed for " + protocols[i]);
                e.printStackTrace(System.out);
                ok = false;
            }
        }

        if (ok) {
            System.out.println("Test passed");
            return;
        } else {
            System.out.println("TEST FAILED");
            System.exit(1);
        }
    }


    private static boolean test(String proto)
            throws Exception {
        System.out.println("Test for protocol " + proto);
        JMXServiceURL url = new JMXServiceURL(proto, null, port);

        JMXConnector connector;
        JMXConnectorServer server;

        for (int i=0; i<20; i++) {
            // no server
            try {
                connector = JMXConnectorFactory.newJMXConnector(url, null);
            } catch (MalformedURLException e) {
                System.out.println("Skipping unsupported URL " + url);
                return true;
            }

            try {
                connector.connect();

                throw new RuntimeException("Do not get expected IOEeption.");
            } catch (IOException e) {
                // OK, the expected IOException is thrown.");
            }

            // close the connector client
            connector.close();

            // with server but not started
            try {
                server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, null);
            } catch (MalformedURLException e) {
                System.out.println("Skipping unsupported URL " + url);
                return true;
            }

            connector = JMXConnectorFactory.newJMXConnector(url, null);

            try {
                connector.connect();

                throw new RuntimeException("Do not get expected IOEeption.");
            } catch (IOException e) {
                // OK, the expected IOException is thrown.");
            }

            // close the connector client
            connector.close();
        }

        return true;
    }
}
