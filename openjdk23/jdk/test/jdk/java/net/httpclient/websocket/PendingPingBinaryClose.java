/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @build DummyWebSocketServer
 * @run testng/othervm
 *      -Djdk.httpclient.sendBufferSize=8192
 *       PendingPingBinaryClose
 */

// This test produce huge logs (14Mb+) so disable logging by default
// *      -Djdk.internal.httpclient.debug=true
// *      -Djdk.internal.httpclient.websocket.debug=true

import org.testng.annotations.Test;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PendingPingBinaryClose extends PendingOperations {

    CompletableFuture<WebSocket> cfBinary;
    CompletableFuture<WebSocket> cfPing;
    CompletableFuture<WebSocket> cfClose;

    @Test(dataProvider = "booleans")
    public void pendingPingBinaryClose(boolean last) throws Exception {
        repeatable( () -> {
            server = Support.notReadingServer();
            server.setReceiveBufferSize(1024);
            server.open();
            webSocket = httpClient().newWebSocketBuilder()
                    .buildAsync(server.getURI(), new WebSocket.Listener() { })
                    .join();
            ByteBuffer data = ByteBuffer.allocate(125);
            for (int i = 0; ; i++) {  // fill up the send buffer
                long start = System.currentTimeMillis();
                System.out.printf("begin cycle #%s at %s%n", i, start);
                cfPing = webSocket.sendPing(data);
                try {
                    cfPing.get(waitSec, TimeUnit.SECONDS);
                    data.clear();
                } catch (TimeoutException e) {
                    break;
                } finally {
                    long stop = System.currentTimeMillis();
                    System.out.printf("end cycle #%s at %s (%s ms)%n", i, stop, stop - start);
                }
            }
            assertFails(ISE, webSocket.sendPing(ByteBuffer.allocate(125)));
            assertFails(ISE, webSocket.sendPong(ByteBuffer.allocate(125)));
            cfBinary = webSocket.sendBinary(ByteBuffer.allocate(4), last);
            cfClose = webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
            assertAllHang(cfBinary, cfClose);
            assertNotDone(cfPing);
            webSocket.abort();
            assertFails(IOE, cfPing);
            assertFails(IOE, cfBinary);
            assertFails(IOE, cfClose);
            return null;
        }, () -> cfPing.isDone());
    }

    @Override
    long initialWaitSec() {
        // Some Windows machines increase buffer size after 1-2 seconds
        return isWindows() ? 3 : 1;
    }
}
