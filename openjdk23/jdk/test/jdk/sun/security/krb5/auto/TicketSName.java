/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8178794 8194486
 * @summary krb5 client should ignore sname in incoming tickets
 * @library /test/lib
 * @compile -XDignore.symbol.file TicketSName.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts -Dtest.kdc.diff.sname
 *      TicketSName
 */

import sun.security.jgss.GSSUtil;
import javax.security.auth.kerberos.KerberosTicket;

public class TicketSName {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).writeJAASConf();

        Context c, s;
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        String expected = OneKDC.SERVER + "@" + OneKDC.REALM;
        if (!c.s().getPrivateCredentials(KerberosTicket.class)
                .stream()
                .anyMatch(t -> t.getServer().toString().equals(expected))) {
            c.status();
            throw new Exception("no " + expected);
        }
    }
}
