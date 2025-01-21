/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8000653 8194486
 * @summary SPNEGO tests fail at context.getDelegCred().getRemainingInitLifetime(mechOid)
 * @library /test/lib
 * @compile -XDignore.symbol.file SpnegoLifeTime.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts SpnegoLifeTime
 */

import org.ietf.jgss.Oid;
import org.ietf.jgss.GSSCredential;
import sun.security.jgss.GSSUtil;

public class SpnegoLifeTime {

    public static void main(String[] args) throws Exception {

        Oid oid = GSSUtil.GSS_SPNEGO_MECH_OID;
        new OneKDC(null).writeJAASConf();

        Context c, s;
        c = Context.fromJAAS("client");
        s = Context.fromJAAS("server");

        c.startAsClient(OneKDC.SERVER, oid);
        c.x().requestCredDeleg(true);
        s.startAsServer(oid);

        Context.handshake(c, s);

        GSSCredential cred = s.delegated().cred();
        cred.getRemainingInitLifetime(oid);
        cred.getUsage(oid);
    }
}

