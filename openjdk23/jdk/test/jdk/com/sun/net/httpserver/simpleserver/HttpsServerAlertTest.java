/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8315436
 * @summary Test if HttpsServer sends the TLS alerts produced
 * @library /test/lib
 * @build jdk.test.lib.net.SimpleSSLContext
 * @run testng/othervm HttpsServerAlertTest
 */

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import jdk.test.lib.net.SimpleSSLContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.fail;

public class HttpsServerAlertTest {

    static final InetSocketAddress LOOPBACK_ADDR = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);

    static final boolean ENABLE_LOGGING = true;
    static final Logger LOGGER = Logger.getLogger("com.sun.net.httpserver");

    SSLContext sslContext;

    @BeforeTest
    public void setup() throws IOException {
        if (ENABLE_LOGGING) {
            ConsoleHandler ch = new ConsoleHandler();
            LOGGER.setLevel(Level.ALL);
            ch.setLevel(Level.ALL);
            LOGGER.addHandler(ch);
        }
        sslContext = new SimpleSSLContext().get();
        SSLContext.setDefault(sslContext);
    }

    @Test
    public void testProtocolMismatch() throws Exception {
        SSLSocketFactory sf = sslContext.getSocketFactory();
        var server = HttpsServer.create(LOOPBACK_ADDR, 0);
        server.setHttpsConfigurator(new Configurator(sslContext));
        server.start();
        try (SSLSocket s = (SSLSocket) sf.createSocket()) {
            // server only accepts TLS 1.3
            s.setEnabledProtocols(new String[]{"TLSv1.2"});
            s.connect(server.getAddress());
            s.startHandshake();
            fail("Expected a handshake failure");
        } catch (SSLHandshakeException e) {
            System.out.println("Got exception: " + e);
            if (e.getCause() instanceof EOFException ||
                    !e.getMessage().contains("protocol_version"))
                throw e;
        } finally {
            server.stop(0);
        }
    }

    private static class Configurator extends HttpsConfigurator {
        public Configurator(SSLContext sslContext) {
            super(sslContext);
        }

        @Override
        public void configure(HttpsParameters params) {
            SSLParameters sslParams = getSSLContext().getDefaultSSLParameters();
            sslParams.setProtocols(new String[]{"TLSv1.3"});
            params.setSSLParameters(sslParams);
        }
    }
}
