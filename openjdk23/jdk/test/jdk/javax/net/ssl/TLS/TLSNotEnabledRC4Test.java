/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8085979
 * @summary Testing TLS engines do not enable RC4 ciphers by default.
 * @library /sun/security/krb5/auto /javax/net/ssl/TLSCommon /test/lib
 * @modules java.security.jgss
 *          java.security.jgss/sun.security.jgss.krb5
 *          java.security.jgss/sun.security.krb5:+open
 *          java.security.jgss/sun.security.krb5.internal:+open
 *          java.security.jgss/sun.security.krb5.internal.ccache
 *          java.security.jgss/sun.security.krb5.internal.crypto
 *          java.security.jgss/sun.security.krb5.internal.ktab
 *          java.base/sun.security.util
 * @run main/othervm -Dtest.security.protocol=TLS TLSNotEnabledRC4Test
 */

/**
 * Testing DTLS engines do not enable RC4 ciphers by default.
 */
public class TLSNotEnabledRC4Test {
    public static void main(String[] args) throws Exception {
        NotEnabledRC4Test.main(args);
    }
}
