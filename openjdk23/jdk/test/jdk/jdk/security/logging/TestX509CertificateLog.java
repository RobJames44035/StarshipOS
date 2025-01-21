/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.security.logging;

import jdk.test.lib.security.TestCertificate;

/*
 * @test
 * @bug 8148188
 * @summary Enhance the security libraries to record events of interest
 * @library /test/lib /test/jdk
 * @modules java.base/sun.security.x509 java.base/sun.security.tools.keytool
 * @run main/othervm jdk.security.logging.TestX509CertificateLog LOGGING_ENABLED
 * @run main/othervm jdk.security.logging.TestX509CertificateLog LOGGING_DISABLED
 */
public class TestX509CertificateLog {
    public static void main(String[] args) throws Exception {
        LogJvm l = new LogJvm(GenerateX509Certicate.class, args);
        l.addExpected(
            "FINE: X509Certificate: Alg:" + TestCertificate.ONE.algorithm +
            ", Serial:" + TestCertificate.ONE.serialNumber +
            ", Subject:" + TestCertificate.ONE.subject +
            ", Issuer:"  + TestCertificate.ONE.issuer +
            ", Key type:" + TestCertificate.ONE.keyType +
            ", Length:" + TestCertificate.ONE.keyLength +
            ", Cert Id:" + TestCertificate.ONE.certId);
        l.addExpected(
            "FINE: X509Certificate: Alg:" + TestCertificate.TWO.algorithm +
            ", Serial:" + TestCertificate.TWO.serialNumber +
            ", Subject:" + TestCertificate.TWO.subject +
            ", Issuer:"  + TestCertificate.TWO.issuer +
            ", Key type:" + TestCertificate.TWO.keyType +
            ", Length:" + TestCertificate.TWO.keyLength +
            ", Cert Id:" + TestCertificate.TWO.certId);
        l.testExpected();
    }

    public static class GenerateX509Certicate {
        public static void main(String[] args) throws Exception {
            TestCertificate.ONE.certificate();
            TestCertificate.TWO.certificate();
        }
    }
}
