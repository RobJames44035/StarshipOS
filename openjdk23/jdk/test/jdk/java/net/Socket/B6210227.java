/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug 6210227
 * @library /test/lib
 * @summary  REGRESSION: Socket.getLocalAddress() returns address of 0.0.0.0 on outbound TCP.
 *           This test requires binding to the wildcard address.
 * @run main B6210227
 * @run main/othervm -Djava.net.preferIPv4Stack=true B6210227
 * @run main/othervm -Djava.net.preferIPv6Addresses=true B6210227
 */

import java.util.*;
import java.net.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import jdk.test.lib.net.IPSupport;

public class B6210227 {
    public static void main(String[] args) throws Exception
    {
        IPSupport.throwSkippedExceptionIfNonOperational();

        ServerSocket ss = new ServerSocket(0);
        int port = ss.getLocalPort();

        try {
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), port);
            Socket s = new Socket();
            s.connect( isa, 1000 );
            InetAddress iaLocal = s.getLocalAddress(); // if this comes back as 0.0.0.0 this would demonstrate issue
            String      sLocalHostname = iaLocal.getHostName();
            byte[]      address = iaLocal.getAddress();
            if (isWildcard(address)) {
                if (iaLocal instanceof Inet6Address) {
                    String msg = IntStream.range(0, address.length)
                        .mapToObj(i -> "0").collect(Collectors.joining(":"));
                    throw new RuntimeException(msg + " returned");
                } else {
                    throw new RuntimeException ("0.0.0.0 returned");
                }
            }
            System.out.println("local hostname is "+sLocalHostname );
        } catch(Exception e) {
            System.out.println("Exception happened");
            throw e;
        } finally {
            ss.close();
        }
    }

    private static boolean isWildcard(byte[] bytes) {
        for (int i = 0; i < bytes.length ; i++) {
            if (bytes[i] != 0) return false;
        }
        return true;
    }
}
