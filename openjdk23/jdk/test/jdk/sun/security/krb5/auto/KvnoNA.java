/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7197159 8194486
 * @summary accept different kvno if there no match
 * @library /test/lib
 * @compile -XDignore.symbol.file KvnoNA.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts KvnoNA
 */

import org.ietf.jgss.GSSException;
import sun.security.jgss.GSSUtil;
import sun.security.krb5.KrbException;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.ktab.KeyTab;
import sun.security.krb5.internal.Krb5;

public class KvnoNA {

    public static void main(String[] args)
            throws Exception {

        OneKDC kdc = new OneKDC(null);
        kdc.writeJAASConf();

        // In KDC, it's 2
        char[] pass = "pass2".toCharArray();
        kdc.addPrincipal(OneKDC.SERVER, pass);

        // In ktab, kvno is 1 or 3, 3 has the same password
        KeyTab ktab = KeyTab.create(OneKDC.KTAB);
        PrincipalName p = new PrincipalName(
            OneKDC.SERVER+"@"+OneKDC.REALM, PrincipalName.KRB_NT_SRV_HST);
        ktab.addEntry(p, "pass1".toCharArray(), 1, true);
        ktab.addEntry(p, "pass2".toCharArray(), 3, true);
        ktab.save();

        Context c, s;

        c = Context.fromUserPass("dummy", "bogus".toCharArray(), false);
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        s.dispose();
        c.dispose();
    }
}
