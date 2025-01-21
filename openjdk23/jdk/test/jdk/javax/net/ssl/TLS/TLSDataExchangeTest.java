/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8085979
 * @summary Testing TLS application data exchange using each of the supported
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
 * @run main/othervm -Dtest.security.protocol=TLS -Dtest.mode=norm TLSDataExchangeTest
 * @run main/othervm -Dtest.security.protocol=TLS -Dtest.mode=norm_sni TLSDataExchangeTest
 * @run main/othervm -Dtest.security.protocol=TLS -Dtest.mode=krb TLSDataExchangeTest
 */

/**
 * Testing TLS application data exchange using each of the supported cipher
 * suites.
 */
public class TLSDataExchangeTest {
    public static void main(String[] args) {
        DataExchangeTest.main(args);
    }
}
