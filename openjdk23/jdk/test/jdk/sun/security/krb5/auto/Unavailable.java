/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8274205
 * @summary Handle KDC_ERR_SVC_UNAVAILABLE error code from KDC
 * @library /test/lib
 * @compile -XDignore.symbol.file Unavailable.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Unavailable
 */

import sun.security.krb5.Config;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.KRBError;
import sun.security.krb5.internal.KerberosTime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Unavailable {

    public static void main(String[] args) throws Exception {

        // Good KDC
        KDC kdc1 = KDC.create(OneKDC.REALM);
        kdc1.addPrincipal(OneKDC.USER, OneKDC.PASS);
        kdc1.addPrincipalRandKey("krbtgt/" + OneKDC.REALM);

        // The "not available" KDC
        KDC kdc2 = new KDC(OneKDC.REALM, "kdc." + OneKDC.REALM.toLowerCase(Locale.US), 0, true) {
            @Override
            protected byte[] processAsReq(byte[] in) throws Exception {
                KRBError err = new KRBError(null, null, null,
                        KerberosTime.now(), 0,
                        29, // KDC_ERR_SVC_UNAVAILABLE
                        null, new PrincipalName("krbtgt/" + OneKDC.REALM),
                        null, null);
                return err.asn1Encode();
            }
        };

        Files.write(Path.of(OneKDC.KRB5_CONF), String.format("""
                [libdefaults]
                default_realm = RABBIT.HOLE

                [realms]
                RABBIT.HOLE = {
                    kdc = kdc.rabbit.hole:%d
                    kdc = kdc.rabbit.hole:%d
                }
                """, kdc2.getPort(), kdc1.getPort()).getBytes());
        System.setProperty("java.security.krb5.conf", OneKDC.KRB5_CONF);
        Config.refresh();

        Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
    }
}
