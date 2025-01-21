/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package java.net;

import java.net.spi.InetAddressResolver.LookupPolicy;

public class NullCharInHostname {
    public static void main(String[] args) {
        var name = "foo\u0000bar";
        System.out.println("file.encoding = " + System.getProperty("file.encoding"));
        System.out.println("native.encoding = " + System.getProperty("native.encoding"));

        // This should throw IAE as it calls the internal impl
        try {
            var impl = new Inet6AddressImpl();
            var addrs = impl.lookupAllHostAddr(name, LookupPolicy.of(LookupPolicy.IPV4));
        } catch (UnknownHostException e0) {
            throw new RuntimeException(e0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // This should throw UHE as before and not IAE for compatibility
        try {
            var addrs = InetAddress.getByName(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e0) {
            e0.printStackTrace();
        }
    }
}
