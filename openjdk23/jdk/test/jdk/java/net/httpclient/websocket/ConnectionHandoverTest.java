/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8164625
 * @summary Verifies HttpClient yields the connection to the WebSocket
 * @build DummyWebSocketServer
 * @run main/othervm -Djdk.httpclient.HttpClient.log=trace ConnectionHandoverTest
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;

public class ConnectionHandoverTest {
    /*
     * An I/O channel associated with the connection is closed by WebSocket.abort().
     * If this connection is returned to the connection pool, then the second
     * attempt to use it would fail with a ClosedChannelException.
     *
     * The assumption is that since the WebSocket client is connecting to the
     * same URI, the pooled connection is to be used.
     */
    public static void main(String[] args) throws IOException {
        try (DummyWebSocketServer server = new DummyWebSocketServer()) {
            server.open();
            URI uri = server.getURI();
            WebSocket.Builder webSocketBuilder =
                    HttpClient.newHttpClient().newWebSocketBuilder();

            WebSocket ws1 = webSocketBuilder
                    .buildAsync(uri, new WebSocket.Listener() { }).join();
            ws1.abort();

            WebSocket ws2 = webSocketBuilder
                    .buildAsync(uri, new WebSocket.Listener() { }).join(); // Exception here if the connection was pooled
            ws2.abort();
        }
    }
}
