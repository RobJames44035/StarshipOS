/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 8225766
 * @summary Curve in certificate should not affect signature scheme
 *          when using TLSv1.3
 * @library /javax/net/ssl/templates
 * @run main/othervm Tls13NamedGroups
 */

import javax.net.ssl.*;

public class Tls13NamedGroups extends SSLSocketTemplate {

    public static void main(String[] args) throws Exception {
        // Limit the supported named group to secp521r1.
        System.setProperty("jdk.tls.namedGroups", "secp521r1");

        new Tls13NamedGroups().run();
    }

    @Override
    public SSLContext createServerSSLContext() throws Exception {
        return createSSLContext(new Cert[]{Cert.CA_ECDSA_SECP256R1},
                new Cert[]{Cert.EE_ECDSA_SECP256R1},
                new ContextParameters("TLSv1.3", "PKIX", "NewSunX509"));
    }

    @Override
    protected void configureServerSocket(SSLServerSocket socket) {
        socket.setNeedClientAuth(true);
    }

    @Override
    public SSLContext createClientSSLContext() throws Exception {
        return createSSLContext(new Cert[]{Cert.CA_ECDSA_SECP256R1},
                new Cert[]{Cert.EE_ECDSA_SECP256R1},
                new ContextParameters("TLSv1.3", "PKIX", "NewSunX509"));
    }
}
