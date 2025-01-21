/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505016
 * @summary Socket spec should clarify what getInetAddress/getPort/etc return after the Socket is closed
 * @run main TestAfterClose
 */

import java.net.*;
import java.io.*;

public class TestAfterClose
{
    static int failCount;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getLoopbackAddress(), 5001);
            test(socket, true);

            socket = new DatagramSocket();
            test(socket, false);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (failCount > 0)
            throw new RuntimeException("Failed: failcount = " + failCount);

    }

    static void test(DatagramSocket socket, boolean connected) throws IOException {
        //Before Close
        int socketPort = socket.getPort();
        InetAddress socketInetAddress = socket.getInetAddress();
        SocketAddress socketRemoteSocketAddress = socket.getRemoteSocketAddress();

        //After Close
        socket.close();

        if (connected ? !(socket.getPort() == socketPort)
                      : !(socket.getPort() == -1)) {
            System.out.println("Socket.getPort failed");
            failCount++;
        }

        if (connected ? !socket.getInetAddress().equals(socketInetAddress)
                      : !(socket.getInetAddress() == null)) {
            System.out.println("Socket.getInetAddress failed");
            failCount++;
        }

        if (connected ? !socket.getRemoteSocketAddress().equals(socketRemoteSocketAddress)
                      : !(socket.getRemoteSocketAddress() == null)) {
            System.out.println("Socket.getRemoteSocketAddresss failed");
            failCount++;
        }

        if (socket.getLocalPort() != -1) {
            System.out.println("Socket.getLocalPort failed");
            failCount++;
        }

        if (socket.getLocalAddress() != null) {
            System.out.println("Socket.getLocalAddress failed");
            failCount++;
        }

        if (socket.getLocalSocketAddress() != null) {
            System.out.println("Socket.getLocalSocketAddress failed");
            failCount++;
        }

        if (connected && !socket.isConnected()) {
            System.out.println("Socket.isConnected failed");
            failCount++;
        }

        if (!socket.isBound()) {
            System.out.println("Socket.isBound failed");
            failCount++;
        }

    }
}
