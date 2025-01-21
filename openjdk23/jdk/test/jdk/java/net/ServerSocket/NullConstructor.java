/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8223457
 * @run main NullConstructor
 * @summary java.net.ServerSocket protected constructor should throw NPE if impl null
 */

import java.net.ServerSocket;
import java.net.SocketImpl;

public class NullConstructor {

    public static void main(String args[]) throws Exception {
        try {
            ServerSocket server = new ServerSocket((SocketImpl)null) {};
            throw new RuntimeException("Test failed");
        } catch (NullPointerException ee) {}
    }
}
