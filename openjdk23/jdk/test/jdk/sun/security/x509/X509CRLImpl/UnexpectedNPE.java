/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5052433 8315042
 * @summary Verify that generateCRL and generateCRLs methods do not throw
 *          NullPointerException. They should throw CRLException instead.
 * @library /test/lib
 */
import java.security.NoSuchProviderException;
import java.security.cert.*;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import jdk.test.lib.Utils;

public class UnexpectedNPE {
    static CertificateFactory cf = null;

    public static void main(String[] av ) throws CertificateException,
            NoSuchProviderException {
        byte[] encoded_1 = { 0x00, 0x00, 0x00, 0x00 };
        byte[] encoded_2 = { 0x30, 0x01, 0x00, 0x00 };
        byte[] encoded_3 = { 0x30, 0x01, 0x00 };
        byte[] encoded_4 = Base64.getDecoder().decode(
                "MAsGCSqGSMP7TQEHAjI1Bgn///////8wCwUyAQ==");

        cf = CertificateFactory.getInstance("X.509", "SUN");

        run(encoded_1);
        run(encoded_2);
        run(encoded_3);
        run(encoded_4);
    }

    private static void run(byte[] buf) {
        Utils.runAndCheckException(
                () -> cf.generateCRL(new ByteArrayInputStream(buf)),
                CRLException.class);
        Utils.runAndCheckException(
                () -> cf.generateCRLs(new ByteArrayInputStream(buf)),
                CRLException.class);
    }
}
