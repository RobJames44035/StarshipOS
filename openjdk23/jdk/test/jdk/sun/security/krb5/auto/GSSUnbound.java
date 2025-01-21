/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8001104 8194486
 * @summary Unbound SASL service: the GSSAPI/krb5 mech
 * @library /test/lib
 * @compile -XDignore.symbol.file GSSUnbound.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts GSSUnbound
 */

import java.security.Security;
import sun.security.jgss.GSSUtil;

// Testing JGSS without JAAS
public class GSSUnbound {

    public static void main(String[] args) throws Exception {

        new OneKDC(null);

        Context c, s;
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
        s = Context.fromThinAir();

        // This is the only setting needed for JGSS without JAAS. The default
        // JAAS config entries are already created by OneKDC.
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        c.startAsClient(OneKDC.BACKEND, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        Context.transmit("i say high --", c, s);
        Context.transmit("   you say low", s, c);

        s.dispose();
        c.dispose();
    }
}
