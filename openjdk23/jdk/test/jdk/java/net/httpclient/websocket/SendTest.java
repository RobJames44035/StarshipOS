/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @build DummyWebSocketServer
 * @run testng/othervm
 *      -Djdk.internal.httpclient.websocket.debug=true
 *       SendTest
 */

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.WebSocket;

import static java.net.http.HttpClient.Builder.NO_PROXY;
import static java.net.http.HttpClient.newBuilder;
import static java.net.http.WebSocket.NORMAL_CLOSURE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

public class SendTest {

    private static final Class<NullPointerException> NPE = NullPointerException.class;

    @Test
    public void sendMethodsThrowNPE() throws IOException {
        try (var server = new DummyWebSocketServer()) {
            server.open();
            var webSocket = newBuilder().proxy(NO_PROXY).build().newWebSocketBuilder()
                    .buildAsync(server.getURI(), new WebSocket.Listener() { })
                    .join();
            try {
                assertThrows(NPE, () -> webSocket.sendText(null, false));
                assertThrows(NPE, () -> webSocket.sendText(null, true));
                assertThrows(NPE, () -> webSocket.sendBinary(null, false));
                assertThrows(NPE, () -> webSocket.sendBinary(null, true));
                assertThrows(NPE, () -> webSocket.sendPing(null));
                assertThrows(NPE, () -> webSocket.sendPong(null));
                assertThrows(NPE, () -> webSocket.sendClose(NORMAL_CLOSURE, null));

                webSocket.abort();

                assertThrows(NPE, () -> webSocket.sendText(null, false));
                assertThrows(NPE, () -> webSocket.sendText(null, true));
                assertThrows(NPE, () -> webSocket.sendBinary(null, false));
                assertThrows(NPE, () -> webSocket.sendBinary(null, true));
                assertThrows(NPE, () -> webSocket.sendPing(null));
                assertThrows(NPE, () -> webSocket.sendPong(null));
                assertThrows(NPE, () -> webSocket.sendClose(NORMAL_CLOSURE, null));
            } finally {
                webSocket.abort();
            }
        }
    }

    // TODO: request in onClose/onError
    // TODO: throw exception in onClose/onError
    // TODO: exception is thrown from request()

    @Test
    public void sendCloseCompleted() throws IOException {
        try (var server = new DummyWebSocketServer()) {
            server.open();
            var webSocket = newBuilder().proxy(NO_PROXY).build().newWebSocketBuilder()
                    .buildAsync(server.getURI(), new WebSocket.Listener() { })
                    .join();
            try {
                webSocket.sendClose(NORMAL_CLOSURE, "").join();
                assertTrue(webSocket.isOutputClosed());
                assertEquals(webSocket.getSubprotocol(), "");
                webSocket.request(1); // No exceptions must be thrown
            } finally {
                webSocket.abort();
            }
        }
    }
}
