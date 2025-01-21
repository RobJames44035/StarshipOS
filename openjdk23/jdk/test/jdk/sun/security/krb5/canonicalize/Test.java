/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
/*
 * @test
 * @bug 6682516 8149521
 * @summary SPNEGO_HTTP_AUTH/WWW_KRB and SPNEGO_HTTP_AUTH/WWW_SPNEGO failed on all non-windows platforms
 * @modules java.security.jgss/sun.security.krb5
 * @run main/othervm -Djdk.net.hosts.file=${test.src}/TestHosts
 *      -Djava.security.krb5.realm=THIS.REALM
 *      -Djava.security.krb5.kdc=localhost
 *      -Djava.security.krb5.conf=krb5.conf Test
 */

import sun.security.krb5.PrincipalName;

public class Test {
    public static void main(String[] args) throws Exception {
        // add using canonicalized name
        check("c1", "c1.this.domain");
        check("c1.this", "c1.this.domain");
        check("c1.this.domain", "c1.this.domain");
        check("c1.this.domain.", "c1.this.domain");

        // canonicalized name goes IP, reject
        check("c2", "c2");
        check("c2.", "c2");

        // canonicalized name goes strange, reject
        check("c3", "c3");

        // unsupported
        check("c4", "c4");
    }

    static void check(String input, String output) throws Exception {
        System.out.println(input + " -> " + output);
        PrincipalName pn = new PrincipalName("host/"+input,
                PrincipalName.KRB_NT_SRV_HST);
        if (!pn.getNameStrings()[1].equals(output)) {
            throw new Exception("Output is " + pn);
        }
    }
}
