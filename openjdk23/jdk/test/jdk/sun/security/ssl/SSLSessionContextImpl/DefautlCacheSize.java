/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8210985
 * @summary Update the default SSL session cache size to 20480
 * @run main/othervm DefautlCacheSize
 */

// The SunJSSE provider cannot use System Properties in samevm/agentvm mode.
// Please run JSSE test in othervm mode.

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;

public class DefautlCacheSize {

    public static void main(String[] args) throws Exception {
        SSLServerSocketFactory sssf =
                (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

        try (SSLServerSocket serverSocket =
                    (SSLServerSocket)sssf.createServerSocket()) {

            String[] protocols = serverSocket.getSupportedProtocols();
            for (int i = 0; i < protocols.length; i++) {
                if (protocols[i].equals("SSLv2Hello")) {
                    continue;
                }
                SSLContext sslContext = SSLContext.getInstance(protocols[i]);
                SSLSessionContext sessionContext =
                        sslContext.getServerSessionContext();
                if (sessionContext.getSessionCacheSize() == 0) {
                    throw new Exception(
                        "the default server session cache size is infinite");
                }

                sessionContext = sslContext.getClientSessionContext();
                if (sessionContext.getSessionCacheSize() == 0) {
                    throw new Exception(
                        "the default client session cache size is infinite");
                }
            }
        }
    }
}
