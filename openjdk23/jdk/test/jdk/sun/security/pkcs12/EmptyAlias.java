/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8173181
 * @summary KeyStore regression due to default keystore being changed to PKCS12
 */

import java.io.*;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Test that a PKCS12 keystore entry can be created with an empty alias name.
 */
public class EmptyAlias {

    private static final String DIR = System.getProperty("test.src", ".");
    private static final String CERT = DIR + "/trusted.pem";
    private static final String EMPTY_ALIAS = "";

    public static void main(String[] args) throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(null, null);

        keystore.setCertificateEntry(EMPTY_ALIAS, loadCertificate(CERT));
        KeyStore.Entry entry = keystore.getEntry(EMPTY_ALIAS, null);

        if (entry == null) {
            throw new Exception(
                "Error retrieving keystore entry using its (empty) alias");
        }

        System.out.println("OK");
    }

    private static Certificate loadCertificate(String certFile)
            throws Exception {
        X509Certificate cert = null;
        try (FileInputStream certStream = new FileInputStream(certFile)) {
            CertificateFactory factory =
                CertificateFactory.getInstance("X.509");
            return factory.generateCertificate(certStream);
        }
    }
}
