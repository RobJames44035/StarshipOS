/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8062744
 * @modules jdk.net
 * @run main SupportedOptions
 */

import java.net.*;
import java.io.IOException;
import jdk.net.*;

public class SupportedOptions {

    public static void main(String[] args) throws Exception {
        if (!Sockets.supportedOptions(ServerSocket.class)
              .contains(StandardSocketOptions.IP_TOS)) {
            throw new RuntimeException("Test failed");
        }
        // Now set the option
        ServerSocket ss = new ServerSocket();
        if (!ss.supportedOptions().contains(StandardSocketOptions.IP_TOS)) {
            throw new RuntimeException("Test failed");
        }
        Sockets.setOption(ss, java.net.StandardSocketOptions.IP_TOS, 128);
    }
}
