/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7152176 8194486
 * @summary More krb5 tests
 * @library /test/lib
 * @compile -XDignore.symbol.file GSS.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts GSS
 */

import sun.security.jgss.GSSUtil;

// Testing JGSS without JAAS
public class GSS {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();

        Context c, s;
        c = Context.fromThinAir();
        s = Context.fromThinAir();

        // This is the only setting needed for JGSS without JAAS. The default
        // JAAS config entries are already created by OneKDC.
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        Context.transmit("i say high --", c, s);
        Context.transmit("   you say low", s, c);

        s.dispose();
        c.dispose();
    }
}
