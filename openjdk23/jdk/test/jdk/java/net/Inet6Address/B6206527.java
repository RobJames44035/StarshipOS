/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/**
 * @test 1.1 05/01/05
 * @bug 6206527
 * @summary "cannot assign address" when binding ServerSocket on Suse 9
 * @library /test/lib
 * @build jdk.test.lib.NetworkConfiguration
 *        jdk.test.lib.Platform
 * @run main B6206527
 */

import java.net.*;
import java.util.*;
import jdk.test.lib.NetworkConfiguration;

public class B6206527 {

    public static void main (String[] args) throws Exception {
        Inet6Address addr = getLocalAddr();
        if (addr == null) {
            System.out.println("Could not find a link-local address");
            return;
        }

        try (ServerSocket ss = new ServerSocket()) {
            System.out.println("trying LL addr: " + addr);
            ss.bind(new InetSocketAddress(addr, 0));
        }

        // need to remove the %scope suffix
        addr = (Inet6Address) InetAddress.getByAddress (
            addr.getAddress()
        );

        try (ServerSocket ss = new ServerSocket()) {
            System.out.println("trying LL addr: " + addr);
            ss.bind(new InetSocketAddress(addr, 0));
        }
    }

    public static Inet6Address getLocalAddr() throws Exception {
        Optional<Inet6Address> oaddr = NetworkConfiguration.probe()
                .ip6Addresses()
                .filter(a -> a.isLinkLocalAddress())
                .findFirst();

        return oaddr.orElseGet(() -> null);
    }
}
