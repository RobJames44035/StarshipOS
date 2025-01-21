/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8263899
 * @summary HttpClient throws NPE in AuthenticationFilter when parsing www-authenticate head
 *
 * @run main/othervm EmptyAuthenticate
 */
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmptyAuthenticate {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        int port = 0;

        //start server:
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        port = server.getAddress().getPort();
        server.createContext("/", exchange -> {
            String response = "test body";
            //this empty header will make the HttpClient throw NPE
            exchange.getResponseHeaders().add("www-authenticate", "");
            exchange.sendResponseHeaders(401, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.start();

        HttpResponse<String> response = null;
        //run client:
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/")).GET().build();
            //this line will throw NPE (wrapped by IOException) when parsing empty www-authenticate response header in AuthenticationFilter:
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            boolean ok = !response.headers().firstValue("WWW-Authenticate").isEmpty();
            if (!ok) {
                throw new RuntimeException("WWW-Authenicate missing");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed");
        } finally {
            server.stop(0);
        }
    }
}
