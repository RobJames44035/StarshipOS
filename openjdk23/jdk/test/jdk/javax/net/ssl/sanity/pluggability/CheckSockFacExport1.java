/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4635454 6208022
 * @summary Check pluggability of SSLSocketFactory and
 * SSLServerSocketFactory classes.
 * @run main/othervm CheckSockFacExport1
 *
 *     SunJSSE does not support dynamic system properties, no way to re-use
 *     system properties in samevm/agentvm mode.
 */

import java.util.*;

import java.security.*;
import java.net.*;
import javax.net.ssl.*;

public class CheckSockFacExport1 {

    public static void main(String argv[]) throws Exception {
        // reserve the security properties
        String reservedSFacAlg =
            Security.getProperty("ssl.SocketFactory.provider");
        String reservedSSFacAlg =
            Security.getProperty("ssl.ServerSocketFactory.provider");

        try {
            Security.setProperty("ssl.SocketFactory.provider",
                                 "MySSLSocketFacImpl");
            MySSLSocketFacImpl.useCustomCipherSuites();
            Security.setProperty("ssl.ServerSocketFactory.provider",
                "MySSLServerSocketFacImpl");
            MySSLServerSocketFacImpl.useCustomCipherSuites();

            String[] supportedCS = null;
            for (int i = 0; i < 2; i++) {
                switch (i) {
                case 0:
                    System.out.println("Testing SSLSocketFactory:");
                    SSLSocketFactory sf = (SSLSocketFactory)
                        SSLSocketFactory.getDefault();
                    supportedCS = sf.getSupportedCipherSuites();
                    break;
                case 1:
                    System.out.println("Testing SSLServerSocketFactory:");
                    SSLServerSocketFactory ssf = (SSLServerSocketFactory)
                        SSLServerSocketFactory.getDefault();
                    supportedCS = ssf.getSupportedCipherSuites();
                    break;
                default:
                    throw new Exception("Internal Test Error");
                }
                System.out.println(Arrays.asList(supportedCS));
                if (supportedCS.length == 0) {
                    throw new Exception("supported ciphersuites are empty");
                }
            }
            System.out.println("Test Passed");
        } finally {
            // restore the security properties
            if (reservedSFacAlg == null) {
                reservedSFacAlg = "";
            }

            if (reservedSSFacAlg == null) {
                reservedSSFacAlg = "";
            }
            Security.setProperty("ssl.SocketFactory.provider",
                                                            reservedSFacAlg);
            Security.setProperty("ssl.ServerSocketFactory.provider",
                                                            reservedSSFacAlg);
        }
    }
}
