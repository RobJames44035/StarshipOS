/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8005460
 * @summary [findbugs] Probably returned array should be cloned
 * @modules java.security.jgss/sun.security.krb5
 */

import sun.security.krb5.PrincipalName;

public class Immutable {
    public static void main(String[] args) throws Exception {
        PrincipalName pn1 = new PrincipalName("host/service@REALM");
        PrincipalName pn2 = (PrincipalName)pn1.clone();
        pn1.getNameStrings()[0] = "http";
        if (!pn1.equals(pn2)) {
            throw new Exception();
        }
    }
}
