/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8001326 8194486
 * @summary the replaycache type none cannot stop an authenticator replay,
 * but it can stop a message replay when s.s.k.acceptor.subkey is true.
 * You should not really use none in production environment. This test merely
 * shows there can be other protections when replay cache is not working fine.
 * @library /test/lib
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts NoneReplayCacheTest
 */

import org.ietf.jgss.GSSException;
import sun.security.jgss.GSSUtil;

public class NoneReplayCacheTest {

    public static void main(String[] args)
            throws Exception {

        new OneKDC(null);

        System.setProperty("sun.security.krb5.rcache", "none");
        System.setProperty("sun.security.krb5.acceptor.subkey", "true");

        Context c, s;
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
        s = Context.fromUserKtab(OneKDC.SERVER, OneKDC.KTAB, true);

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        byte[] first = c.take(new byte[0]);

        c.take(s.take(first));

        byte[] msg = c.wrap("hello".getBytes(), true);
        s.unwrap(msg, true);

        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);
        s.take(first);  // apreq replay not detectable
        try {
            s.unwrap(msg, true);    // msg replay detectable
            throw new Exception("This method should fail");
        } catch (GSSException gsse) {
            gsse.printStackTrace();
        }
    }
}
