/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505016
 * @summary Socket spec should clarify what getInetAddress/getPort/etc return after the Socket is closed
 */

import java.net.*;
import java.io.*;

public class TestAfterClose
{
    static int failCount;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(0, 0, null);
            test(ss);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (failCount > 0)
            throw new RuntimeException("Failed: failcount = " + failCount);

    }

    static void test(ServerSocket ss) throws IOException {
        //Before Close
        InetAddress ssInetAddress = ss.getInetAddress();
        int ssLocalPort = ss.getLocalPort();
        SocketAddress ssLocalSocketAddress = ss.getLocalSocketAddress();

        //After Close
        ss.close();

        if (ssLocalPort != ss.getLocalPort()) {
            System.out.println("ServerSocket.getLocalPort failed");
            failCount++;
        }

        if (!ss.getInetAddress().equals(ssInetAddress)) {
            System.out.println("ServerSocket.getInetAddress failed");
            failCount++;
        }

        if (!ss.getLocalSocketAddress().equals(ssLocalSocketAddress)) {
            System.out.println("ServerSocket.getLocalSocketAddress failed");
            failCount++;
        }

        if (!ss.isBound()) {
            System.out.println("ServerSocket.isBound failed");
            failCount++;
        }

    }
}
