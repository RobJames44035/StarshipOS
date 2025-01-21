/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8226374
 * @library /javax/net/ssl/templates
 * @summary Restrict signature algorithms and named groups
 * @run main/othervm RestrictSignatureScheme
 */

import java.security.Security;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLException;

public class RestrictSignatureScheme extends SSLSocketTemplate {

    private static volatile int index;
    private static final String[][][] protocols = {
        {{"TLSv1.3"}, {"TLSv1.3"}},
        {{"TLSv1.3", "TLSv1.2"}, {"TLSv1.2"}},
        {{"TLSv1.3", "TLSv1.2"}, {"TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.3", "TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.2"}}
    };

    private final SSLContext context;
    RestrictSignatureScheme() throws Exception {
        this.context = createSSLContext(
                new Cert[]{Cert.EE_RSASSA_PSS},
                new Cert[]{Cert.EE_RSASSA_PSS},
                new ContextParameters("TLS", "PKIX", "NewSunX509")
        );
    }

    @Override
    public SSLContext createClientSSLContext() throws Exception {
        return context;
    }

    @Override
    public SSLContext createServerSSLContext() throws Exception {
        return context;
    }

    // Servers are configured before clients, increment test case after.
    @Override
    protected void configureClientSocket(SSLSocket socket) {
        String[] ps = protocols[index][0];

        System.out.print("Setting client protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        socket.setEnabledProtocols(ps);
        socket.setEnabledCipherSuites(new String[] {
            "TLS_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"});
    }

    @Override
    protected void configureServerSocket(SSLServerSocket serverSocket) {
        String[] ps = protocols[index][1];

        System.out.print("Setting server protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        serverSocket.setEnabledProtocols(ps);
        serverSocket.setEnabledCipherSuites(new String[] {
            "TLS_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"});
    }

    /*
     * Run the test case.
     */
    public static void main(String[] args) throws Exception {
        Security.setProperty("jdk.tls.disabledAlgorithms", "RSASSA-PSS");

        for (index = 0; index < protocols.length; index++) {
            try {
                (new RestrictSignatureScheme()).run();
            } catch (SSLException | IllegalStateException ssle) {
                // The named group should be restricted.
                continue;
            }

            throw new Exception("The test case should be disabled");
        }
    }
}
