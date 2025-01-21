/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8058290 8194486
 * @summary JAAS Krb5LoginModule has suspect ticket-renewal logic,
 *          relies on clockskew grace
 * @library /test/lib
 * @compile -XDignore.symbol.file Renew.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Renew 1
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Renew 2
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Renew 3
 */

import sun.security.krb5.Config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import javax.security.auth.kerberos.KerberosTicket;

public class Renew {

    public static void main(String[] args) throws Exception {

        // Three test cases:
        // 1. renewTGT=false
        // 2. renewTGT=true with a short life time, renew will happen
        // 3. renewTGT=true with a long life time, renew won't happen
        int test = Integer.parseInt(args[0]);

        OneKDC k = new OneKDC(null);
        KDC.saveConfig(OneKDC.KRB5_CONF, k,
                "renew_lifetime = 1d",
                "ticket_lifetime = " + (test == 2? "10s": "8h"));
        Config.refresh();
        k.writeJAASConf();

        // KDC would save ccache in a file
        System.setProperty("test.kdc.save.ccache", "cache.here");

        Files.write(Paths.get(OneKDC.JAAS_CONF), Arrays.asList(
                "first {",
                "   com.sun.security.auth.module.Krb5LoginModule required;",
                "};",
                "second {",
                "   com.sun.security.auth.module.Krb5LoginModule required",
                "   doNotPrompt=true",
                "   renewTGT=" + (test != 1),
                "   useTicketCache=true",
                "   ticketCache=cache.here;",
                "};"
        ));

        Context c;

        // The first login uses username and password
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
        Date d1 = c.s().getPrivateCredentials(KerberosTicket.class).iterator().next().getAuthTime();

        // 6s is longer than half of 10s
        Date expiring = new Date(d1.getTime() + 6000);
        while (new Date().before(expiring)) {
            Thread.sleep(500);
        }

        // The second login uses the cache
        c = Context.fromJAAS("second");
        Date d2 = c.s().getPrivateCredentials(KerberosTicket.class).iterator().next().getAuthTime();

        if (test == 2) {
            if (d1.equals(d2)) {
                throw new Exception("Ticket not renewed");
            }
        } else {
            if (!d1.equals(d2)) {
                throw new Exception("Ticket renewed");
            }
        }
    }
}
