/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8085979
 * @summary Testing TLS engines handshake using each of the supported
 *          cipher suites with different maximum fragment length. Testing of
 *          MFLN extension.
 * @library /sun/security/krb5/auto /javax/net/ssl/TLSCommon /test/lib
 * @modules java.security.jgss
 *          jdk.security.auth
 *          java.security.jgss/sun.security.jgss.krb5
 *          java.security.jgss/sun.security.krb5:+open
 *          java.security.jgss/sun.security.krb5.internal:+open
 *          java.security.jgss/sun.security.krb5.internal.ccache
 *          java.security.jgss/sun.security.krb5.internal.crypto
 *          java.security.jgss/sun.security.krb5.internal.ktab
 *          java.base/sun.security.util
 * @run main/othervm -Dtest.security.protocol=TLSv1.1 -Dtest.mode=norm TLSMFLNTest
 * @run main/othervm -Dtest.security.protocol=TLSv1.1 -Dtest.mode=norm_sni TLSMFLNTest
 * @run main/othervm -Dtest.security.protocol=TLSv1.1 -Dtest.mode=krb TLSMFLNTest
 */

/**
 * Testing TLS engines handshake using each of the supported cipher suites with
 * different maximum fragment length. Testing of MFLN extension.
 */
public class TLSMFLNTest {
    public static void main(String[] args) {
        MFLNTest.main(args);
    }
}
