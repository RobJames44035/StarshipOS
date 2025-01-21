/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 5019096
 * @summary Add scatter/gather APIs for SSLEngine
 * @library /javax/net/ssl/templates
 * @run main/othervm CloseStart
 */

//
// Check to see if the args are being parsed properly.
//

import javax.net.ssl.*;

public class CloseStart extends SSLContextTemplate {

    private static void checkDone(SSLEngine ssle) throws Exception {
        if (!ssle.isInboundDone()) {
            throw new Exception("isInboundDone isn't done");
        }
        if (!ssle.isOutboundDone()) {
            throw new Exception("isOutboundDone isn't done");
        }
    }

    private static void runTest2(SSLEngine ssle) throws Exception {
        ssle.closeOutbound();
        checkDone(ssle);
    }

    public static void main(String args[]) throws Exception {
        new CloseStart().run();
    }

    private void run() throws Exception {
        SSLEngine ssle = createSSLEngine();
        ssle.closeInbound();
        if (!ssle.isInboundDone()) {
            throw new Exception("isInboundDone isn't done");
        }

        ssle = createSSLEngine();
        ssle.closeOutbound();
        if (!ssle.isOutboundDone()) {
            throw new Exception("isOutboundDone isn't done");
        }

        System.out.println("Test Passed.");
    }

    /*
     * Create an initialized SSLContext to use for this test.
     */
    private SSLEngine createSSLEngine() throws Exception {

        SSLContext sslCtx = createClientSSLContext();
        SSLEngine ssle = sslCtx.createSSLEngine("client", 1001);
        ssle.setUseClientMode(true);

        return ssle;
    }
}
