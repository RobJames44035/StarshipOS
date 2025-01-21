/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8043758
 * @summary Testing DTLS engines handshake using each of the supported
 *          cipher suites with replicated packets check.
 * @library /sun/security/krb5/auto /javax/net/ssl/TLSCommon /javax/net/ssl/DTLS /test/lib
 * @modules java.security.jgss
 *          jdk.security.auth
 *          java.security.jgss/sun.security.jgss.krb5
 *          java.security.jgss/sun.security.krb5:+open
 *          java.security.jgss/sun.security.krb5.internal:+open
 *          java.security.jgss/sun.security.krb5.internal.ccache
 *          java.security.jgss/sun.security.krb5.internal.crypto
 *          java.security.jgss/sun.security.krb5.internal.ktab
 *          java.base/sun.security.util
 * @run main/othervm -Dtest.security.protocol=DTLSv1.0
 *      -Dtest.mode=norm DTLSv10HandshakeWithReplicatedPacketsTest
 * @run main/othervm -Dtest.security.protocol=DTLSv1.0
 *      -Dtest.mode=norm_sni DTLSv10HandshakeWithReplicatedPacketsTest
 * @run main/othervm -Dtest.security.protocol=DTLSv1.0
 *      -Dtest.mode=krb DTLSv10HandshakeWithReplicatedPacketsTest
 */

/**
 * Testing DTLS engines handshake using each of the supported cipher suites with
 * replicated packets check.
 */
public class DTLSv10HandshakeWithReplicatedPacketsTest {
    public static void main(String[] args) {
        DTLSHandshakeWithReplicatedPacketsTest.main(args);
    }
}
