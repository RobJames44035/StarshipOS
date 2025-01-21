/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7077640 8194486
 * @summary gss wrap for cfx doesn't handle rrc != 0
 * @library /test/lib
 * @compile -XDignore.symbol.file RRC.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts RRC
 */

import java.util.Arrays;
import sun.security.jgss.GSSUtil;

// The basic krb5 test skeleton you can copy from
public class RRC {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();

        Context c, s;
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_SPNEGO_MECH_OID);
        s.startAsServer(GSSUtil.GSS_SPNEGO_MECH_OID);

        Context.handshake(c, s);

        byte[] msg = "i say high --".getBytes();
        byte[] wrapped = c.wrap(msg, false);

        // Simulate RRC equals to EC
        int rrc = wrapped[5];
        byte[] rotated = new byte[wrapped.length];
        System.arraycopy(wrapped, 0, rotated, 0, 16);
        System.arraycopy(wrapped, wrapped.length-rrc, rotated, 16, rrc);
        System.arraycopy(wrapped, 16, rotated, 16+rrc, wrapped.length-16-rrc);
        rotated[7] = (byte)rrc;

        byte[] unwrapped = s.unwrap(rotated, false);
        if (!Arrays.equals(msg, unwrapped)) {
            throw new Exception("Failure");
        }

        s.dispose();
        c.dispose();
    }
}
