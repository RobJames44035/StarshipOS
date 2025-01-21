/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

/*
 * THE CONTENTS OF THIS FILE HAVE TO BE IN SYNC WITH THE EXAMPLES USED IN THE
 * JAVADOC OF WEBSOCKET TYPE
 *
 * @test
 * @compile WebSocketExample.java
 */
public class WebSocketExample {

    WebSocket.Listener listener = new WebSocket.Listener() {
        // ...
    };

    public void newBuilderExample0() {
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://websocket.example.com"), listener);
    }

    public void newBuilderExample1() {
        InetSocketAddress addr = new InetSocketAddress("proxy.example.com", 80);
        HttpClient client = HttpClient.newBuilder()
                .proxy(ProxySelector.of(addr))
                .build();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://websocket.example.com"), listener);
    }

    public void requestExample() {
        WebSocket.Listener listener = new WebSocket.Listener() {

            StringBuilder text = new StringBuilder();

            @Override
            public CompletionStage<?> onText(WebSocket webSocket,
                                             CharSequence message,
                                             boolean last) {
                text.append(message);
                if (last) {
                    processCompleteTextMessage(text);
                    text = new StringBuilder();
                }
                webSocket.request(1);
                return null;
            }
        };
    }

    static void processCompleteTextMessage(CharSequence result) { }
}
