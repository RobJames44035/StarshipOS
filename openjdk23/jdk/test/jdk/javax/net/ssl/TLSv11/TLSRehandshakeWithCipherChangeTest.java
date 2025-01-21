/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8085979
 * @summary Testing TLS engines re-handshaking with cipher change. New cipher
 *          is taken randomly from the supporetd ciphers list.
 * @key randomness
 * @library /sun/security/krb5/auto /test/lib /javax/net/ssl/TLSCommon
 * @modules java.security.jgss
 *          java.security.jgss/sun.security.jgss.krb5
 *          java.security.jgss/sun.security.krb5:+open
 *          java.security.jgss/sun.security.krb5.internal:+open
 *          java.security.jgss/sun.security.krb5.internal.ccache
 *          java.security.jgss/sun.security.krb5.internal.crypto
 *          java.security.jgss/sun.security.krb5.internal.ktab
 *          java.base/sun.security.util
 * @build jdk.test.lib.RandomFactory
 * @run main/othervm -Dtest.security.protocol=TLSv1.1 TLSRehandshakeWithCipherChangeTest
 */

/**
 * Testing TLS engines re-handshaking with cipher change. New cipher is taken
 * randomly from the supported ciphers list.
 */
public class TLSRehandshakeWithCipherChangeTest {
    public static void main(String[] args) {
        RehandshakeWithCipherChangeTest.main(args);
    }
}
