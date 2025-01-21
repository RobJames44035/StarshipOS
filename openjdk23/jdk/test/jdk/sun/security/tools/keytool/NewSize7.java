/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6561126 8267319
 * @summary keytool should use larger default keysize for keypairs
 * @modules java.base/sun.security.util
 *          java.base/sun.security.tools.keytool
 * @run main NewSize7
 */

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import sun.security.util.SecurityProviderConstants;

public class NewSize7 {
    public static void main(String[] args) throws Exception {
        String FILE = "newsize7-ks";
        new File(FILE).delete();
        sun.security.tools.keytool.Main.main(("-debug -genkeypair -keystore " +
                FILE +
                " -alias a -dname cn=c -storepass changeit" +
                " -keypass changeit -keyalg rsa").split(" "));
        KeyStore ks = KeyStore.getInstance("JKS");
        try (FileInputStream fin = new FileInputStream(FILE)) {
            ks.load(fin, "changeit".toCharArray());
        }
        Files.delete(Paths.get(FILE));
        RSAPublicKey r = (RSAPublicKey)ks.getCertificate("a").getPublicKey();
        if (r.getModulus().bitLength() != 3072) {
            throw new Exception("Bad keysize");
        }
        X509Certificate x = (X509Certificate)ks.getCertificate("a");
        if (!x.getSigAlgName().equals("SHA384withRSA")) {
            throw new Exception("Bad sigalg");
        }
    }
}
