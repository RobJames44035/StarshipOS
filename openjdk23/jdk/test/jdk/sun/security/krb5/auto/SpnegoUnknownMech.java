/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8186186 8209829
 * @library /test/lib
 * @compile -XDignore.symbol.file SpnegoUnknownMech.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm/fail -Djdk.net.hosts.file=TestHosts SpnegoUnknownMech
 */

import sun.security.jgss.GSSUtil;

public class SpnegoUnknownMech {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();

        Context c, s;
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_SPNEGO_MECH_OID);
        s.startAsServer(GSSUtil.GSS_SPNEGO_MECH_OID);

        byte[] init = c.take(new byte[0]);
        // Modify the krb5 mech OID inside NegTokenInit to something else
        init[0x20] = 10;
        s.take(init);

        s.dispose();
        c.dispose();
    }
}
