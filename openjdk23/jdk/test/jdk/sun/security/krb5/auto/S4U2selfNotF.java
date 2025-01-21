/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8272162
 * @summary S4U2Self ticket without forwardable flag
 * @library /test/lib
 * @compile -XDignore.symbol.file S4U2selfNotF.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts
 *                   -Djdk.security.krb5.s4u2proxy.acceptNonForwardableServiceTicket=true
 *                   S4U2selfNotF
 */

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import jdk.test.lib.Asserts;
import sun.security.jgss.GSSUtil;
import sun.security.krb5.Config;

public class S4U2selfNotF {

    public static void main(String[] args) throws Exception {

        // Create 2 KDCs that has almost the same settings
        OneKDC[] kdcs = new OneKDC[2];
        boolean[] touched = new boolean[2];
        for (int i = 0; i < 2; i++) {
            final int pos = i;
            kdcs[i] = new OneKDC(null) {
                protected byte[] processTgsReq(byte[] in) throws Exception {
                    touched[pos] = true;
                    return super.processTgsReq(in);
                }
            };
            kdcs[i].setOption(KDC.Option.ALLOW_S4U2SELF,
                    List.of(OneKDC.USER + "@" + OneKDC.REALM));
            kdcs[i].setOption(KDC.Option.ALLOW_S4U2PROXY, Map.of(
                    OneKDC.USER + "@" + OneKDC.REALM,
                    List.of(OneKDC.BACKEND + "@" + OneKDC.REALM)));
        }
        kdcs[0].writeJAASConf();

        // except that the 1st issues a non-forwardable S4U2self
        // ticket and only the 2nd accepts it
        kdcs[0].setOption(KDC.Option.S4U2SELF_NOT_FORWARDABLE, true);
        kdcs[1].setOption(KDC.Option.S4U2SELF_ALLOW_NOT_FORWARDABLE, true);

        Files.write(Path.of(OneKDC.KRB5_CONF), String.format("""
                [libdefaults]
                default_realm = RABBIT.HOLE
                forwardable = true
                default_keytab_name = localkdc.ktab

                [realms]
                RABBIT.HOLE = {
                    kdc = kdc.rabbit.hole:%d kdc.rabbit.hole:%d
                }
                """, kdcs[0].getPort(), kdcs[1].getPort())
                .getBytes(StandardCharsets.UTF_8));
        Config.refresh();

        Context c = Context.fromJAAS("client");
        c = c.impersonate(OneKDC.USER2);
        c.startAsClient(OneKDC.BACKEND, GSSUtil.GSS_KRB5_MECH_OID);
        c.take(new byte[0]);

        Asserts.assertTrue(touched[0]);     // get S4U2self from 1st one
        Asserts.assertTrue(touched[1]);     // get S4U2proxy from 2nd one
    }
}
