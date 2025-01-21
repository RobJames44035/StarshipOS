/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @summary Ensure that java.rmi.Naming.lookup can handle URLs containing
 *          IPv6 addresses.
 * @bug 4402708
 * @library ../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build RegistryVM
 * @run main/othervm -Djava.net.preferIPv6Addresses=true LookupIPv6
 */

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Inet6Address;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;

public class LookupIPv6 {
    public static void main(String[] args) throws Exception {
        // use loopback IPv6 address to avoid lengthy socket connection delays
        String[] urls = {
            "rmi://[0000:0000:0000:0000:0000:0000:0000:0001]/foo",
            "//[0:0:0:0:0:0:0:1]:88/foo",
            "rmi://[0::0:0:0:1]/foo:bar",
            "//[::1]:88"
        };
        for (int i = 0; i < urls.length; i++) {
            try {
                Naming.lookup(urls[i]);
            } catch (MalformedURLException ex) {
                throw ex;
            } catch (Exception ex) {
                // URLs are bogus, lookups expected to fail
            }
        }

        /* Attempt to use IPv6-based URL to look up object in local registry.
         * Since not all platforms support IPv6, this portion of the test may
         * be a no-op in some cases.  On supporting platforms, the first
         * element of the array returned by InetAddress.getAllByName should be
         * an Inet6Address since this test is run with
         * -Djava.net.preferIPv6Addresses=true.
         */
        InetAddress localAddr = InetAddress.getAllByName(null)[0];
        if (localAddr instanceof Inet6Address) {
            System.out.println("IPv6 detected");
            RegistryVM rvm = RegistryVM.createRegistryVM();
            try {
                rvm.start();
                String name = String.format("rmi://[%s]:%d/foo",
                        localAddr.getHostAddress(), rvm.getPort());
                Naming.rebind(name, new R());
                Naming.lookup(name);
            } finally {
                rvm.cleanup();
            }
        }
    }

    private static class R implements Remote, Serializable { }
}
