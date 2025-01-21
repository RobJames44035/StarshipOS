/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8043758
 * @summary Testing DTLS buffer overflow and underflow status when dealing with
 *          application data.
 * @library /sun/security/krb5/auto /javax/net/ssl/TLSCommon /test/lib
 * @modules java.security.jgss
 *          jdk.security.auth
 *          java.base/sun.security.util
 *          java.security.jgss/sun.security.jgss.krb5
 *          java.security.jgss/sun.security.krb5:+open
 *          java.security.jgss/sun.security.krb5.internal:+open
 *          java.security.jgss/sun.security.krb5.internal.ccache
 *          java.security.jgss/sun.security.krb5.internal.crypto
 *          java.security.jgss/sun.security.krb5.internal.ktab
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=norm DTLSBufferOverflowUnderflowTest
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=norm_sni DTLSBufferOverflowUnderflowTest
 * @run main/othervm -Dtest.security.protocol=DTLS
 *      -Dtest.mode=krb DTLSBufferOverflowUnderflowTest
 */

/**
 * Testing DTLS incorrect app data packages unwrapping.
 */
public class DTLSBufferOverflowUnderflowTest {
    public static void main(String[] args) {
        BufferOverflowUnderflowTest.main(args);
    }
}
