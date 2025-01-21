/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8270946
 * @library /test/lib
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @summary Check that X509CertImpl.getFingerprint does not return null when
 *          there are errors calculating the fingerprint
 */

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import sun.security.x509.X509CertImpl;
import sun.security.util.Debug;

import jdk.test.lib.Asserts;
import jdk.test.lib.security.CertUtils;

public class GetFingerprintError {

    private static final Debug dbg = Debug.getInstance("certpath");

    public static void main(String[] args) throws Exception {
        X509Certificate cert = CertUtils.getCertFromString(CertUtils.RSA_CERT);

        // test invalid MessageDigest algorithm
        Asserts.assertNull(X509CertImpl.getFingerprint("NoSuchAlg", cert, dbg));

        // test cert with bad encoding
        X509Certificate fcert = new X509CertificateWithBadEncoding(cert);
        Asserts.assertNull(X509CertImpl.getFingerprint("SHA-256", fcert, dbg));
    }

    private static class X509CertificateWithBadEncoding
            extends CertUtils.ForwardingX509Certificate {
        private X509CertificateWithBadEncoding(X509Certificate cert) {
            super(cert);
        }
        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            throw new CertificateEncodingException();
        }
    }
}
