/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.security.KeyStore;
import java.security.cert.Certificate;
import jdk.test.lib.Asserts;

/**
 * @test
 * @bug 6522064
 * @library /test/lib
 * @requires os.family == "windows"
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.x509
 * @summary Aliases from Microsoft CryptoAPI has bad character encoding
 */

public class NonAsciiAlias {
    public static void main(String[] args) throws Exception {
        KeyStore ks = KeyStore.getInstance("Windows-MY");
        String alias = "\u58c6\u94a56522064";
        try {
            ks.load(null, null);
            CertAndKeyGen cag = new CertAndKeyGen("RSA", "SHA256withRSA");
            cag.generate(2048);
            ks.setKeyEntry(alias, cag.getPrivateKey(), null, new Certificate[]{
                    cag.getSelfCertificate(new X500Name("CN=Me"), 1000)
            });
            // Confirms the alias is there
            Asserts.assertTrue(ks.containsAlias(alias));
            ks.store(null, null);
            ks.load(null, null);
            // Confirms the alias is there after reload
            Asserts.assertTrue(ks.containsAlias(alias));
            ks.deleteEntry(alias);
            // Confirms the alias is removed
            Asserts.assertFalse(ks.containsAlias(alias));
            ks.store(null, null);
            ks.load(null, null);
            // Confirms the alias is removed after reload
            Asserts.assertFalse(ks.containsAlias(alias));
        } finally {
            ks.deleteEntry(alias);
            // in case the correct alias is not found, clean up a wrong one
            ks.deleteEntry("??6522064");
        }
    }
}
