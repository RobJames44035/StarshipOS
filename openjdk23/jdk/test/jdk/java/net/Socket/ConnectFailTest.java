/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.test.lib.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.List;

import static java.net.InetAddress.getLoopbackAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @test
 * @bug 8343791
 * @summary verifies that `connect()` failures throw the expected exception and leave socket in the expected state
 * @library /test/lib
 * @run junit ConnectFailTest
 */
class ConnectFailTest {

    // Implementation Note: Explicitly binding on the loopback address to avoid potential unstabilities.

    private static final int DEAD_SERVER_PORT = 0xDEAD;

    private static final InetSocketAddress REFUSING_SOCKET_ADDRESS = Utils.refusingEndpoint();

    private static final InetSocketAddress UNRESOLVED_ADDRESS =
            InetSocketAddress.createUnresolved("no.such.host", DEAD_SERVER_PORT);

    @Test
    void testUnresolvedAddress() {
        assertTrue(UNRESOLVED_ADDRESS.isUnresolved());
    }

    /**
     * Verifies that an unbound socket is closed when {@code connect()} fails.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testUnboundSocket(Socket socket) throws IOException {
        try (socket) {
            assertFalse(socket.isBound());
            assertFalse(socket.isConnected());
            assertThrows(IOException.class, () -> socket.connect(REFUSING_SOCKET_ADDRESS));
            assertTrue(socket.isClosed());
        }
    }

    /**
     * Verifies that a bound socket is closed when {@code connect()} fails.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testBoundSocket(Socket socket) throws IOException {
        try (socket) {
            socket.bind(new InetSocketAddress(getLoopbackAddress(), 0));
            assertTrue(socket.isBound());
            assertFalse(socket.isConnected());
            assertThrows(IOException.class, () -> socket.connect(REFUSING_SOCKET_ADDRESS));
            assertTrue(socket.isClosed());
        }
    }

    /**
     * Verifies that a connected socket is not closed when {@code connect()} fails.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testConnectedSocket(Socket socket) throws Throwable {
        try (socket; ServerSocket serverSocket = createEphemeralServerSocket()) {
            socket.connect(serverSocket.getLocalSocketAddress());
            try (Socket _ = serverSocket.accept()) {
                assertTrue(socket.isBound());
                assertTrue(socket.isConnected());
                SocketException exception = assertThrows(
                        SocketException.class,
                        () -> socket.connect(REFUSING_SOCKET_ADDRESS));
                assertEquals("Already connected", exception.getMessage());
                assertFalse(socket.isClosed());
            }
        }
    }

    /**
     * Verifies that an unbound socket is closed when {@code connect()} is invoked using an unresolved address.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testUnboundSocketWithUnresolvedAddress(Socket socket) throws IOException {
        try (socket) {
            assertFalse(socket.isBound());
            assertFalse(socket.isConnected());
            assertThrows(UnknownHostException.class, () -> socket.connect(UNRESOLVED_ADDRESS));
            assertTrue(socket.isClosed());
        }
    }

    /**
     * Verifies that a bound socket is closed when {@code connect()} is invoked using an unresolved address.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testBoundSocketWithUnresolvedAddress(Socket socket) throws IOException {
        try (socket) {
            socket.bind(new InetSocketAddress(getLoopbackAddress(), 0));
            assertTrue(socket.isBound());
            assertFalse(socket.isConnected());
            assertThrows(UnknownHostException.class, () -> socket.connect(UNRESOLVED_ADDRESS));
            assertTrue(socket.isClosed());
        }
    }

    /**
     * Verifies that a connected socket is not closed when {@code connect()} is invoked using an unresolved address.
     */
    @ParameterizedTest
    @MethodSource("sockets")
    void testConnectedSocketWithUnresolvedAddress(Socket socket) throws Throwable {
        try (socket; ServerSocket serverSocket = createEphemeralServerSocket()) {
            socket.connect(serverSocket.getLocalSocketAddress());
            try (Socket _ = serverSocket.accept()) {
                assertTrue(socket.isBound());
                assertTrue(socket.isConnected());
                assertThrows(IOException.class, () -> socket.connect(UNRESOLVED_ADDRESS));
                assertFalse(socket.isClosed());
            }
        }
    }

    static List<Socket> sockets() throws Exception {
        Socket socket = new Socket();
        @SuppressWarnings("resource")
        Socket channelSocket = SocketChannel.open().socket();
        Socket noProxySocket = new Socket(Proxy.NO_PROXY);
        return List.of(socket, channelSocket, noProxySocket);
    }

    private static ServerSocket createEphemeralServerSocket() throws IOException {
        return new ServerSocket(0, 0, InetAddress.getLoopbackAddress());
    }

}
