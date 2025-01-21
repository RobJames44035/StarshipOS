/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @build DummyWebSocketServer
 * @run testng/othervm
 *      -Djdk.httpclient.sendBufferSize=8192
 *      -Djdk.internal.httpclient.debug=true
 *      -Djdk.internal.httpclient.websocket.debug=true
 *       PendingBinaryPongClose
 */

import org.testng.annotations.Test;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PendingBinaryPongClose extends PendingOperations {

    CompletableFuture<WebSocket> cfBinary;
    CompletableFuture<WebSocket> cfPong;
    CompletableFuture<WebSocket> cfClose;

    @Test(dataProvider = "booleans")
    public void pendingBinaryPongClose(boolean last) throws Exception {
        repeatable(() -> {
            server = Support.notReadingServer();
            server.setReceiveBufferSize(1024);
            server.open();
            webSocket = httpClient().newWebSocketBuilder()
                    .buildAsync(server.getURI(), new WebSocket.Listener() { })
                    .join();
            ByteBuffer data = ByteBuffer.allocate(65536);
            for (int i = 0; ; i++) {  // fill up the send buffer
                long start = System.currentTimeMillis();
                System.out.printf("begin cycle #%s at %s%n", i, start);
                cfBinary = webSocket.sendBinary(data, last);
                try {
                    cfBinary.get(waitSec, TimeUnit.SECONDS);
                    data.clear();
                } catch (TimeoutException e) {
                    break;
                } finally {
                    long stop = System.currentTimeMillis();
                    System.out.printf("end cycle #%s at %s (%s ms)%n", i, stop, stop - start);
                }
            }
            assertFails(ISE, webSocket.sendText("", true));
            assertFails(ISE, webSocket.sendText("", false));
            assertFails(ISE, webSocket.sendBinary(ByteBuffer.allocate(0), true));
            assertFails(ISE, webSocket.sendBinary(ByteBuffer.allocate(0), false));
            cfPong = webSocket.sendPong(ByteBuffer.allocate(125));
            assertFails(ISE, webSocket.sendPing(ByteBuffer.allocate(125)));
            assertFails(ISE, webSocket.sendPong(ByteBuffer.allocate(125)));
            cfClose = webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
            assertAllHang(cfPong, cfClose);
            assertNotDone(cfBinary);
            webSocket.abort();
            assertFails(IOE, cfBinary);
            assertFails(IOE, cfPong);
            assertFails(IOE, cfClose);
            return null;
        }, () -> cfBinary.isDone());
    }
}
