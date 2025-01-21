/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6894072 8004488 8194486
 * @summary always refresh keytab
 * @library /test/lib
 * @compile -XDignore.symbol.file KeyTabCompat.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts KeyTabCompat
 */

import javax.security.auth.kerberos.KerberosKey;
import sun.security.jgss.GSSUtil;

/*
 * There are 2 compat issues to check:
 *
 * 1. If there is only KerberosKeys in private credential set and no
 *    KerberosPrincipal. JAAS login should go on.
 * 2. If KeyTab is used, user won't get KerberosKeys from
 *    private credentials set.
 */
public class KeyTabCompat {

    public static void main(String[] args)
            throws Exception {
        OneKDC kdc = new OneKDC("aes128-cts");
        kdc.writeJAASConf();
        kdc.addPrincipal(OneKDC.SERVER, "pass1".toCharArray());
        kdc.writeKtab(OneKDC.KTAB);

        Context c, s;

        // Part 1
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
        s = Context.fromUserPass(OneKDC.USER2, OneKDC.PASS2, true);

        s.s().getPrincipals().clear();

        c.startAsClient(OneKDC.USER2, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        // Part 2
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);
        s.status();

        if (s.s().getPrivateCredentials(KerberosKey.class).size() != 0) {
            throw new Exception("There should be no KerberosKey");
        }
    }
}
