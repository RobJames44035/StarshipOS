/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.test.lib.Asserts;
import sun.security.krb5.PrincipalName;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/*
 * @test
 * @bug 8210821
 * @summary Support dns_canonicalize_hostname in krb5.conf
 * @library /test/lib
 * @compile -XDignore.symbol.file DnsCanonicalizeHostname.java
 * @run main jdk.test.lib.FileInstaller dns_canonicalize_hostname.hosts hosts
 * @run main/othervm -Djdk.net.hosts.file=hosts DnsCanonicalizeHostname none
 * @run main/othervm -Djdk.net.hosts.file=hosts DnsCanonicalizeHostname true
 * @run main/othervm -Djdk.net.hosts.file=hosts DnsCanonicalizeHostname false
 */
public class DnsCanonicalizeHostname {

    // In dns_canonicalize_hostname.hosts, all "dummy.example.com", "dummy",
    // and "bogus" are resolved to 127.0.0.1. Since "dummy.example.com" is on
    // the first line, it is returned at the reverse lookup.

    public static void main(String[] args) throws Exception {

        Files.write(Path.of("krb5.conf"), List.of(
                "[libdefaults]",
                "default_realm = R",
                args[0].equals("none")
                        ? "# empty line"
                        : "dns_canonicalize_hostname = " + args[0],
                "",
                "[realms]",
                "R = {",
                "    kdc = 127.0.0.1",
                "}"
        ));
        System.setProperty("java.security.krb5.conf", "krb5.conf");

        String n1 = new PrincipalName("host/dummy", PrincipalName.KRB_NT_SRV_HST)
                .getNameStrings()[1];
        String n2 = new PrincipalName("host/bogus", PrincipalName.KRB_NT_SRV_HST)
                .getNameStrings()[1];

        switch (args[0]) {
            case "none":
            case "true":
                Asserts.assertEQ(n1, "dummy.example.com");
                Asserts.assertEQ(n2, "bogus");
                break;
            case "false":
                Asserts.assertEQ(n1, "dummy");
                Asserts.assertEQ(n2, "bogus");
                break;
        }
    }
}
