/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/**
 * @test
 * @bug 4302026
 * @run main/othervm GetPeerHost
 * @summary make sure the server side doesn't do DNS lookup.
 */
import javax.net.*;

public class GetPeerHost {

    public static void main(String[] argv) throws Exception {

        String testRoot = System.getProperty("test.src", ".");
        System.setProperty("javax.net.ssl.trustStore", testRoot
                            + "/../../../../javax/net/ssl/etc/truststore");
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");
        GetPeerHostServer server = new GetPeerHostServer();
        server.start();
        GetPeerHostClient client =
            new GetPeerHostClient(server.getServerPort());
        client.start();
        server.join ();
        if (!server.getPassStatus ()) {
            throw new Exception ("The test failed");
        }
    }
}
