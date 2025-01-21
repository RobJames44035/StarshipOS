/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7089889 8194486
 * @summary Krb5LoginModule.login() throws an exception if used without a keytab
 * @library /test/lib
 * @compile -XDignore.symbol.file NoInitNoKeytab.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts NoInitNoKeytab
 */

import java.io.FileOutputStream;
import sun.security.jgss.GSSUtil;

// The basic krb5 test skeleton you can copy from
public class NoInitNoKeytab {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();
        try (FileOutputStream fos =
                new FileOutputStream(OneKDC.JAAS_CONF, true)) {
            fos.write((
                "noinit {\n" +
                "    com.sun.security.auth.module.Krb5LoginModule required\n" +
                "    principal=\"" + OneKDC.USER + "\"\n" +
                "    useKeyTab=false\n" +
                "    isInitiator=false\n" +
                "    storeKey=true;\n};\n").getBytes());
        }
        Context c, s;
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("noinit");

        c.startAsClient(OneKDC.USER, GSSUtil.GSS_SPNEGO_MECH_OID);
        s.startAsServer(GSSUtil.GSS_SPNEGO_MECH_OID);

        Context.handshake(c, s);

        Context.transmit("i say high --", c, s);
        Context.transmit("   you say low", s, c);

        s.dispose();
        c.dispose();
    }
}
