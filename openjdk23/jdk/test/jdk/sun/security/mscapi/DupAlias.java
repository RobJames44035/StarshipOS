/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
import jdk.test.lib.Asserts;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.HexFormat;

/**
 * @test
 * @bug 8187634
 * @requires os.family == "windows"
 * @library /test/lib
 * @modules java.base/sun.security.tools.keytool
 *          java.base/sun.security.x509
 * @summary getCertificateAlias should return correct alias
 */
public class DupAlias {
    public static void main(String[] args) throws Exception {

        String nn = "8187634";
        String na = nn + "a";
        String nb = nn + "b";
        String n1 = nn + " (1)";

        CertAndKeyGen g = new CertAndKeyGen("EC", "SHA256withECDSA");
        g.generate(-1);
        X509Certificate a = g.getSelfCertificate(new X500Name("CN=" + na), 1000);
        g.generate(-1);
        X509Certificate b = g.getSelfCertificate(new X500Name("CN=" + nb), 1000);

        KeyStore ks = KeyStore.getInstance("Windows-MY-CURRENTUSER");
        try {
            ks.load(null, null);
            ks.deleteEntry(na);
            ks.deleteEntry(nb);
            ks.deleteEntry(nn);
            ks.deleteEntry(n1);
            ks.setCertificateEntry(na, a);
            ks.setCertificateEntry(nb, b);

            ps(String.format("""
                    $cert = Get-Item Cert:/CurrentUser/My/%s;
                    $cert.FriendlyName = %s;
                    $cert = Get-Item Cert:/CurrentUser/My/%s;
                    $cert.FriendlyName = %s;
                    """, thumbprint(a), nn, thumbprint(b), nn));

            ks.load(null, null);
            Asserts.assertFalse(ks.containsAlias(na));
            Asserts.assertFalse(ks.containsAlias(nb));
            Asserts.assertEquals(ks.getCertificateAlias(ks.getCertificate(nn)), nn);
            Asserts.assertEquals(ks.getCertificateAlias(ks.getCertificate(n1)), n1);
        } finally {
            ks.deleteEntry(na);
            ks.deleteEntry(nb);
            ks.deleteEntry(nn);
            ks.deleteEntry(n1);
        }
    }

    static void ps(String f) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("powershell", "-Command", f);
        pb.inheritIO();
        if (pb.start().waitFor() != 0) {
            throw new RuntimeException("Failed");
        }
    }

    static String thumbprint(X509Certificate c) throws Exception {
        return HexFormat.of().formatHex(
                MessageDigest.getInstance("SHA-1").digest(c.getEncoded()));
    }
}
