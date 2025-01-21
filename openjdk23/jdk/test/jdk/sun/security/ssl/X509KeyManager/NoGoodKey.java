/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8278560
 * @summary X509KeyManagerImpl::getAliases might return a good key with others
 * @library /test/lib
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.util
 *          java.base/sun.security.x509
 */
import jdk.test.lib.Asserts;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.util.KnownOIDs;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.X500Name;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Vector;
import javax.net.ssl.*;

public class NoGoodKey {
    public static void main(String[] args) throws Exception {

        PrintStream oldErr = System.err;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        CertificateExtensions exts = new CertificateExtensions();
        Vector<ObjectIdentifier> xku = new Vector<>(1);
        xku.add(ObjectIdentifier.of(KnownOIDs.KP_TimeStamping));
        var ext = new ExtendedKeyUsageExtension(xku);
        exts.setExtension(ext.getId(), ext);

        KeyStore ks = KeyStore.getInstance("pkcs12");
        char[] pass = "password".toCharArray();
        ks.load(null, null);

        CertAndKeyGen ckg;

        // This is for the first keyType but wrong extendedKeyUsage
        ckg = new CertAndKeyGen("EC", "SHA256withECDSA");
        ckg.generate("secp256r1");
        ks.setKeyEntry("a", ckg.getPrivateKey(), pass, new java.security.cert.Certificate[]
                { ckg.getSelfCertificate(new X500Name("CN=user"), new Date(), 10000, exts) });

        // This is for the 2nd keyType and is perfect
        ckg = new CertAndKeyGen("RSA", "SHA256withRSA");
        ckg.generate(2048);
        ks.setKeyEntry("b", ckg.getPrivateKey(), pass, new Certificate[]
                { ckg.getSelfCertificate(new X500Name("CN=user"), 10000) });

        try {
            System.setProperty("javax.net.debug", "keymanager");
            System.setErr(new PrintStream(bout));
            var kmf = KeyManagerFactory.getInstance("NewSunX509");
            kmf.init(ks, pass);
            var km = (X509ExtendedKeyManager) kmf.getKeyManagers()[0];

            // b will be chosen anyway
            Asserts.assertEQ(km.chooseClientAlias(new String[]{"EC", "RSA"}, null, null), "1.0.b");
        } finally {
            System.setErr(oldErr);
        }

        // make sure it's chosen as good matching key
        String log = bout.toString();
        Asserts.assertFalse(log.contains("no good matching key found"), log);
    }
}
