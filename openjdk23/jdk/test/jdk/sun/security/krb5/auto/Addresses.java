/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8031111 8194486
 * @summary fix krb5 caddr
 * @library /test/lib
 * @compile -XDignore.symbol.file Addresses.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Addresses
 */

import sun.security.krb5.Config;

import javax.security.auth.kerberos.KerberosTicket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Addresses {

    public static void main(String[] args) throws Exception {

        KDC.saveConfig(OneKDC.KRB5_CONF, new OneKDC(null),
                "noaddresses = false",
                "extra_addresses = 10.0.0.10, 10.0.0.11 10.0.0.12");
        Config.refresh();

        KerberosTicket ticket =
                Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false)
                        .s().getPrivateCredentials(KerberosTicket.class)
                        .iterator().next();

        InetAddress loopback = InetAddress.getLoopbackAddress();
        InetAddress extra1 = InetAddress.getByName("10.0.0.10");
        InetAddress extra2 = InetAddress.getByName("10.0.0.11");
        InetAddress extra3 = InetAddress.getByName("10.0.0.12");

        boolean loopbackFound = false;
        boolean extra1Found = false;
        boolean extra2Found = false;
        boolean extra3Found = false;
        boolean networkFound = false;

        for (InetAddress ia: ticket.getClientAddresses()) {
            System.out.println(ia);
            if (ia.equals(loopback)) {
                loopbackFound = true;
                System.out.println("  loopback found");
            } else if (ia.equals(extra1)) {
                extra1Found = true;
                System.out.println("  extra1 found");
            } else if (ia.equals(extra2)) {
                extra2Found = true;
                System.out.println("  extra2 found");
            } else if (ia.equals(extra3)) {
                extra3Found = true;
                System.out.println("  extra3 found");
            } else if (ia instanceof Inet4Address) {
                networkFound = true;
                System.out.println("  another address (" + ia +
                        "), assumed real network");
            }
        }

        if (!loopbackFound || !networkFound
                || !extra1Found || !extra2Found || !extra3Found ) {
            throw new Exception();
        }
    }
}
