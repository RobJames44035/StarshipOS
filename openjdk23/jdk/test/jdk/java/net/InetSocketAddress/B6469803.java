/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6469803
 * @summary Socket creation on Windows takes a long time if web proxy does not have a DNS
 */

import java.net.*;

public class B6469803 {
    public static void main(String[] args) {
        InetSocketAddress addr = new InetSocketAddress("192.168.1.1", 12345);
        String s = addr.getHostString();
        if (!s.equals("192.168.1.1"))
            throw new RuntimeException("getHostString() returned the wrong string: " + s );
        addr = new InetSocketAddress("localhost", 12345);
        s = addr.getHostString();
        if (!s.equals("localhost"))
            throw new RuntimeException("getHostString() returned the wrong string: " + s);
    }
}
