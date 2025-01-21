/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The "application" launched by DyamicLoadWarningTest.
 *
 * The application phones home, sends its pid to the test, waits for a reply, then exits.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        InetAddress lh = InetAddress.getLoopbackAddress();
        int port = Integer.parseInt(args[0]);
        try (Socket s = new Socket(lh, port);
             DataOutputStream out = new DataOutputStream(s.getOutputStream())) {

            // send pid
            long pid = ProcessHandle.current().pid();
            out.writeLong(pid);

            // wait for shutdown
            s.getInputStream().read();
        }
    }
}
