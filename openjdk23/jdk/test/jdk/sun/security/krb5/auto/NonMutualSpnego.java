/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6733095 8194486
 * @summary Failure when SPNEGO request non-Mutual
 * @library /test/lib
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts NonMutualSpnego
 */

import sun.security.jgss.GSSUtil;

public class NonMutualSpnego {

    public static void main(String[] args)
            throws Exception {

        // Create and start the KDC
        new OneKDC(null).writeJAASConf();
        new NonMutualSpnego().go();
    }

    void go() throws Exception {
        Context c = Context.fromJAAS("client");
        Context s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_SPNEGO_MECH_OID);
        c.x().requestMutualAuth(false);
        s.startAsServer(GSSUtil.GSS_SPNEGO_MECH_OID);

        Context.handshake(c, s);

        Context.transmit("i say high --", c, s);
        Context.transmit("   you say low", s, c);

        c.dispose();
        s.dispose();
    }
}
