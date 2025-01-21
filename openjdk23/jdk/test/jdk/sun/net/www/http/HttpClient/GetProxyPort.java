/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5025019
 * @summary REGRESSION: Sun implementation for HttpURLConnection could throw NPE
 * @modules java.base/sun.net
 *          java.base/sun.net.www.http
 * @library /test/lib
 */
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import sun.net.www.http.HttpClient;
import jdk.test.lib.net.URIBuilder;

public class GetProxyPort {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket();
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ss.bind(new InetSocketAddress(loopback, 0));
        URL myURL = URIBuilder.newBuilder()
            .scheme("http")
            .loopback()
            .port(ss.getLocalPort())
            .toURL();
        HttpClient httpC = new HttpClient(myURL, null, -1);
        int port = httpC.getProxyPortUsed();
    }
}
