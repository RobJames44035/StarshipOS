/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.

/*
 * @test
 * @bug 6976117 8234725
 * @summary SSLContext.getInstance("TLSv1.1") returns SSLEngines/SSLSockets
 *          without TLSv1.1 enabled
 * @library /test/lib
 * @run main/othervm SSLContextVersion
 */

import javax.net.ssl.*;

import jdk.test.lib.security.SecurityUtils;

public class SSLContextVersion {
    static enum ContextVersion {
        TLS_CV_01("SSL", "TLSv1.2", "TLSv1.2"),
        TLS_CV_02("TLS", "TLSv1.2", "TLSv1.2"),
        TLS_CV_03("SSLv3", "TLSv1", "TLSv1.2"),
        TLS_CV_04("TLSv1", "TLSv1", "TLSv1.2"),
        TLS_CV_05("TLSv1.1", "TLSv1.1", "TLSv1.2"),
        TLS_CV_06("TLSv1.2", "TLSv1.2", "TLSv1.2"),
        TLS_CV_07("Default", "TLSv1.2", "TLSv1.2"),
        TLS_CV_08("Default", "TLSv1.3", "TLSv1.3"),
        TLS_CV_09("TLS", "TLSv1.3", "TLSv1.3"),
        TLS_CV_10("TLSv1.3", "TLSv1.3", "TLSv1.3");

        final String contextVersion;
        final String defaultProtocolVersion;
        final String supportedProtocolVersion;

        ContextVersion(String contextVersion, String defaultProtocolVersion,
                String supportedProtocolVersion) {
            this.contextVersion = contextVersion;
            this.defaultProtocolVersion = defaultProtocolVersion;
            this.supportedProtocolVersion = supportedProtocolVersion;
        }
    }

    public static void main(String[] args) throws Exception {
        // Re-enable TLSv1 and TLSv1.1 since test depends on them.
        SecurityUtils.removeFromDisabledTlsAlgs("TLSv1", "TLSv1.1");

        for (ContextVersion cv : ContextVersion.values()) {
            System.out.println("Checking SSLContext of " + cv.contextVersion);
            SSLContext context = SSLContext.getInstance(cv.contextVersion);

            // Default SSLContext is initialized automatically.
            if (!cv.contextVersion.equals("Default")) {
                // Use default TK, KM and random.
                context.init((KeyManager[])null, (TrustManager[])null, null);
            }

            SSLParameters parameters = context.getDefaultSSLParameters();

            String[] protocols = parameters.getProtocols();
            String[] ciphers = parameters.getCipherSuites();

            if (protocols.length == 0 || ciphers.length == 0) {
                throw new Exception("No default protocols or cipher suites");
            }

            boolean isMatch = false;
            for (String protocol : protocols) {
                System.out.println("\tdefault protocol version " + protocol);
                if (protocol.equals(cv.defaultProtocolVersion)) {
                    isMatch = true;
                    break;
                }
            }

            if (!isMatch) {
                throw new Exception("No matched default protocol");
            }

            parameters = context.getSupportedSSLParameters();

            protocols = parameters.getProtocols();
            ciphers = parameters.getCipherSuites();

            if (protocols.length == 0 || ciphers.length == 0) {
                throw new Exception("No supported protocols or cipher suites");
            }

            isMatch = false;
            for (String protocol : protocols) {
                System.out.println("\tsupported protocol version " + protocol);
                if (protocol.equals(cv.supportedProtocolVersion)) {
                    isMatch = true;
                    break;
                }
            }

            if (!isMatch) {
                throw new Exception("No matched supported protocol");
            }
            System.out.println("\t... Success");
        }
    }
}
