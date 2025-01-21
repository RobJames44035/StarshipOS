/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8043758
 * @summary Testing DTLS engines re-handshaking using each of the supported
 *          cipher suites.
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
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=norm DTLSRehandshakeTest
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=norm_sni DTLSRehandshakeTest
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=krb DTLSRehandshakeTest
 */

/**
 * Testing DTLS engines re-handshaking using each of the supported cipher
 * suites.
 */
public class DTLSRehandshakeTest {
    public static void main(String[] args) {
        RehandshakeTest.main(args);
    }
}
