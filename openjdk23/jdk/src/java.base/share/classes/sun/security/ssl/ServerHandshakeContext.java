/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.io.IOException;
import java.security.AlgorithmConstraints;
import sun.security.util.LegacyAlgorithmConstraints;

class ServerHandshakeContext extends HandshakeContext {
    // To prevent the TLS renegotiation issues, by setting system property
    // "jdk.tls.rejectClientInitiatedRenegotiation" to true, applications in
    // server side can disable all client initiated SSL renegotiation
    // regardless of the support of TLS protocols.
    //
    // By default, allow client initiated renegotiation.
    static final boolean rejectClientInitiatedRenego =
            Utilities.getBooleanProperty(
                "jdk.tls.rejectClientInitiatedRenegotiation", false);

    // legacy algorithm constraints
    static final AlgorithmConstraints legacyAlgorithmConstraints =
            new LegacyAlgorithmConstraints(
                    LegacyAlgorithmConstraints.PROPERTY_TLS_LEGACY_ALGS,
                    new SSLAlgorithmDecomposer());

    // temporary authentication information
    SSLPossession interimAuthn;

    StatusResponseManager.StaplingParameters stapleParams;
    CertificateMessage.CertificateEntry currentCertEntry;
    private static final long DEFAULT_STATUS_RESP_DELAY = 5000L;
    final long statusRespTimeout;
    boolean acceptCliHelloFragments = false;


    ServerHandshakeContext(SSLContextImpl sslContext,
            TransportContext conContext) throws IOException {
        super(sslContext, conContext);
        long respTimeOut = Long.getLong(
                    "jdk.tls.stapling.responseTimeout",
                    DEFAULT_STATUS_RESP_DELAY);
        statusRespTimeout = respTimeOut >= 0 ? respTimeOut :
                DEFAULT_STATUS_RESP_DELAY;
        handshakeConsumers.put(
                SSLHandshake.CLIENT_HELLO.id, SSLHandshake.CLIENT_HELLO);
    }

    @Override
    void kickstart() throws IOException {
        if (!conContext.isNegotiated || kickstartMessageDelivered) {
            return;
        }

        SSLHandshake.kickstart(this);
        kickstartMessageDelivered = true;
    }
}

