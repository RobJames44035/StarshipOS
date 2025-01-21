/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6469663
 * @summary HTTP Request-URI contains fragment when connecting through proxy
 * @library /test/lib
 * @run main/othervm RequestURI
 */

import java.net.*;
import java.io.*;

import jdk.test.lib.net.HttpHeaderParser;

// Create a Server listening on port 5001 to act as the proxy. Requests
// never need to be forwared from it. We are only interested in the
// request being sent to it. Set the system proxy properties to the
// value of the RequestURIServer so that the HTTP request will to sent to it.

public class RequestURI
{
    public static void main(String[] args) {
        ServerSocket ss;
        int port;

        try {
            ss = new ServerSocket(5001);
            port = ss.getLocalPort();
        } catch (Exception e) {
            System.out.println ("Exception: " + e);
            return;
        }

        RequestURIServer server = new RequestURIServer(ss);
        server.start();

        try {
            System.getProperties().setProperty("http.proxyHost", "localhost");
            System.getProperties().setProperty("http.proxyPort", Integer.toString(port));

            URL url = new URL("http://boo.bar.com/foo.html#section5");
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();

            int resp = uc.getResponseCode();
            if (resp != 200)
                throw new RuntimeException("Failed: Fragment is being passed as part of the RequestURI");

            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RequestURIServer extends Thread
{
    ServerSocket ss;

    String replyOK =  "HTTP/1.1 200 OK\r\n" +
                      "Content-Length: 0\r\n\r\n";
    String replyFAILED = "HTTP/1.1 404 Not Found\r\n\r\n";

    public RequestURIServer(ServerSocket ss) {
        this.ss = ss;
    }

    public void run() {
        try {
            Socket sock = ss.accept();
            InputStream is = sock.getInputStream();
            OutputStream os = sock.getOutputStream();

            HttpHeaderParser headers =  new HttpHeaderParser (is);
            String requestLine = headers.getRequestDetails();

            int first  = requestLine.indexOf(' ');
            int second  = requestLine.lastIndexOf(' ');
            String URIString = requestLine.substring(first+1, second);

            URI requestURI = new URI(URIString);
            if (requestURI.getFragment() != null)
                os.write(replyFAILED.getBytes("UTF-8"));
            else
                os.write(replyOK.getBytes("UTF-8"));

            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
