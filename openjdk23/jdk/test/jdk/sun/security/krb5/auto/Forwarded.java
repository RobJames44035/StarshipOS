/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8031111 8194486
 * @summary fix krb5 caddr
 * @library /test/lib
 * @compile -XDignore.symbol.file Forwarded.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts Forwarded
 */

import sun.security.jgss.GSSUtil;
import sun.security.krb5.internal.KDCOptions;
import sun.security.krb5.internal.KDCReqBody;
import sun.security.krb5.internal.TGSReq;

public class Forwarded {

    public static void main(String[] args) throws Exception {

        new OneKDC(null).setOption(KDC.Option.CHECK_ADDRESSES, true);

        Context c;
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);

        c.startAsClient(OneKDC.SERVER, GSSUtil.GSS_KRB5_MECH_OID);
        c.x().requestCredDeleg(true);

        c.take(new byte[0]);
    }
}
