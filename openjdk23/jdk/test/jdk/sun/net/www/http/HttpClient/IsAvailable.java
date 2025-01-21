/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8009650
 * @summary HttpClient available() check throws SocketException when connection
 * has been closed
 * @modules java.base/sun.net
 *          java.base/sun.net.www.http:+open
 * @library /test/lib
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.ServerSocket;
import sun.net.www.http.HttpClient;
import java.security.*;
import java.lang.reflect.Method;
import jdk.test.lib.net.URIBuilder;

public class IsAvailable {

    public static void main(String[] args) throws Exception {
        int readTimeout = 20;
        ServerSocket ss = new ServerSocket();
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ss.bind(new InetSocketAddress(loopback, 0));

        try (ServerSocket toclose = ss) {

            URL url1 = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(ss.getLocalPort())
                .toURL();

            HttpClient c1 = HttpClient.New(url1);

            Method available = HttpClient.class.
                    getDeclaredMethod("available", null);
            available.setAccessible(true);

            c1.setReadTimeout(readTimeout);
            boolean a = (boolean) available.invoke(c1);
            if (!a) {
                throw new RuntimeException("connection should be available");
            }
            if (c1.getReadTimeout() != readTimeout) {
                throw new RuntimeException("read timeout has been altered");
            }

            c1.closeServer();

            a = (boolean) available.invoke(c1);
            if (a) {
                throw new RuntimeException("connection shouldn't be available");
            }
        }
    }
}
