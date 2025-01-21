/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6770883
 * @modules java.security.jgss/sun.security.jgss
 * @run main/othervm NoSpnegoAsDefMech
 * @summary Infinite loop if SPNEGO specified as sun.security.jgss.mechanism
 */

import org.ietf.jgss.*;
import sun.security.jgss.*;

public class NoSpnegoAsDefMech {

    public static void main(String[] argv) throws Exception {
        System.setProperty("sun.security.jgss.mechanism", GSSUtil.GSS_SPNEGO_MECH_OID.toString());
        try {
            GSSManager.getInstance().createName("service@localhost", GSSName.NT_HOSTBASED_SERVICE, new Oid("1.3.6.1.5.5.2"));
        } catch (GSSException e) {
            // This is OK, for example, krb5.conf is missing or other problems
        }
    }
}
