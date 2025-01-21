/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.test.lib.security;

import java.io.*;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;


public final class TestTLSHandshake extends SSLSocketTest {

    public static final String CIPHER_SUITE =
        "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384";
    public static final long CERT_ID = Integer.toUnsignedLong(-1057291798);
    public static final long ANCHOR_CERT_ID = Integer.toUnsignedLong(1688661792);
    public static final String CERT_SERIAL = "00:ed:be:c8:f7:05:af:25:14";
    public static final String ANCHOR_CERT_SERIAL = "8e:19:17:78:b2:f3:31:be";

    public String protocolVersion;
    public String peerHost;
    public int peerPort;

    @Override
    protected void runServerApplication(SSLSocket socket) throws Exception {
        InputStream sslIS = socket.getInputStream();
        OutputStream sslOS = socket.getOutputStream();

        sslIS.read();
        sslOS.write(85);
        sslOS.flush();
    }

    @Override
    protected void runClientApplication(SSLSocket socket) throws Exception {
        socket.setEnabledCipherSuites(new String[] { CIPHER_SUITE });
        InputStream sslIS = socket.getInputStream();
        OutputStream sslOS = socket.getOutputStream();

        sslOS.write(280);
        sslOS.flush();
        sslIS.read();

        SSLSession sslSession = socket.getSession();
        protocolVersion =  sslSession.getProtocol();
        peerHost = sslSession.getPeerHost();
        peerPort = sslSession.getPeerPort();
    }
}
