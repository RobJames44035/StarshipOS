/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8224973
 * @summary Basic test for the default behavior of openConnection(URL,Proxy)
 * @run testng TestDefaultBehavior
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import org.testng.annotations.Test;
import static java.net.Proxy.*;
import static org.testng.Assert.expectThrows;

public class TestDefaultBehavior {

    static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;
    static final Class<UnsupportedOperationException> UOE = UnsupportedOperationException.class;

    static final InetSocketAddress ADDR = InetSocketAddress.createUnresolved("proxy.com", 80);
    static final URI uri = URI.create("http://example.com:80/");

    @Test
    public void testDefaultBehavior() {
        CustomURLStreamHandler handler = new CustomURLStreamHandler();

        expectThrows(IAE, () -> handler.openConnection(null, null));
        expectThrows(IAE, () -> handler.openConnection(null, NO_PROXY));
        expectThrows(IAE, () -> handler.openConnection(null, new Proxy(Type.SOCKS, ADDR)));
        expectThrows(IAE, () -> handler.openConnection(null, new Proxy(Type.HTTP, ADDR)));
        expectThrows(IAE, () -> handler.openConnection(uri.toURL(), null));

        expectThrows(UOE, () -> handler.openConnection(uri.toURL(), NO_PROXY));
        expectThrows(UOE, () -> handler.openConnection(uri.toURL(), new Proxy(Type.SOCKS, ADDR)));
        expectThrows(UOE, () -> handler.openConnection(uri.toURL(), new Proxy(Type.HTTP, ADDR)));
    }

    // A URLStreamHandler that delegates the overloaded openConnection that
    // takes a proxy, to the default java.net.URLStreamHandler implementation.
    static class CustomURLStreamHandler extends URLStreamHandler {

        @Override
        public URLConnection openConnection(URL url, Proxy proxy) throws IOException {
            return super.openConnection(url, proxy);
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }
    }
}
