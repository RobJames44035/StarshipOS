/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.security.cert.X509Certificate;

import sun.security.ssl.ClientHello.ClientHelloMessage;

class ClientHandshakeContext extends HandshakeContext {
    /*
     * Allow unsafe server certificate change?
     *
     * Server certificate change during SSL/TLS renegotiation may be considered
     * unsafe, as described in the Triple Handshake attacks:
     *
     *     https://secure-resumption.com/tlsauth.pdf
     *
     * Endpoint identification (See
     * SSLParameters.getEndpointIdentificationAlgorithm()) is a pretty nice
     * guarantee that the server certificate change in renegotiation is legal.
     * However, endpoint identification is only enabled for HTTPS and LDAP
     * over SSL/TLS by default.  It is not enough to protect SSL/TLS
     * connections other than HTTPS and LDAP.
     *
     * The renegotiation indication extension (See RFC 5746) is a pretty
     * strong guarantee that the endpoints on both client and server sides
     * are identical on the same connection.  However, the Triple Handshake
     * attacks can bypass this guarantee if there is a session-resumption
     * handshake between the initial full handshake and the renegotiation
     * full handshake.
     *
     * Server certificate change may be unsafe and should be restricted if
     * endpoint identification is not enabled and the previous handshake is
     * a session-resumption abbreviated initial handshake, unless the
     * identities represented by both certificates can be regraded as the
     * same (See isIdentityEquivalent()).
     *
     * Considering the compatibility impact and the actual requirements to
     * support server certificate change in practice, the system property,
     * jdk.tls.allowUnsafeServerCertChange, is used to define whether unsafe
     * server certificate change in renegotiation is allowed or not.  The
     * default value of the system property is "false".  To mitigate the
     * compatibility impact, applications may want to set the system
     * property to "true" at their own risk.
     *
     * If the value of the system property is "false", server certificate
     * change in renegotiation after a session-resumption abbreviated initial
     * handshake is restricted (See isIdentityEquivalent()).
     *
     * If the system property is set to "true" explicitly, the restriction on
     * server certificate change in renegotiation is disabled.
     */
    static final boolean allowUnsafeServerCertChange =
            Utilities.getBooleanProperty(
                    "jdk.tls.allowUnsafeServerCertChange", false);

    /*
     * the reserved server certificate chain in previous handshaking
     *
     * The server certificate chain is only reserved if the previous
     * handshake is a session-resumption abbreviated initial handshake.
     */
    X509Certificate[] reservedServerCerts = null;

    X509Certificate[] deferredCerts;

    ClientHelloMessage initialClientHelloMsg = null;

    // Flag to indicate receipt of a CertificateRequest message from
    // the server.  Because this is optional, we cannot guarantee
    // the handshakeConsumers Map will always have it present there.
    boolean receivedCertReq = false;

    // PSK identity is selected in first Hello and used again after HRR
    byte[] pskIdentity;

    ClientHandshakeContext(SSLContextImpl sslContext,
            TransportContext conContext) throws IOException {
        super(sslContext, conContext);
    }

    @Override
    void kickstart() throws IOException {
        if (kickstartMessageDelivered) {
            return;
        }

        SSLHandshake.kickstart(this);
        kickstartMessageDelivered = true;
    }
}
