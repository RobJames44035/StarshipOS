/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4696512
 * @summary HTTP client: Improve proxy server configuration and selection
 * @library /test/lib
 * @compile ProxyTest.java
 * @run main/othervm -Dhttp.proxyHost=inexistant -Dhttp.proxyPort=8080 ProxyTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jdk.test.lib.net.URIBuilder;

public class ProxyTest {
    static HttpServer server;

    public ProxyTest() {
    }

    static public class MyProxySelector extends ProxySelector {
        private static volatile URI lastURI;
        private final static List<Proxy> NO_PROXY = List.of(Proxy.NO_PROXY);

        public java.util.List<Proxy> select(URI uri) {
            System.out.println("Selecting no proxy for " + uri);
            lastURI = uri;
            return NO_PROXY;
        }

        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        }

        public static URI lastURI() { return lastURI; }
    }

    public static void main(String[] args) {
        ProxySelector defSelector = ProxySelector.getDefault();
        if (defSelector == null)
            throw new RuntimeException("Default ProxySelector is null");
        ProxySelector.setDefault(new MyProxySelector());
        try {
            InetAddress loopback = InetAddress.getLoopbackAddress();
            server = HttpServer.create(new InetSocketAddress(loopback, 0), 10, "/", new ProxyTestHandler());
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            URL url = URIBuilder.newBuilder()
                      .scheme("http")
                      .loopback()
                      .port(server.getAddress().getPort())
                      .toURL();
            System.out.println("client opening connection to: " + url);
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
            InputStream is = urlc.getInputStream();
            is.close();
            URI lastURI = MyProxySelector.lastURI();
            if (!String.valueOf(lastURI).equals(url + "/")) {
                throw new AssertionError("Custom proxy was not used: last URI was " + lastURI);
            }
        } catch (Exception e) {
                throw new RuntimeException(e);
        } finally {
            if (server != null) {
                server.stop(1);
            }
        }
    }
}

class ProxyTestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            exchange.sendResponseHeaders(200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(PrintWriter pw = new PrintWriter(exchange.getResponseBody(), false, Charset.forName("UTF-8"))) {
            pw.print("Hello .");
        }
    }
}
