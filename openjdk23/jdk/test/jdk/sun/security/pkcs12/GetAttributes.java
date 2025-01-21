/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8225181
 * @summary KeyStore should have a getAttributes method
 * @library /test/lib
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.x509
 */

import jdk.test.lib.Asserts;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class GetAttributes {

    static char[] pass = "changeit".toCharArray();

    public static void main(String[] args) throws Exception {

        // Create a keystore with one private key entry and one cert entry
        CertAndKeyGen cag = new CertAndKeyGen("EC", "SHA256withECDSA");
        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(null, null);
        cag.generate("secp256r1");
        ks.setKeyEntry("a", cag.getPrivateKey(), pass, new Certificate[] {
                cag.getSelfCertificate(new X500Name("CN=a"), 1000)} );
        cag.generate("secp256r1");
        ks.setCertificateEntry("b",
                cag.getSelfCertificate(new X500Name("CN=b"), 1000));

        // Test
        check(ks);

        // Test newly loaded
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ks.store(bos, pass);
        KeyStore ks2 = KeyStore.getInstance("pkcs12");
        ks2.load(new ByteArrayInputStream(bos.toByteArray()), pass);
        check(ks2);
    }

    static void check(KeyStore ks) throws Exception {
        var entry = ks.getEntry("a", new KeyStore.PasswordProtection(pass));
        Asserts.assertEQ(ks.getAttributes("a"), entry.getAttributes());
        entry = ks.getEntry("b", null);
        Asserts.assertEQ(ks.getAttributes("b"), entry.getAttributes());
    }
}
