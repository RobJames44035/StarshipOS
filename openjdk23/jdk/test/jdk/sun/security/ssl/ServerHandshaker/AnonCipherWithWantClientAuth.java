/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 4392475
 * @modules jdk.crypto.ec
 * @library /javax/net/ssl/templates
 * @summary Calling setWantClientAuth(true) disables anonymous suites
 * @run main/othervm -Djavax.net.debug=all AnonCipherWithWantClientAuth
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import javax.net.ssl.SSLSocket;

public class AnonCipherWithWantClientAuth extends SSLSocketTemplate {
    /*
     * Run the test case.
     */
    public static void main(String[] args) throws Exception {
        // reset the security property to make sure that the algorithms
        // and keys used in this test are not disabled.
        Security.setProperty("jdk.tls.disabledAlgorithms", "");
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");

        (new AnonCipherWithWantClientAuth()).run();
    }

    @Override
    protected void runServerApplication(SSLSocket socket) throws Exception {
        String ciphers[] = {
                "SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DH_anon_EXPORT_WITH_RC4_40_MD5",
                "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA" };
        socket.setEnabledCipherSuites(ciphers);
        socket.setWantClientAuth(true);

        InputStream sslIS = socket.getInputStream();
        OutputStream sslOS = socket.getOutputStream();

        sslIS.read();
        sslOS.write(85);
        sslOS.flush();
    }

    @Override
    protected void runClientApplication(SSLSocket socket) throws Exception {
        String ciphers[] = {
                "SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DH_anon_EXPORT_WITH_RC4_40_MD5" };
        socket.setEnabledCipherSuites(ciphers);
        socket.setUseClientMode(true);

        InputStream sslIS = socket.getInputStream();
        OutputStream sslOS = socket.getOutputStream();

        sslOS.write(280);
        sslOS.flush();
        sslIS.read();
    }
}
