/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8217633
 * @library /javax/net/ssl/templates
 * @summary Configurable extensions with system properties
 * @run main/othervm DisableExtensions supported_versions TLSv1.3 fail
 * @run main/othervm DisableExtensions supported_versions TLSv1.2
 */

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLException;

public class DisableExtensions extends SSLSocketTemplate {

    private final String[] protocols;

    public DisableExtensions(String[] protocols) {
        this.protocols = protocols;
    }

    @Override
    protected void configureClientSocket(SSLSocket socket) {
        socket.setEnabledProtocols(protocols);
    }

    // Run the test case.
    //
    // Check that the extension could be disabled, and the impact may be
    // different for different protocols.
    public static void main(String[] args) throws Exception {
        System.setProperty("jdk.tls.client.disableExtensions", args[0]);

        boolean shouldSuccess = (args.length != 3);

        try {
            (new DisableExtensions(new String[] {args[1]})).run();
        } catch (SSLException | IllegalStateException ssle) {
            if (shouldSuccess) {
                throw new RuntimeException(
                        "The extension " + args[0] + " is disabled");
            }

            return;
        }

        if (!shouldSuccess) {
            throw new RuntimeException(
                    "The extension " + args[0] +
                    " should be disabled and the connection should fail");
        }
    }
}
