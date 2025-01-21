/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8307143
 * @summary CredentialsCache.cacheName should not be static
 * @modules java.security.jgss/sun.security.krb5
 *          java.security.jgss/sun.security.krb5.internal.ccache
 * @library /test/lib
 */

import jdk.test.lib.Asserts;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.ccache.CredentialsCache;

public class TwoFiles {
    public static void main(String[] args) throws Exception {
        PrincipalName pn = new PrincipalName("me@HERE");
        CredentialsCache cc1 = CredentialsCache.create(pn, "cc1");
        CredentialsCache cc2 = CredentialsCache.create(pn, "cc2");
        // name is canonicalized
        Asserts.assertTrue(cc1.cacheName().endsWith("cc1"), cc1.cacheName());
        Asserts.assertTrue(cc2.cacheName().endsWith("cc2"), cc2.cacheName());
    }
}
