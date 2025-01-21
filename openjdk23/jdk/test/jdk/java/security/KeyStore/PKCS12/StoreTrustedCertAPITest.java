/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import static java.lang.System.err;
import static java.lang.System.out;

/**
 * @test
 * @bug 8048830
 * @summary Test imports certificate from file to PKCS12 keystore store it as
 * trusted certificate Check import errors (must be not errors) & check keystore
 * content after import
 * @library ../
 * @library /test/lib
 * @run main StoreTrustedCertAPITest
 */
public class StoreTrustedCertAPITest {
    private static final char[] PASSWORD = "passwd".toCharArray();
    private static final String ALIAS = "testkey_stcapi";
    private static final String WORKING_DIRECTORY = System.getProperty(
            "test.classes", "." + File.separator);
    private static final String CERT_PATH = WORKING_DIRECTORY + File.separator
            + "cert.data";
    private static final String KEYSTORE_PATH = WORKING_DIRECTORY
            + File.separator + "ks.pkcs12";

    /**
     * Test logic (environment has set up)
     */
    private void runTest() throws FileNotFoundException, CertificateException,
            KeyStoreException, IOException, NoSuchAlgorithmException {
        Certificate cert;
        CertificateFactory cf;
        try (FileInputStream fi = new FileInputStream(CERT_PATH)) {
            cf = CertificateFactory.getInstance("X.509");
            cert = cf.generateCertificate(fi);
            KeyStore ks = KeyStore.getInstance(
                    Utils.KeyStoreType.pkcs12.name());
            ks.load(null, null);
            ks.setCertificateEntry(ALIAS, cert);
            Utils.saveKeyStore(ks, KEYSTORE_PATH, PASSWORD);
            ks = Utils.loadKeyStore(KEYSTORE_PATH, Utils.KeyStoreType.pkcs12,
                    PASSWORD);
            final Certificate ksCert = ks.getCertificate(ALIAS);
            if (!ksCert.equals(cert)) {
                err.println("Orig cert: " + cert.toString());
                err.println("Cert from keystore: " + ksCert.toString());
                throw new RuntimeException("Certificates don't match");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        StoreTrustedCertAPITest test = new StoreTrustedCertAPITest();
        test.setUp();
        test.runTest();
        out.println("Test Passed");
    }

    private void setUp() {
        Utils.createKeyStore(Utils.KeyStoreType.pkcs12, KEYSTORE_PATH, ALIAS);
        Utils.exportCert(Utils.KeyStoreType.pkcs12, KEYSTORE_PATH,
                ALIAS, CERT_PATH);
        new File(KEYSTORE_PATH).delete();
    }
}
