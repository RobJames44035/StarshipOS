/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8131051 8194486 8187218
 * @summary KDC might issue a renewable ticket even if not requested
 * @library /test/lib
 * @compile -XDignore.symbol.file LongLife.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts LongLife
 */

import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;
import sun.security.krb5.Config;
import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosTicket;

public class LongLife {

    public static void main(String[] args) throws Exception {

        OneKDC kdc = new OneKDC(null).writeJAASConf();

        test(kdc, "10h", false, 36000, false);
        test(kdc, "2d", false, KDC.DEFAULT_LIFETIME, true);
        test(kdc, "2d", true, 2 * 24 * 3600, false);

        // 8187218: getRemainingLifetime() is negative if lifetime
        // is longer than 30 days.
        test(kdc, "30d", true, 30 * 24 * 3600, false);
    }

    static void test(
            KDC kdc,
            String ticketLifetime,
            boolean forceTill, // if true, KDC will not try RENEWABLE
            int expectedLifeTime,
            boolean expectedRenewable) throws Exception {

        KDC.saveConfig(OneKDC.KRB5_CONF, kdc,
                "ticket_lifetime = " + ticketLifetime);
        Config.refresh();

        if (forceTill) {
            System.setProperty("test.kdc.force.till", "");
        } else {
            System.clearProperty("test.kdc.force.till");
        }

        Context c = Context.fromJAAS("client");

        GSSCredential cred = Subject.callAs(c.s(),
                ()-> {
                    GSSManager m = GSSManager.getInstance();
                    return m.createCredential(GSSCredential.INITIATE_ONLY);
                });

        KerberosTicket tgt = c.s().getPrivateCredentials(KerberosTicket.class)
                .iterator().next();
        System.out.println(tgt);

        int actualLifeTime = cred.getRemainingLifetime();
        if (actualLifeTime < expectedLifeTime - 60
                || actualLifeTime > expectedLifeTime + 60) {
            throw new Exception("actualLifeTime is " + actualLifeTime);
        }

        if (tgt.isRenewable() != expectedRenewable) {
            throw new Exception("TGT's RENEWABLE flag is " + tgt.isRenewable());
        }
    }
}
