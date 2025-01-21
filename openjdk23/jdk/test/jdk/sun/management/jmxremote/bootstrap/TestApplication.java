/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * A test "application" used by unit tests -
 *   LocalManagementTest.java, CustomLauncherTest.java.
 * This application binds to some random port, prints the port number
 * to standard output, waits for somebody to connect, and then shuts down.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestApplication {
    public static void main(String[] args) throws IOException {
        // Some tests require the application to exit immediately
        if (args.length > 0 && args[0].equals("-exit")) {
            return;
        }

        // bind to a random port
        ServerSocket ss = new ServerSocket(0);
        int port = ss.getLocalPort();

        // signal test that we are started - do not remove these lines!!
        System.out.println("port:" + port);
        System.out.println("waiting for the manager ...");
        System.out.flush();

        // wait for manager to connect
        Socket s = ss.accept();
        s.close();
        ss.close();
    }
}
