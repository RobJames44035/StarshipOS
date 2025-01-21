/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import jdk.test.lib.security.CertUtils;

/*
 * @test
 * @bug 8251468
 * @library /test/lib
 * @summary Check that X509Certificate.getSubjectAlternativeNames,
 *          getIssuerAlternativeNames and getExtendedKeyUsage throw
 *          CertificateParsingException if extension is unparseable/invalid.
 */
public class GetUnparseableExtensions {

    // Cert has 3 badly encoded extensions:
    // 1. SubjectAlternativeNameExtension with a null RFC822Name
    // 2. IssuerAlternativeNameExtension with a null RFC822Name
    // 3. ExtendedKeyUsageExtension encoded as a Set instead of a Sequence
    private final static String CERT_WITH_UNPARSEABLE_EXTS = """
        -----BEGIN CERTIFICATE-----
        MIIDDTCCAfWgAwIBAgIIc/2fukmBqZgwDQYJKoZIhvcNAQELBQAwDTELMAkGA1UE
        AxMCQ0EwHhcNMjExMDI2MTgxNzU4WhcNMjIwMTI0MTgxNzU4WjANMQswCQYDVQQD
        EwJFMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKIIIsP6ghZ94hIl
        U3G+D11vASSP7JMmX6G6opGKel60dZ5So3Tdj0E6VHehwcxEdh94LEIxvOkS3Cdh
        hd1iiB4/q2InvLuGzSVvWAwYNkB7GbR31pIst0GAFdH+z/tVjjHcacBGwmysk8DN
        Rmxm66AyJxHA1U/v64vx5kSGNoUAEcWm+OslxfdJ7nygnU+S6a/06sRvifVn7q7c
        Kg4AIFE1+vWrcgYbApkPNjLiRGAr33TAb1y+nN1zW/luEMEQIJBd+YJW9OrPGMUr
        SDSXfB06HscH1bTUfN2ngsxT2hy9eiCiDtagPIwYA0sVNjyUtfPsjAH1oxDPXzC5
        quJB5/sCAwEAAaNxMG8wHQYDVR0OBBYEFCqWMQBki21cSqKIHp4rFitf6pQ2MAsG
        A1UdEQQEMAKBADALBgNVHRIEBDACgQAwHwYDVR0jBBgwFoAUJbvRN17hEohYsg1M
        icq1mMpIwoAwEwYDVR0lBAwxCgYIKwYBBQUHAwEwDQYJKoZIhvcNAQELBQADggEB
        ABBqJ/yYXD1xueB63GRY4ZotO6ukEkJiPZIwrr2vZW+GMws2b9gqNoD+dL9AeYCA
        Zb6RYbaNDY5OoJmEty9KbtON7Rt1LtCFuZbYKxrhW0dJgXLyNOgXr+x0g2btbvWV
        r8U1icwHapZM5IqDKLivzZNzwv52mrJDuzWqmlhAIlPLIU1QfNQ1oC8HpFkL71Bb
        sB/4OIxxjRzf0AGmb7aeQNfxag2oKlOwqzum1FLt8BaVjylc0aUATPtkgothK8nK
        m5jXJmA4zA11ck0uJW39gDRuR0D1k4qm/s/5Iuhd6MRVDXEhVbJXuH2yzHcmbgRs
        paD/he6C1JszWf7YrTsX3Fc=
        -----END CERTIFICATE-----
        """;

    @FunctionalInterface
    public interface TestCase<T> {
        T test() throws Exception;
    }

    public static void main(String[] args) throws Exception {

        X509Certificate cert =
                CertUtils.getCertFromString(CERT_WITH_UNPARSEABLE_EXTS);
        getExtension(() -> cert.getSubjectAlternativeNames());
        getExtension(() -> cert.getIssuerAlternativeNames());
        getExtension(() -> cert.getExtendedKeyUsage());
    }

    private static void getExtension(TestCase<?> t) throws Exception {
        try {
            t.test();
            throw new Exception("Test FAILED");
        } catch (CertificateParsingException cpe) {
            System.out.println(cpe.getMessage());
        }
    }
}
