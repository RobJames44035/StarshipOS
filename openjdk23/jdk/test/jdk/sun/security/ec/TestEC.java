/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/**
 * @test
 * @bug 6840752 8168078
 * @summary  Provide out-of-the-box support for ECC algorithms
 * @library /test/lib
 * @library ../pkcs11
 * @library ../pkcs11/ec
 * @library ../pkcs11/sslecc
 * @library ../../../java/security/testlibrary
 * @library ../../../javax/net/ssl/TLSCommon
 * @modules jdk.crypto.cryptoki/sun.security.pkcs11.wrapper
 * @run main/othervm -Djdk.tls.namedGroups="secp256r1" TestEC
 */

import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

/*
 * Leverage the collection of EC tests used by PKCS11
 *
 * NOTE: the following 5 files were copied here from the PKCS11 EC Test area
 *       and must be kept in sync with the originals:
 *
 *           ../pkcs11/ec/p12passwords.txt
 *           ../pkcs11/ec/certs/sunlabscerts.pem
 *           ../pkcs11/ec/pkcs12/secp256r1server-secp384r1ca.p12
 *           ../pkcs11/sslecc/keystore
 *           ../pkcs11/sslecc/truststore
 */

public class TestEC {

    /*
     * Turn on SSL debugging
     */
    private static final boolean debug = true;

    public static void main(String[] args) throws Exception {
        // reset security properties to make sure that the algorithms
        // and keys used in this test are not disabled.
        Security.setProperty("jdk.tls.disabledAlgorithms", "");
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");

        if (debug) {
            System.setProperty("javax.net.debug", "all");
        }

        ProvidersSnapshot snapshot = ProvidersSnapshot.create();
        try {
            main0(args);
        } finally {
            snapshot.restore();
        }
    }

    public static void main0(String[] args) throws Exception {
        Provider p = Security.getProvider(System.getProperty("test.provider.name", "SunEC"));

        if (p == null) {
            throw new NoSuchProviderException("Can't get SunEC provider");
        }

        System.out.println("Running tests with " + p.getName() +
            " provider...\n");
        long start = System.currentTimeMillis();

        /*
         * The entry point used for each test is its instance method
         * called main (not its static method called main).
         */
        System.out.println("TestECDH");
        new TestECDH().main(p);
        System.out.println("TestECDSA");
        new TestECDSA().main(p);
        System.out.println("TestCurves");
        new TestCurves().main(p);
        System.out.println("TestKeyFactory");
        new TestKeyFactory().main(p);
        System.out.println("TestECGenSpec");
        new TestECGenSpec().main(p);
        System.out.println("ReadPKCS12");
        new ReadPKCS12().main(p);
        System.out.println("ReadCertificate");
        new ReadCertificates().main(p);
        System.out.println("ClientJSSEServerJSSE");
        new ClientJSSEServerJSSE().main(p);

        long stop = System.currentTimeMillis();
        System.out.println("\nCompleted tests with " + p.getName() +
            " provider (" + ((stop - start) / 1000.0) + " seconds).");
    }
}
